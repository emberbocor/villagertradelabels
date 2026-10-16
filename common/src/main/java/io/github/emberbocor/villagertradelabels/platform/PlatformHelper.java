package io.github.emberbocor.villagertradelabels.platform;

import java.nio.file.Path;

public interface PlatformHelper {
    Path getConfigDirectory();

    boolean isModLoaded(String modId);
}
