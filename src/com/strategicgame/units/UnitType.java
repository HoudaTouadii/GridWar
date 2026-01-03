package com.strategicgame.units;

/**
 * Enum for unit types.
 * Used by the UnitFactory for type-safe unit creation.
 * 
 */
public enum UnitType {
    SOLDIER("Soldier", 50),
    ARCHER("Archer", 60),
    CAVALRY("Cavalry", 80);

    private final String displayName;
    private final int cost;

    UnitType(String displayName, int cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCost() {
        return cost;
    }
}
