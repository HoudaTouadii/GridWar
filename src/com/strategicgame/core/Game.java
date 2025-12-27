package com.strategicgame.core;

/**
 * Abstract base class for the main game engine.
 * Defines the game loop and state management contract.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public abstract class Game {
    protected GameManager gameManager;
    protected GameState currentState;
    protected volatile boolean isRunning;
    protected long fps = 60;
    protected long frameTime = 1000 / fps;

    /**
     * Initializes the game with necessary components.
     */
    public abstract void initialize();

    /**
     * Updates game logic based on delta time.
     * 
     * @param delta Time elapsed since last update in milliseconds
     */
    public abstract void update(double delta);

    /**
     * Renders the current game state.
     */
    public abstract void render();

    /**
     * Main game loop - continuously updates and renders until game stops.
     */
    public void run() {
        initialize();
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long currentTime = System.currentTimeMillis();
            long delta = currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= frameTime) {
                update(delta);
                render();
                delta = 0;
            }
        }

        cleanup();
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Cleanup resources before game shutdown.
     */
    protected void cleanup() {
        // Override in subclasses for cleanup logic
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
