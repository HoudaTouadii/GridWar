package com.strategicgame.core;

import com.strategicgame.player.Player;
import com.strategicgame.map.GameMap;
import com.strategicgame.ui.GameUI;
import java.util.*;

/**
 * Singleton GameManager responsible for game state management and turn coordination.
 * Implements the Singleton pattern for centralized game control.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class GameManager {
    private static GameManager instance;
    
    private GameMap gameMap;
    private List<Player> players;
    private int currentPlayerIndex;
    private int turnNumber;
    private GameUI ui;
    private boolean gameOver;
    private Player winner;

    private GameManager() {
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.turnNumber = 1;
        this.gameOver = false;
    }

    /**
     * Gets the singleton instance of GameManager.
     * 
     * @return The GameManager instance
     */
    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Initializes the game with map and players.
     * 
     * @param mapWidth Width of the game map
     * @param mapHeight Height of the game map
     * @param playerCount Number of players
     */
    public void initializeGame(int mapWidth, int mapHeight, int playerCount) {
        this.gameMap = new GameMap(mapWidth, mapHeight);
        gameMap.generateMap();
        
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player("Player " + (i + 1), i);
            players.add(player);
        }
    }

    /**
     * Processes the next turn.
     */
    public void nextTurn() {
        if (gameOver) return;

        // Current player ends their turn
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.endTurn();

        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        // If we've cycled through all players, increment turn number
        if (currentPlayerIndex == 0) {
            turnNumber++;
        }

        // Check win condition
        checkWinCondition();
    }

    /**
     * Checks if there's a winner.
     */
    private void checkWinCondition() {
        int activePlayers = 0;
        for (Player player : players) {
            if (player.hasUnits() || player.hasBuildings()) {
                activePlayers++;
                winner = player;
            }
        }

        if (activePlayers <= 1) {
            gameOver = true;
        }
    }

    /**
     * Gets the current active player.
     * 
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Gets all players in the game.
     * 
     * @return List of players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Gets the game map.
     * 
     * @return The game map instance
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Gets the current turn number.
     * 
     * @return Current turn
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Checks if the game is over.
     * 
     * @return true if game is finished
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the winner if game is over.
     * 
     * @return The winning player, or null if no winner
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Saves the current game state.
     * 
     * @param filename File to save to
     */
    public void saveGame(String filename) {
        // Implementation for saving game state
    }

    /**
     * Loads a game from file.
     * 
     * @param filename File to load from
     */
    public void loadGame(String filename) {
        // Implementation for loading game state
    }

    /**
     * Resets the game manager for a new game.
     */
    public void reset() {
        players.clear();
        currentPlayerIndex = 0;
        turnNumber = 1;
        gameOver = false;
        winner = null;
        gameMap = null;
    }
}
