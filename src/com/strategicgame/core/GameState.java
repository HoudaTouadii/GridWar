package com.strategicgame.core;

/**
 * Enum representing different game states.
 * Supports state transitions for menu, gameplay, pause, and game over states.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public enum GameState {
    MENU("Main Menu"),
    INITIALIZING("Game Initializing"),
    PLAYING("Game In Progress"),
    PAUSED("Game Paused"),
    GAME_OVER("Game Over"),
    LOADING("Loading Game"),
    SAVING("Saving Game");

    private final String displayName;

    GameState(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name for this state.
     * 
     * @return Display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if state is playable (not menu or game over).
     * 
     * @return true if game is actively being played
     */
    public boolean isPlayable() {
        return this == PLAYING || this == PAUSED;
    }
}
