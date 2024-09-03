package com.tyler.narutoorigin.ranks;

import net.minecraft.util.text.TextFormatting;

public enum Rank {
    HOKAGE("Hokage", TextFormatting.GREEN),
    AMEKAGE("Amekage", TextFormatting.GRAY),
    MIZUKAGE("Mizukage", TextFormatting.DARK_BLUE),
    AKATSUKI("Akatsuki", TextFormatting.RED);

    private final String name;
    private final TextFormatting color;

    Rank(String name, TextFormatting color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public TextFormatting getColor() {
        return color;
    }

    public String getDisplayName() {
        return color + this.name;
    }

    public static Rank fromString(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
}
