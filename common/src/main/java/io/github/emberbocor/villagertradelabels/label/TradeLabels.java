package io.github.emberbocor.villagertradelabels.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import io.github.emberbocor.villagertradelabels.mixin.ItemEnchantmentsAccessor;
import io.github.emberbocor.villagertradelabels.platform.Services;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class TradeLabels {
    private TradeLabels() {
    }

    public static Optional<TradeLabel> of(ItemStack stack, HolderLookup.Provider registries) {
        if (stack.isEmpty() || stack.has(DataComponents.HIDE_TOOLTIP)) {
            return Optional.empty();
        }
        HolderSet<Enchantment> order = tooltipOrder(registries);
        List<EnchantmentLevel> enchantments = new ArrayList<>();
        addShown(stack.get(DataComponents.STORED_ENCHANTMENTS), order, enchantments);
        addShown(stack.get(DataComponents.ENCHANTMENTS), order, enchantments);
        if (!enchantments.isEmpty()) {
            return Optional.of(enchantmentLabel(enchantments.getFirst(), enchantments.size() - 1));
        }
        if (stack.is(Items.FILLED_MAP) && (stack.has(DataComponents.CUSTOM_NAME) || stack.has(DataComponents.ITEM_NAME))) {
            return Optional.of(new TradeLabel(itemName(stack), Component.empty()));
        }
        return Optional.empty();
    }

    private static HolderSet<Enchantment> tooltipOrder(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentTags.TOOLTIP_ORDER)
                .<HolderSet<Enchantment>>map(tag -> tag)
                .orElseGet(HolderSet::direct);
    }

    private static void addShown(@Nullable ItemEnchantments enchantments, HolderSet<Enchantment> order, List<EnchantmentLevel> target) {
        if (enchantments == null || !((ItemEnchantmentsAccessor) enchantments).villagertradelabels$showInTooltip()) {
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

    private static Component itemName(ItemStack stack) {
        MutableComponent name = Component.empty().append(stack.getHoverName()).withStyle(Services.PLATFORM.rarityStyle(stack.getRarity()));
        if (stack.has(DataComponents.CUSTOM_NAME)) {
            name.withStyle(ChatFormatting.ITALIC);
        }
        return name;
    }

    private record EnchantmentLevel(Holder<Enchantment> enchantment, int level) {
    }
}
