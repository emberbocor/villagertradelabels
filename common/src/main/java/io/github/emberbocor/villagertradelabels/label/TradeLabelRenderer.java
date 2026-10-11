package io.github.emberbocor.villagertradelabels.label;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public final class TradeLabelRenderer {
    private static final float Z = 310.0F;
    private static final int TEXT_HEIGHT = 8;
    private static final int PADDING = 3;
    private static final int BOX_HEIGHT = TEXT_HEIGHT + 2 * PADDING;
    private static final int VANILLA_INSET = 4;

    private TradeLabelRenderer() {
    }

    public static void render(GuiGraphics guiGraphics, Font font, TradeLabel label, int left, int right, int bottom, int centerX) {
        float scale = scale();
        FormattedCharSequence text = label.fit(font, Mth.floor((right - left) / scale) - 2 * PADDING);
        int textWidth = font.width(text);
        if (textWidth < 2) {
            return;
        }
        int width = textWidth + 2 * PADDING;
        int half = width / 2;
        boolean centered = centerX - half * scale >= left && centerX + (width - half) * scale <= right;
        int boxX = centered ? -half : -width;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centered ? centerX : right, bottom, Z);
        guiGraphics.pose().scale(scale, scale, 1.0F);
        TooltipRenderUtil.renderTooltipBackground(guiGraphics,
                boxX + VANILLA_INSET, VANILLA_INSET - BOX_HEIGHT, width - 2 * VANILLA_INSET, BOX_HEIGHT - 2 * VANILLA_INSET, 0);
        guiGraphics.drawString(font, text, boxX + PADDING, PADDING - BOX_HEIGHT, 0xFFFFFFFF, true);
        guiGraphics.pose().popPose();
    }

    private static float scale() {
        double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        return (float) (Math.max(1, Math.round(guiScale / 2)) / guiScale);
    }
}
