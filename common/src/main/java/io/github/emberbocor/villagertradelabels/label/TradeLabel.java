package io.github.emberbocor.villagertradelabels.label;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public record TradeLabel(Component name, Component suffix) {
    private static final String ELLIPSIS = "…";

    public FormattedCharSequence fit(Font font, int maxWidth) {
        FormattedText full = FormattedText.composite(name, suffix);
        if (font.width(full) <= maxWidth) {
            return Language.getInstance().getVisualOrder(full);
        }
        Component ellipsis = Component.literal(ELLIPSIS).withStyle(name.getStyle());
        int nameWidth = maxWidth - font.width(suffix) - font.width(ellipsis);
        FormattedText text = nameWidth >= 0
                ? FormattedText.composite(head(font, name, nameWidth), ellipsis, suffix)
                : FormattedText.composite(head(font, full, maxWidth - font.width(ellipsis)), ellipsis);
        return Language.getInstance().getVisualOrder(text);
    }

    private static FormattedText head(Font font, FormattedText text, int maxWidth) {
        List<Segment> segments = new ArrayList<>();
        font.substrByWidth(text, Math.max(0, maxWidth)).visit((style, content) -> {
            segments.add(new Segment(style, content));
            return Optional.empty();
        }, Style.EMPTY);
        while (!segments.isEmpty() && segments.getLast().content().isBlank()) {
            segments.removeLast();
        }
        if (!segments.isEmpty()) {
            Segment last = segments.removeLast();
            segments.add(new Segment(last.style(), last.content().stripTrailing()));
        }
        return FormattedText.composite(segments.stream().map(segment -> FormattedText.of(segment.content(), segment.style())).toList());
    }

    private record Segment(Style style, String content) {
    }
}
