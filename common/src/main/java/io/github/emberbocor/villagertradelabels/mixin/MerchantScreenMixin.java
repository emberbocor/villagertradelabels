package io.github.emberbocor.villagertradelabels.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.emberbocor.villagertradelabels.client.LabelToggle;
import io.github.emberbocor.villagertradelabels.client.TradeLabelsScreen;
import io.github.emberbocor.villagertradelabels.compat.TradePeekCompat;
import io.github.emberbocor.villagertradelabels.config.ClientConfig;
import io.github.emberbocor.villagertradelabels.label.TradeLabelRenderer;
import io.github.emberbocor.villagertradelabels.label.TradeLabels;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin extends AbstractContainerScreen<MerchantMenu> implements TradeLabelsScreen {
    @Unique
    private static final int VISIBLE_ROWS = 7;
    @Unique
    private static final int FIRST_ROW_Y = 18;
    @Unique
    private static final int ROW_HEIGHT = 20;
    @Unique
    private static final int LABEL_LEFT = 27;
    @Unique
    private static final int DISCOUNTED_LABEL_LEFT = 42;
    @Unique
    private static final int RESULT_CENTER_X = 81;
    @Unique
    private static final int TOGGLE_X = 5;
    @Unique
    private static final int TOGGLE_Y = 4;

    @Shadow
    int scrollOff;

    private MerchantScreenMixin(MerchantMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void villagertradelabels$addToggle(CallbackInfo ci) {
        addRenderableWidget(new LabelToggle(leftPos + TOGGLE_X, topPos + TOGGLE_Y));
    }

    @Override
    public void villagertradelabels$renderLabels(GuiGraphics guiGraphics) {
        MerchantOffers offers = menu.getOffers();
        if (!ClientConfig.showLabels() || offers.isEmpty() || minecraft.level == null) {
            return;
        }
        List<MerchantOffer> lockedOffers = TradePeekCompat.lockedOffers(this);
        int total = offers.size() + lockedOffers.size();
        int firstIndex = total > VISIBLE_ROWS ? scrollOff : 0;
        RegistryAccess registries = minecraft.level.registryAccess();
        guiGraphics.nextStratum();
        for (int row = 0; row < VISIBLE_ROWS && firstIndex + row < total; row++) {
            int index = firstIndex + row;
            MerchantOffer offer = index < offers.size() ? offers.get(index) : lockedOffers.get(index - offers.size());
            int rowY = topPos + FIRST_ROW_Y + row * ROW_HEIGHT;
            boolean discounted = offer.getCostA().getCount() != offer.getBaseCostA().getCount();
            int left = leftPos + (discounted ? DISCOUNTED_LABEL_LEFT : LABEL_LEFT);
            TradeLabels.of(offer.getResult(), registries).ifPresent(label -> TradeLabelRenderer.render(
                    guiGraphics, font, label, left, rowY + ROW_HEIGHT - 1, leftPos + RESULT_CENTER_X));
        }
    }
}
