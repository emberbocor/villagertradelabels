package io.github.emberbocor.villagertradelabels.label;

import org.joml.Matrix3x2fStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public final class TradeLabelRenderer {
    private static final int TEXT_HEIGHT = 8;
    private static final int PADDING = 3;
    private static final int BOX_HEIGHT = TEXT_HEIGHT + 2 * PADDING;
    private static final int VANILLA_INSET = 4;
    private static final float SIZE_FACTOR = 1.1F;
    private static final float TEXT_WIDTH_FACTOR = 0.64F;

    private TradeLabelRenderer() {
    }

    public static void extract(GuiGraphicsExtractor graphics, Font font, TradeLabel label, int left, int bottom, int centerX) {
        float pixelScale = pixelScale();
        int maxTextWidth = Mth.floor(2 * (centerX - left) / pixelScale) - 2 * PADDING;
        FormattedCharSequence text = label.fit(font, Mth.floor(maxTextWidth * TEXT_WIDTH_FACTOR));
        int textWidth = font.width(text);
        if (textWidth < 2) {
            return;
        }
        int width = textWidth + 2 * PADDING;
        int boxX = -(width / 2);

        Matrix3x2fStack pose = graphics.pose();
        pose.pushMatrix();
        pose.translate(centerX, bottom);
        pose.scale(pixelScale * SIZE_FACTOR, pixelScale * SIZE_FACTOR);
        TooltipRenderUtil.extractTooltipBackground(graphics,
                boxX + VANILLA_INSET, VANILLA_INSET - BOX_HEIGHT, width - 2 * VANILLA_INSET, BOX_HEIGHT - 2 * VANILLA_INSET, null);
        graphics.text(font, text, boxX + PADDING, PADDING - BOX_HEIGHT, 0xFFFFFFFF, true);
        pose.popMatrix();
    }

    private static float pixelScale() {
        int guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        return (float) Math.max(1, Math.round(guiScale / 2.0)) / guiScale;
    }
}
