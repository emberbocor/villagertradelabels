package io.github.emberbocor.villagertradelabels.compat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import io.github.emberbocor.villagertradelabels.VillagerTradeLabels;
import io.github.emberbocor.villagertradelabels.platform.Services;
import net.minecraft.world.item.trading.MerchantOffer;

public final class TradePeekCompat {
    private static final String MOD_ID = "villagertradepeek";
    private static final String HOLDER_CLASS = "io.github.emberbocor.villagertradepeek.client.LockedTradesHolder";
    private static final String TRADE_CLASS = "io.github.emberbocor.villagertradepeek.trade.LockedTrade";

    private static boolean resolved;
    @Nullable
    private static Method getLockedTrades;
    @Nullable
    private static Method getOffer;

    private TradePeekCompat() {
    }

    public static List<MerchantOffer> lockedOffers(Object screen) {
        if (!resolved) {
            resolve();
        }
        if (getLockedTrades == null || getOffer == null) {
            return List.of();
        }
        try {
            List<?> trades = (List<?>) getLockedTrades.invoke(screen);
            List<MerchantOffer> offers = new ArrayList<>(trades.size());
            for (Object trade : trades) {
                offers.add((MerchantOffer) getOffer.invoke(trade));
            }
            return offers;
        } catch (ReflectiveOperationException | RuntimeException e) {
            disable(e);
            return List.of();
        }
    }

    private static void resolve() {
        resolved = true;
        if (!Services.PLATFORM.isModLoaded(MOD_ID)) {
            return;
        }
        try {
            ClassLoader loader = TradePeekCompat.class.getClassLoader();
            getLockedTrades = Class.forName(HOLDER_CLASS, false, loader).getMethod("villagertradepeek$getLockedTrades");
            getOffer = Class.forName(TRADE_CLASS, false, loader).getMethod("offer");
        } catch (ReflectiveOperationException | LinkageError e) {
            disable(e);
        }
    }

    private static void disable(Throwable cause) {
        getLockedTrades = null;
        getOffer = null;
        VillagerTradeLabels.LOGGER.debug("Villager Trade Peek integration disabled: {}", cause.toString());
    }
}
