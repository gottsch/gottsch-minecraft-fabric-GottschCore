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
 * Fluent builder for structured admin/debug reports sent to Minecraft chat.
 *
 * <pre>
 * List&lt;Text&gt; lines = CommandResponseFormatter.report("Crop Inspect")
 *     .row("Position", pos.toShortString())
 *     .row("Age", age)
 *     .section("Timing")
 *         .row("lastCallGameTime", state.getLastCallGameTime())
 *     .build();
 * </pre>
 *
 * Label-column width is computed globally from the longest label across all rows.
 *
 * @author Mark Gottschling on May 11, 2026
 */
public class ReportBuilder {

    private final String title;
    private final List<Row> rows = new ArrayList<>();
    private int maxLabelLen = 0;

    ReportBuilder(String title) {
        this.title = title;
    }

    /** Adds a key/value row where the value is formatted via {@code String.valueOf(value)}. */
    public ReportBuilder row(String label, Object value) {
        rows.add(new Row(label, Text.literal(String.valueOf(value)).formatted(Formatting.WHITE)));
        if (label.length() > maxLabelLen) maxLabelLen = label.length();
        return this;
    }

    /** Adds a key/value row with a pre-styled value component. */
    public ReportBuilder row(String label, Text value) {
        rows.add(new Row(label, value));
        if (label.length() > maxLabelLen) maxLabelLen = label.length();
        return this;
    }

    /** Adds a grey {@code ─── name ───} section sub-header. */
    public ReportBuilder section(String name) {
        rows.add(new Row(null, Text.literal("─── " + name + " ───").formatted(Formatting.GRAY)));
        return this;
    }

    /** Adds a blank line. */
    public ReportBuilder blank() {
        rows.add(new Row(null, Text.literal(NEWLINE)));
        return this;
    }

    /** Adds a grey italic free-form note line. */
    public ReportBuilder note(String text) {
        rows.add(new Row(null, Text.literal(text).formatted(Formatting.GRAY, Formatting.ITALIC)));
        return this;
    }

    /**
     * Builds and returns the complete list of {@link Text} lines.
     * The result starts with a {@code ═══ title ═══} header and ends with
     * a {@code ══} footer bar.
     */
    public List<Text> build() {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.literal("═══ " + title + " ═══").formatted(Formatting.YELLOW, Formatting.BOLD));
        for (Row row : rows) {
            if (row.label() == null) {
                lines.add(row.value());
            } else {
                String padded = String.format("  %-" + maxLabelLen + "s : ", row.label());
                MutableText line = Text.literal(padded).formatted(Formatting.GRAY)
                        .append(row.value());
                lines.add(line);
            }
        }
        lines.add(Text.literal(TITLE_BAR).formatted(Formatting.YELLOW));
        return lines;
    }

    private record Row(String label, Text value) {}
}
