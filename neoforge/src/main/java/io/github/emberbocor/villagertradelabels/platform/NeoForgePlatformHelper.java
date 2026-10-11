package io.github.emberbocor.villagertradelabels.platform;

import java.nio.file.Path;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

public final class NeoForgePlatformHelper implements PlatformHelper {
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
}
