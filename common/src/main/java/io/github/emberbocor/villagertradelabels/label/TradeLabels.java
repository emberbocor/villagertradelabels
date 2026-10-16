package io.github.emberbocor.villagertradelabels.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class TradeLabels {
    private TradeLabels() {
    }

    public static Optional<TradeLabel> of(ItemStack stack, HolderLookup.Provider registries) {
        TooltipDisplay display = stack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
        if (stack.isEmpty() || display.hideTooltip()) {
            return Optional.empty();
        }
        HolderSet<Enchantment> order = tooltipOrder(registries);
        List<EnchantmentLevel> enchantments = new ArrayList<>();
        addShown(stack, DataComponents.STORED_ENCHANTMENTS, display, order, enchantments);
        addShown(stack, DataComponents.ENCHANTMENTS, display, order, enchantments);
        if (!enchantments.isEmpty()) {
            return Optional.of(enchantmentLabel(enchantments.getFirst(), enchantments.size() - 1));
        }
        if (isNamedMap(stack)) {
            return Optional.of(new TradeLabel(stack.getStyledHoverName(), Component.empty()));
        }
        return Optional.empty();
    }

    private static boolean isNamedMap(ItemStack stack) {
        if (!(stack.getItem() instanceof MapItem)) {
            return false;
        }
        return !stack.is(Items.FILLED_MAP) || stack.has(DataComponents.CUSTOM_NAME) || hasOwnItemName(stack);
    }

    private static boolean hasOwnItemName(ItemStack stack) {
        return stack.hasNonDefault(DataComponents.ITEM_NAME) && stack.has(DataComponents.ITEM_NAME);
    }

    private static HolderSet<Enchantment> tooltipOrder(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.TOOLTIP_ORDER)
                .<HolderSet<Enchantment>>map(tag -> tag)
                .orElseGet(HolderSet::direct);
    }

    private static void addShown(ItemStack stack, DataComponentType<ItemEnchantments> type, TooltipDisplay display,
                                 HolderSet<Enchantment> order, List<EnchantmentLevel> target) {
        ItemEnchantments enchantments = stack.get(type);
        if (enchantments == null || !display.shows(type)) {
            return;
        }
        for (Holder<Enchantment> enchantment : order) {
            int level = enchantments.getLevel(enchantment);
            if (level > 0) {
                target.add(new EnchantmentLevel(enchantment, level));
            }
        }
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            if (!order.contains(entry.getKey())) {
                target.add(new EnchantmentLevel(entry.getKey(), entry.getIntValue()));
            }
        }
    }

    private static TradeLabel enchantmentLabel(EnchantmentLevel first, int others) {
        Holder<Enchantment> enchantment = first.enchantment();
        MutableComponent name = enchantment.value().description().copy();
        ComponentUtils.mergeStyles(name, Style.EMPTY.withColor(enchantment.is(EnchantmentTags.CURSE) ? ChatFormatting.RED : ChatFormatting.GRAY));
        MutableComponent suffix = Component.empty();
        if (first.level() != 1 || enchantment.value().getMaxLevel() != 1) {
            suffix.append(Component.empty()
                    .withStyle(name.getStyle())
                    .append(CommonComponents.SPACE)
                    .append(Component.translatable("enchantment.level." + first.level())));
        }
        if (others > 0) {
            suffix.append(Component.literal(" +" + others).withStyle(ChatFormatting.GRAY));
        }
        return new TradeLabel(name, suffix);
    }

    private record EnchantmentLevel(Holder<Enchantment> enchantment, int level) {
    }
}
