package io.github.emberbocor.villagertradelabels.platform;

import java.nio.file.Path;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

public interface PlatformHelper {
    Path getConfigDirectory();

    boolean isModLoaded(String modId);

    Style rarityStyle(Rarity rarity);
}
