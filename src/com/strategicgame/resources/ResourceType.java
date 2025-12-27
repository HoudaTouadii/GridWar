package com.strategicgame.resources;

/**
 * Enum representing different types of resources in the game.
 * Resources are collected and consumed to build units and structures.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public enum ResourceType {
    GOLD("Gold", "Precious metal for trade and construction", 100),
    WOOD("Wood", "Building material from forests", 75),
    STONE("Stone", "Heavy material from mountains", 50),
    FOOD("Food", "Sustenance for units and population", 80);

    private final String name;
    private final String description;
    private final int baseValue;

    ResourceType(String name, String description, int baseValue) {
        this.name = name;
        this.description = description;
        this.baseValue = baseValue;
    }

    public String getDisplayName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBaseValue() {
        return baseValue;
    }
}
