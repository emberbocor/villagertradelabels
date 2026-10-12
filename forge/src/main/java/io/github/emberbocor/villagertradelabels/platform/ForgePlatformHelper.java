package io.github.emberbocor.villagertradelabels.platform;

import java.nio.file.Path;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

public final class ForgePlatformHelper implements PlatformHelper {
    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Style rarityStyle(Rarity rarity) {
        return rarity.getStyleModifier().apply(Style.EMPTY);
    }

    @Override
    public int defaultHideFlags(ItemStack stack) {
        return stack.getItem().getDefaultTooltipHideFlags(stack);
    }
}
