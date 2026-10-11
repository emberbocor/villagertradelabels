package io.github.emberbocor.villagertradelabels;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(VillagerTradeLabels.MODID)
public class VillagerTradeLabels {
    public static final String MODID = "villagertradelabels";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VillagerTradeLabels(IEventBus modEventBus, ModContainer modContainer) {
    }
}
