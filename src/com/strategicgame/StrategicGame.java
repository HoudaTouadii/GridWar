package com.strategicgame;

import com.strategicgame.core.*;
import com.strategicgame.player.Player;
import com.strategicgame.units.*;
import com.strategicgame.buildings.*;
import com.strategicgame.resources.ResourceType;
import com.strategicgame.ui.GameUI;
import java.util.*;

/**
 * StrategicGame is the main concrete game implementation.
 * Extends the abstract Game class and implements the actual game loop.
 * Entry point for the application.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class StrategicGame extends Game {
    private GameUI ui;
    private static final int MAP_WIDTH = 20;
    private static final int MAP_HEIGHT = 20;
    private static final int PLAYER_COUNT = 2;

    /**
     * Main entry point for the game.
     * 
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        StrategicGame game = new StrategicGame();
        game.run();
    }

    @Override
    public void initialize() {
        isRunning = true;
        gameManager = GameManager.getInstance();
        ui = new GameUI();
        
        System.out.println("Initializing Strategic Game...");
        
        // Initialize game with map and players
        gameManager.initializeGame(MAP_WIDTH, MAP_HEIGHT, PLAYER_COUNT);
        
        // Give each player starting units
        initializePlayerStartingResources();
        
        currentState = GameState.PLAYING;
        System.out.println("Game initialized successfully!");
    }

    /**
     * Initializes starting units and buildings for players.
     */
    private void initializePlayerStartingResources() {
        for (Player player : gameManager.getPlayers()) {
            // Add starting units
            Unit soldier1 = new Soldier();
            Unit soldier2 = new Soldier();
            Unit archer = new Archer();
            
            player.addUnit(soldier1);
            player.addUnit(soldier2);
            player.addUnit(archer);
            
            // Add starting building
            Building commandCenter = new CommandCenter();
            commandCenter.completeConstruction();
            player.addBuilding(commandCenter);
            
            System.out.println(player.getName() + " [" + 
                player.getFaction().getDisplayName() + "] ready!");
        }
    }

    @Override
    public void update(double delta) {
        if (currentState != GameState.PLAYING) {
            return;
        }

        Player currentPlayer = gameManager.getCurrentPlayer();
        
        // Display game status
        ui.displayGameStatus();
        
        // Player turn loop
        boolean turnComplete = false;
        while (!turnComplete) {
            int choice = ui.displayTurnMenu();
            
            switch (choice) {
                case 1: // View Units
                    ui.displayUnits(currentPlayer);
                    break;
                case 2: // View Buildings
                    ui.displayBuildings(currentPlayer);
                    break;
                case 3: // Train Unit
                    trainUnit(currentPlayer);
                    break;
                case 4: // Build Building
                    buildBuilding(currentPlayer);
                    break;
                case 5: // End Turn
                    currentPlayer.endTurn();
                    gameManager.nextTurn();
                    turnComplete = true;
                    break;
                default:
                    ui.showError("Invalid choice!");
            }
        }

        // Check win condition
        if (gameManager.isGameOver()) {
            currentState = GameState.GAME_OVER;
            isRunning = false;
        }
    }

    /**
     * Handles unit training.
     * 
     * @param player The player training units
     */
    private void trainUnit(Player player) {
        String unitType = ui.displayUnitTrainingMenu();
        if (unitType == null) {
            return;
        }

        Unit newUnit = UnitFactory.createUnit(unitType);
        if (newUnit == null) {
            ui.showError("Invalid unit type!");
            return;
        }

        // Check if player has training camp
        boolean hasTrainingCamp = player.getBuildings().stream()
            .anyMatch(b -> b instanceof TrainingCamp && b.isConstructed());

        if (!hasTrainingCamp) {
            ui.showError("You need a TrainingCamp to train units!");
            return;
        }

        // Check resources
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, newUnit.getCost());

        if (!player.getResourceManager().spend(cost)) {
            ui.showError("Not enough resources!");
            return;
        }

        player.addUnit(newUnit);
        ui.showMessage("Trained " + unitType + " successfully!");
    }

    /**
     * Handles building construction.
     * 
     * @param player The player building
     */
    private void buildBuilding(Player player) {
        String buildingType = ui.displayBuildingMenu();
        if (buildingType == null) {
            return;
        }

        Building newBuilding = BuildingFactory.createBuilding(buildingType);
        if (newBuilding == null) {
            ui.showError("Invalid building type!");
            return;
        }

        // Check resources
        if (!player.getResourceManager().spend(newBuilding.getConstructionCost())) {
            ui.showError("Not enough resources!");
            return;
        }

        player.addBuilding(newBuilding);
        ui.showMessage("Started construction of " + buildingType + 
            " (" + newBuilding.getConstructionTime() + " turns)");
    }

    @Override
    public void render() {
        if (currentState == GameState.GAME_OVER) {
            ui.displayGameOver(gameManager.getWinner());
        }
    }

    @Override
    protected void cleanup() {
        System.out.println("Shutting down game...");
    }
}
