package com.strategicgame.map;

/**
 * Enum representing different tile types on the game map.
 * Each type has different properties and bonuses/maluses.
 * 
 */
public enum TileType {
    GRASS("Grass", true, 1.0f, "Standard terrain"),
    WATER("Water", false, 0.5f, "Impassable for land units"),
    MOUNTAIN("Mountain", true, 1.3f, "Difficult terrain, stone bonus"),
    FOREST("Forest", true, 1.1f, "Wood bonus, movement penalty"),
    DESERT("Desert", true, 0.8f, "Poor resources"),
    SWAMP("Swamp", true, 1.5f, "Difficult movement, disease risk");

    private final String name;
    private final boolean passable;
    private final float resourceBonus;
    private final String description;

    TileType(String name, boolean passable, float resourceBonus, String description) {
        this.name = name;
        this.passable = passable;
        this.resourceBonus = resourceBonus;
        this.description = description;
    }

    public String getDisplayName() {
        return name;
    }

    public boolean isPassable() {
        return passable;
    }

    public float getResourceBonus() {
        return resourceBonus;
    }

    public String getDescription() {
        return description;
    }
}
