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
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;

import java.util.ArrayList;
import java.util.List;

import static mod.gottsch.fabric.gottschcore.command.FormatterConstants.*;

/**
 * Shared command/message formatting utility for all gottsch mods.
 *
 * <h3>Status messages</h3>
 * Short feedback returned after a command runs. Two shapes:
 * <ul>
 *   <li><b>Shorthand</b> — title only, for self-explanatory outcomes.
 *   <li><b>Full</b> — title + grey body, for outcomes that need context.
 * </ul>
 * All methods return {@code List<Text>} so both
 * {@code CommandSourceStack.sendSuccess()} and {@code player.sendSystemMessage()}
 * can consume the result.
 *
 * <h3>Structured reports</h3>
 * Use {@link #report(String)} to get a {@link ReportBuilder} for admin/debug
 * commands like {@code inspect} or {@code simulate}.
 *
 * <h3>Entry point families</h3>
 * <ul>
 *   <li><b>Text-based</b> ({@code formatSuccess(Text title)}, etc.) — caller
 *       controls translation. Preferred for raw-string admin output.
 *   <li><b>Lang-key-based</b> ({@code formatSuccessKey(String langKey, ...)}, etc.) — caller
 *       passes the full translation key. Preferred for player-facing localised messages.
 * </ul>
 *
 * @author Mark Gottschling on May 11, 2026
 */
public final class CommandResponseFormatter {

    private CommandResponseFormatter() {}

    // =========================================================================
    // Text-based — caller controls translation
    // =========================================================================

    /** Green ✔ — shorthand (title only). */
    public static List<Text> formatSuccess(Text title) {
        return buildShorthand(Formatting.GREEN, "✔", title);
    }

    /** Green ✔ SUCCESS — full (title + grey body). */
    public static List<Text> formatSuccess(Text title, Text body) {
        return buildFull(Formatting.GREEN, "✔", "SUCCESS", title, body);
    }

    /** Alias for {@link #formatSuccess(Text, Text)}. */
    public static List<Text> formatSuccessWithDetail(Text title, Text detail) {
        return formatSuccess(title, detail);
    }

    /** Red ✘ — shorthand. */
    public static List<Text> formatFailure(Text title) {
        return buildShorthand(Formatting.RED, "✘", title);
    }

    /** Red ✘ ERROR — full. */
    public static List<Text> formatFailure(Text title, Text body) {
        return buildFull(Formatting.RED, "✘", "ERROR", title, body);
    }

    /**
     * Red ✘ ERROR — with a bulleted list of reasons.
     * <pre>
     * ✘ ERROR
     * ══════════════════════════════
     *
     * Title
     *
     * ├─ reason 1
     * └─ reason 2
     * </pre>
     */
    public static List<Text> formatFailureWithReasons(Text title, List<Text> reasons) {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.literal("✘ ERROR").formatted(Formatting.RED, Formatting.BOLD));
        lines.add(Text.literal(TITLE_BAR).formatted(Formatting.RED));
        lines.add(newline());
        lines.add(title.copy().formatted(Formatting.WHITE, Formatting.BOLD));
        lines.add(newline());
        for (int i = 0; i < reasons.size(); i++) {
            String branch = (i == reasons.size() - 1) ? LAST_BRANCH : BRANCH;
            lines.add(Text.literal(branch).formatted(Formatting.GRAY)
                    .append(reasons.get(i).copy().formatted(Formatting.GOLD, Formatting.ITALIC)));
        }
        lines.add(newline());
        return lines;
    }

    /** Yellow ⚠ — shorthand. */
    public static List<Text> formatWarning(Text title) {
        return buildShorthand(Formatting.YELLOW, "⚠", title);
    }

    /** Yellow ⚠ WARNING — full. */
    public static List<Text> formatWarning(Text title, Text body) {
        return buildFull(Formatting.YELLOW, "⚠", "WARNING", title, body);
    }

    /** Aqua ℹ — shorthand. */
    public static List<Text> formatInfo(Text title) {
        return buildShorthand(Formatting.AQUA, "ℹ", title);
    }

    /** Aqua ℹ INFO — full. */
    public static List<Text> formatInfo(Text title, Text body) {
        return buildFull(Formatting.AQUA, "ℹ", "INFO", title, body);
    }

    // =========================================================================
    // Lang-key-based — caller passes full translation key
    // =========================================================================

    /** Green ✔ — shorthand, translation-key variant. */
    public static List<Text> formatSuccessKey(String langKey, Object... args) {
        return buildShorthandKey(Formatting.GREEN, "✔", langKey, args);
    }

    /** Green ✔ SUCCESS — full, translation-key variant. */
    public static List<Text> formatSuccessKey(String titleKey, String bodyKey, Object... bodyArgs) {
        return buildFullKey(Formatting.GREEN, "✔", "SUCCESS", titleKey, bodyKey, bodyArgs);
    }

    /** Red ✘ — shorthand, translation-key variant. */
    public static List<Text> formatFailureKey(String langKey, Object... args) {
        return buildShorthandKey(Formatting.RED, "✘", langKey, args);
    }

    /** Red ✘ ERROR — full, translation-key variant. */
    public static List<Text> formatFailureKey(String titleKey, String bodyKey, Object... bodyArgs) {
        return buildFullKey(Formatting.RED, "✘", "ERROR", titleKey, bodyKey, bodyArgs);
    }

    /** Yellow ⚠ — shorthand, translation-key variant. */
    public static List<Text> formatWarningKey(String langKey, Object... args) {
        return buildShorthandKey(Formatting.YELLOW, "⚠", langKey, args);
    }

    /** Yellow ⚠ WARNING — full, translation-key variant. */
    public static List<Text> formatWarningKey(String titleKey, String bodyKey, Object... bodyArgs) {
        return buildFullKey(Formatting.YELLOW, "⚠", "WARNING", titleKey, bodyKey, bodyArgs);
    }

    /** Aqua ℹ — shorthand, translation-key variant. */
    public static List<Text> formatInfoKey(String langKey, Object... args) {
        return buildShorthandKey(Formatting.AQUA, "ℹ", langKey, args);
    }

    /** Aqua ℹ INFO — full, translation-key variant. */
    public static List<Text> formatInfoKey(String titleKey, String bodyKey, Object... bodyArgs) {
        return buildFullKey(Formatting.AQUA, "ℹ", "INFO", titleKey, bodyKey, bodyArgs);
    }

    // =========================================================================
    // Structured report builder
    // =========================================================================

    /**
     * Returns a {@link ReportBuilder} pre-seeded with the given header title.
     * Call {@link ReportBuilder#build()} to get the final list of lines.
     */
    public static ReportBuilder report(String title) {
        return new ReportBuilder(title);
    }

    /**
     * ⚠ WARNING prompt for destructive commands that require a second confirmation step.
     *
     * <pre>
     * ⚠ WARNING
     * ══════════════════════════════
     *
     * action (bold white)
     *
     * detail (grey)  [✔ Confirm]
     * </pre>
     *
     * Build {@code confirmButton} with {@link FormatterConstants#buildConfirmButton(String)}.
     */
    public static List<Text> formatConfirmPrompt(Text action, Text detail, Text confirmButton) {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.literal("⚠ WARNING").formatted(Formatting.YELLOW, Formatting.BOLD));
        lines.add(Text.literal(TITLE_BAR).formatted(Formatting.YELLOW));
        lines.add(newline());
        lines.add(action.copy().formatted(Formatting.WHITE, Formatting.BOLD));
        lines.add(newline());
        lines.add(detail.copy().formatted(Formatting.GRAY).append(confirmButton));
        lines.add(newline());
        return lines;
    }

    // =========================================================================
    // Private helpers
    // =========================================================================

    private static List<Text> buildShorthand(Formatting color, String icon, Text title) {
        List<Text> lines = new ArrayList<>();
        MutableText titleLine = Text.literal(icon + " ").formatted(color, Formatting.BOLD)
                .append(title.copy().formatted(color, Formatting.BOLD));
        lines.add(titleLine);
        lines.add(Text.literal(TITLE_BAR).formatted(color));
        lines.add(newline());
        return lines;
    }

    private static List<Text> buildFull(
            Formatting color, String icon, String label, Text title, Text body) {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.literal(icon + " " + label).formatted(color, Formatting.BOLD));
        lines.add(Text.literal(TITLE_BAR).formatted(color));
        lines.add(newline());
        lines.add(title.copy().formatted(Formatting.WHITE, Formatting.BOLD));
        lines.add(newline());
        lines.add(body.copy().formatted(Formatting.GRAY));
        lines.add(newline());
        return lines;
    }

    private static List<Text> buildShorthandKey(Formatting color, String icon, String langKey, Object[] args) {
        List<Text> lines = new ArrayList<>();
        MutableText title = Text.translatable(langKey, args).formatted(color, Formatting.BOLD);
        lines.add(Text.literal(icon + " ").formatted(color, Formatting.BOLD).append(title));
        lines.add(Text.literal(TITLE_BAR).formatted(color));
        lines.add(newline());
        return lines;
    }

    private static List<Text> buildFullKey(
            Formatting color, String icon, String label,
            String titleKey, String bodyKey, Object[] bodyArgs) {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.literal(icon + " " + label).formatted(color, Formatting.BOLD));
        lines.add(Text.literal(TITLE_BAR).formatted(color));
        lines.add(newline());
        lines.add(Text.translatable(titleKey).formatted(Formatting.WHITE, Formatting.BOLD));
        lines.add(newline());
        lines.add(Text.translatable(bodyKey, bodyArgs).formatted(Formatting.GRAY));
        lines.add(newline());
        return lines;
    }
}
