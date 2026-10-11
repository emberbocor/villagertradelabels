package io.github.emberbocor.villagertradelabels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.item.enchantment.ItemEnchantments;

@Mixin(ItemEnchantments.class)
public interface ItemEnchantmentsAccessor {
    @Accessor("showInTooltip")
    boolean villagertradelabels$showInTooltip();
}
