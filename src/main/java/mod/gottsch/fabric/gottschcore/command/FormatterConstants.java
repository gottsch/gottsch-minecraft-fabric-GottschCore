/*
 * This file is part of GottschCore.
 * Copyright (c) 2026 Mark Gottschling (gottsch)
 *
 * GottschCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GottschCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GottschCore.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.gottschcore.command;

import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;

/**
 * Shared display constants, style helpers, and interactive component builders
 * used by {@link CommandResponseFormatter} and dependent mods.
 *
 * @author Mark Gottschling on May 11, 2026
 */
public final class FormatterConstants {

    // ===== BOX-DRAWING STRINGS =====

    /** Double-line horizontal bar (U+2550). Used as the outer title separator. */
    public static final String TITLE_BAR   = "══════════════════════════════";
    /** Single-line horizontal bar (U+2500). Used as section sub-headers. */
    public static final String SECTION_BAR = "──────────────────────────────";
    public static final String BRANCH      = "├─ ";
    public static final String LAST_BRANCH = "└─ ";
    public static final String VERTICAL    = "│  ";

    /** Empty string that renders as a blank line in Minecraft chat. */
    public static final String NEWLINE = "";

    // ===== SEMANTIC ICON COMPONENTS =====

    public static final Text ICON_SUCCESS = bold("✔", Formatting.GREEN);
    public static final Text ICON_FAILURE = bold("✘", Formatting.RED);
    public static final Text ICON_WARNING = bold("⚠", Formatting.YELLOW);
    public static final Text ICON_INFO    = bold("ℹ", Formatting.AQUA);

    private FormatterConstants() {}

    // ===== NEWLINE =====

    public static Text newline() {
        return Text.literal(NEWLINE);
    }

    // ===== STYLE HELPERS =====

    public static Text bold(String text, Formatting color) {
        return Text.literal(text).formatted(color, Formatting.BOLD);
    }

    public static Text grey(String text) {
        return Text.literal(text).formatted(Formatting.GRAY);
    }

    public static Text aqua(String text) {
        return Text.literal(text).formatted(Formatting.AQUA);
    }

    public static Text gold(String text) {
        return Text.literal(text).formatted(Formatting.GOLD);
    }

    // ===== LOCATION FORMATTING =====

    /** Returns a gold-coloured "x, y, z" component from a BlockPos. */
    public static Text formatLocation(BlockPos pos) {
        return gold(pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    // ===== INTERACTIVE (CLICK / HOVER) BUILDERS =====

    /**
     * A text component that runs {@code command} on click and shows {@code hover} on hover.
     */
    public static Text clickable(String text, String command, Text hover) {
        return Text.literal(text).fillStyle(Style.EMPTY
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover)));
    }

    /**
     * A text component that fills the chat bar with {@code command} on click and shows
     * {@code hover} on hover.
     */
    public static Text suggestable(String text, String command, Text hover) {
        return Text.literal(text).fillStyle(Style.EMPTY
                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover)));
    }

    /**
     * Wraps an existing icon component with a run-command click event and hover tooltip.
     * The icon's text and colour are preserved; only interaction style is added.
     */
    public static Text clickableIcon(Text icon, String command, Text hover) {
        return icon.copy().fillStyle(Style.EMPTY
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover)));
    }

    /**
     * A bold green {@code [✔ Confirm]} button that runs {@code command} on click.
     * Append this to the detail line of a {@link CommandResponseFormatter#formatConfirmPrompt} message.
     */
    public static Text buildConfirmButton(String command) {
        return Text.literal(" [✔ Confirm]").fillStyle(Style.EMPTY
                .withColor(Formatting.GREEN)
                .withBold(true)
                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        Text.literal("Confirm — this cannot be undone"))));
    }
}
