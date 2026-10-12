package io.github.emberbocor.villagertradelabels.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.emberbocor.villagertradelabels.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public final class TradeLabels {
    private static final String HIDE_FLAGS = "HideFlags";

    private TradeLabels() {
    }

    public static Optional<TradeLabel> of(ItemStack stack) {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        int hideFlags = hideFlags(stack);
        List<EnchantmentLevel> enchantments = new ArrayList<>();
        if (stack.getItem() instanceof EnchantedBookItem && isShown(hideFlags, ItemStack.TooltipPart.ADDITIONAL)) {
            addKnown(EnchantedBookItem.getEnchantments(stack), enchantments);
        }
        if (stack.hasTag() && isShown(hideFlags, ItemStack.TooltipPart.ENCHANTMENTS)) {
            addKnown(stack.getEnchantmentTags(), enchantments);
        }
        if (!enchantments.isEmpty()) {
            return Optional.of(enchantmentLabel(enchantments.get(0), enchantments.size() - 1));
        }
        if (stack.is(Items.FILLED_MAP) && stack.hasCustomHoverName()) {
            return Optional.of(new TradeLabel(itemName(stack), Component.empty()));
        }
        return Optional.empty();
    }

    private static int hideFlags(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(HIDE_FLAGS, Tag.TAG_ANY_NUMERIC) ? tag.getInt(HIDE_FLAGS) : Services.PLATFORM.defaultHideFlags(stack);
    }

    private static boolean isShown(int hideFlags, ItemStack.TooltipPart part) {
        return (hideFlags & part.getMask()) == 0;
    }

    private static void addKnown(ListTag tags, List<EnchantmentLevel> target) {
        for (int i = 0; i < tags.size(); i++) {
            CompoundTag tag = tags.getCompound(i);
            BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(tag))
                    .ifPresent(enchantment -> target.add(new EnchantmentLevel(enchantment, EnchantmentHelper.getEnchantmentLevel(tag))));
        }
    }

    private static TradeLabel enchantmentLabel(EnchantmentLevel first, int others) {
        Enchantment enchantment = first.enchantment();
        MutableComponent name = Component.translatable(enchantment.getDescriptionId())
                .withStyle(enchantment.isCurse() ? ChatFormatting.RED : ChatFormatting.GRAY);
        MutableComponent suffix = Component.empty();
        if (first.level() != 1 || enchantment.getMaxLevel() != 1) {
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
        if (stack.hasCustomHoverName()) {
            name.withStyle(ChatFormatting.ITALIC);
        }
        return name;
    }

    private record EnchantmentLevel(Enchantment enchantment, int level) {
    }
}
