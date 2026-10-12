package io.github.emberbocor.villagertradelabels.client;

import org.jetbrains.annotations.Nullable;

import io.github.emberbocor.villagertradelabels.VillagerTradeLabels;
import io.github.emberbocor.villagertradelabels.config.ClientConfig;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class LabelToggle extends AbstractButton {
    public static final int SIZE = 11;
    private static final ResourceLocation ON_TEXTURE = texture("toggle_on");
    private static final ResourceLocation ON_HIGHLIGHTED_TEXTURE = texture("toggle_on_highlighted");
    private static final ResourceLocation OFF_TEXTURE = texture("toggle_off");
    private static final ResourceLocation OFF_HIGHLIGHTED_TEXTURE = texture("toggle_off_highlighted");
    private static final Component HIDE = Component.translatable("villagertradelabels.toggle.hide");
    private static final Component SHOW = Component.translatable("villagertradelabels.toggle.show");

    public LabelToggle(int x, int y) {
        super(x, y, SIZE, SIZE, HIDE);
        updateMessage();
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(VillagerTradeLabels.MODID, "textures/gui/" + name + ".png");
    }

    @Override
    public void onPress() {
        ClientConfig.setShowLabels(!ClientConfig.showLabels());
        updateMessage();
    }

    private void updateMessage() {
        Component text = ClientConfig.showLabels() ? HIDE : SHOW;
        setMessage(text);
        setTooltip(Tooltip.create(text));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(texture(), getX(), getY(), 0.0F, 0.0F, getWidth(), getHeight(), getWidth(), getHeight());
    }

    private ResourceLocation texture() {
        if (ClientConfig.showLabels()) {
            return isHovered() ? ON_HIGHLIGHTED_TEXTURE : ON_TEXTURE;
        }
        return isHovered() ? OFF_HIGHLIGHTED_TEXTURE : OFF_TEXTURE;
    }

    @Override
    public void setFocused(boolean focused) {
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Nullable
    @Override
    public ComponentPath nextFocusPath(FocusNavigationEvent event) {
        return null;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }
}
