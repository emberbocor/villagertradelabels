package io.github.emberbocor.villagertradelabels.platform;

import java.nio.file.Path;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public final class FabricPlatformHelper implements PlatformHelper {
    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public Style rarityStyle(Rarity rarity) {
        return Style.EMPTY.withColor(rarity.color);
    }

    @Override
    public int defaultHideFlags(ItemStack stack) {
        return 0;
    }
}
