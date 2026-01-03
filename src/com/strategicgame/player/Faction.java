package com.strategicgame.player;

/**
 * Faction enum represents different factions in the game.
 * Each faction can have unique bonuses and characteristics.
 * 
 */
public enum Faction {
    EMPIRE("Empire", "Balanced faction with strong economy", 1.0f),
    KINGDOM("Kingdom", "Military-focused with strong units", 1.1f),
    REBELLION("Rebellion", "Resource-poor but agile", 0.9f);

    private final String name;
    private final String description;
    private final float unitStrengthBonus;

    Faction(String name, String description, float unitStrengthBonus) {
        this.name = name;
        this.description = description;
        this.unitStrengthBonus = unitStrengthBonus;
    }

    public String getDisplayName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getUnitStrengthBonus() {
        return unitStrengthBonus;
    }
}
