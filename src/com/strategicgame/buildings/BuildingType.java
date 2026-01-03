package com.strategicgame.buildings;

/**
 * Enum for building types.
 * Used by the BuildingFactory for type-safe building creation.
 */
public enum BuildingType {
    COMMAND_CENTER("CommandCenter"),
    TRAINING_CAMP("TrainingCamp"),
    MINE("Mine"),
    FARM("Farm"),
    SAWMILL("Sawmill");

    private final String displayName;

    BuildingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
