package com.strategicgame.ui;

import com.strategicgame.core.GameManager;
import com.strategicgame.player.Player;
import com.strategicgame.units.Unit;
import com.strategicgame.buildings.Building;
import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * GameUI handles console-based user interface.
 * Displays game state, player information, and handles menu interactions.
 * Uses the Single Responsibility Principle - only handles UI display.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class GameUI {
    private final GameManager gameManager;
    private final Scanner scanner;

    public GameUI() {
        this.gameManager = GameManager.getInstance();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu.
     * 
     * @return Selected menu option
     */
    public int displayMainMenu() {
        System.out.println("\n=== STRATEGIC GAME ===");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
        return scanner.nextInt();
    }

    /**
     * Displays game status for the current player.
     */
    public void displayGameStatus() {
        Player currentPlayer = gameManager.getCurrentPlayer();
        System.out.println("\n--- Turn " + gameManager.getTurnNumber() + " ---");
        System.out.println("Current Player: " + currentPlayer.getName());
        System.out.println("Faction: " + currentPlayer.getFaction().getDisplayName());
        displayResources(currentPlayer);
    }

    /**
     * Displays player resources.
     * 
     * @param player The player to display resources for
     */
    private void displayResources(Player player) {
        System.out.println("\nResources:");
        Map<ResourceType, Integer> resources = player.getResourceManager().getAllResources();
        for (ResourceType type : ResourceType.values()) {
            System.out.printf("  %s: %d\n", type.getDisplayName(), 
                resources.getOrDefault(type, 0));
        }
    }

    /**
     * Displays player's units.
     * 
     * @param player The player to display units for
     */
    public void displayUnits(Player player) {
        List<Unit> units = player.getUnits();
        System.out.println("\nUnits (" + units.size() + "):");
        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            System.out.printf("  %d. %s - HP: %d/%d, ATK: %d, DEF: %d\n",
                i + 1, unit.getName(), unit.getHealth(), 20,
                unit.getAttack(), unit.getDefense());
        }
    }

    /**
     * Displays player's buildings.
     * 
     * @param player The player to display buildings for
     */
    public void displayBuildings(Player player) {
        List<Building> buildings = player.getBuildings();
        System.out.println("\nBuildings (" + buildings.size() + "):");
        for (int i = 0; i < buildings.size(); i++) {
            Building building = buildings.get(i);
            String status = building.isConstructed() ? "Complete" : 
                "Constructing (" + building.getRemainingConstructionTime() + " turns left)";
            System.out.printf("  %d. %s - %s\n", i + 1, building.getName(), status);
        }
    }

    /**
     * Displays turn options menu.
     * 
     * @return Selected option
     */
    public int displayTurnMenu() {
        System.out.println("\n--- Your Turn ---");
        System.out.println("1. View Units");
        System.out.println("2. View Buildings");
        System.out.println("3. Train Unit");
        System.out.println("4. Build Building");
        System.out.println("5. End Turn");
        System.out.print("Choose action: ");
        return scanner.nextInt();
    }

    /**
     * Displays available units to train.
     * 
     * @return Selected unit type
     */
    public String displayUnitTrainingMenu() {
        System.out.println("\nSelect unit type to train:");
        System.out.println("1. Soldier (50 Gold)");
        System.out.println("2. Archer (60 Gold)");
        System.out.println("3. Cavalry (80 Gold)");
        System.out.print("Choose unit: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return "Soldier";
            case 2: return "Archer";
            case 3: return "Cavalry";
            default: return null;
        }
    }

    /**
     * Displays available buildings to construct.
     * 
     * @return Selected building type
     */
    public String displayBuildingMenu() {
        System.out.println("\nSelect building to construct:");
        System.out.println("1. CommandCenter (200 Gold, 150 Wood, 100 Stone)");
        System.out.println("2. TrainingCamp (100 Gold, 75 Wood)");
        System.out.println("3. Farm (30 Gold, 40 Wood)");
        System.out.println("4. Mine (50 Gold, 50 Wood)");
        System.out.println("5. Sawmill (40 Gold, 30 Stone)");
        System.out.print("Choose building: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return "CommandCenter";
            case 2: return "TrainingCamp";
            case 3: return "Farm";
            case 4: return "Mine";
            case 5: return "Sawmill";
            default: return null;
        }
    }

    /**
     * Displays a game over message.
     * 
     * @param winner The winning player
     */
    public void displayGameOver(Player winner) {
        System.out.println("\n=== GAME OVER ===");
        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
            System.out.println("Final Score: " + winner.getScore());
        } else {
            System.out.println("Draw!");
        }
    }

    /**
     * Displays an error message.
     * 
     * @param message The error message
     */
    public void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    /**
     * Displays an info message.
     * 
     * @param message The info message
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
