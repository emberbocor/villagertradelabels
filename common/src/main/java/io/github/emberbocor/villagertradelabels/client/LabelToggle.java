package io.github.emberbocor.villagertradelabels.client;

import org.jetbrains.annotations.Nullable;

import io.github.emberbocor.villagertradelabels.VillagerTradeLabels;
import io.github.emberbocor.villagertradelabels.config.ClientConfig;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class LabelToggle extends AbstractButton {
    public static final int SIZE = 11;
    private static final WidgetSprites SPRITES = new WidgetSprites(
            sprite("toggle_on"), sprite("toggle_off"), sprite("toggle_on_highlighted"), sprite("toggle_off_highlighted"));
    private static final Component HIDE = Component.translatable("villagertradelabels.toggle.hide");
    private static final Component SHOW = Component.translatable("villagertradelabels.toggle.show");

    public LabelToggle(int x, int y) {
        super(x, y, SIZE, SIZE, HIDE);
        updateMessage();
    }

    private static ResourceLocation sprite(String name) {
        return ResourceLocation.fromNamespaceAndPath(VillagerTradeLabels.MODID, name);
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
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blitSprite(SPRITES.get(ClientConfig.showLabels(), isHovered()), getX(), getY(), getWidth(), getHeight());
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
