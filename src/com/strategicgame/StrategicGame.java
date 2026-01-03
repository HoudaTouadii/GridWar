package com.strategicgame;

import com.strategicgame.core.*;
import com.strategicgame.player.Player;
import com.strategicgame.units.*;
import com.strategicgame.buildings.*;
import com.strategicgame.combat.CombatResolver;
import com.strategicgame.resources.ResourceType;
import com.strategicgame.ui.GameUI;
import java.util.*;
import com.strategicgame.map.*;


/**
 * StrategicGame is the main concrete game implementation.
 * Extends the abstract Game class and implements the actual game loop.
 * Entry point for the application.
 * 
 * !vous allez peut etre constater que certaines fonctionnalités prévues
 * !(des méthodes dans les classes) sont pas implémentées ici
 * !mais grace au fait que le projet soit conçu selon les principes
 * !de la POO favorisant l'extensibilité, l'intégration future de ces fonctionnalités
 * !ou meme d'autres fcts est possible via des implémentations d'interfaces
 * !ou extensions de classes. Merci :)
 * 
 */
public class StrategicGame extends Game {
    private GameUI ui;
    private static final int MAP_WIDTH = 20;
    private static final int MAP_HEIGHT = 20;
    private static final int PLAYER_COUNT = 2;
    private AiController aiController;

    /**
     * Main entry point for the game.
     */
   public static void main(String[] args) {
    GameUI ui = new GameUI();              // créer l'UI console
    int choice = ui.displayMainMenu();     // afficher le menu principal

    if (choice == 1) {
        // Nouvelle partie
        StrategicGame game = new StrategicGame();
        game.ui = ui;                      // réutiliser la même UI
        game.run();                        // lance initialize() puis la boucle update/render
    } else if (choice == 2) {
        // Charger partie (pas implémenté :( )
        ui.showMessage("Load Game not implemented yet.");
    } else if (choice == 3) {
        // Quitter le jeu
        ui.showMessage("Goodbye!");
        System.exit(0);
    } else {
        // nombre qui n'existe pas dans le menu
        ui.showError("Invalid choice!");
        System.exit(0);
    }
}

    @Override
    public void initialize() {
        isRunning = true;
        gameManager = GameManager.getInstance();
        aiController = new AiController();
        ui = new GameUI();
        
        System.out.println("Initializing GridWar...");
        
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

            // Place starting units on the map by default
            Position pos1 = new Position(0, 0);
            Position pos2 = new Position(1, 0);
            Position pos3 = new Position(2, 0);

            Tile tile1 = gameManager.getGameMap().getTile(pos1);
            Tile tile2 = gameManager.getGameMap().getTile(pos2);
            Tile tile3 = gameManager.getGameMap().getTile(pos3);

            tile1.setOccupant(soldier1);
            tile2.setOccupant(soldier2);
            tile3.setOccupant(archer);
            
            System.out.println("Placed unit id=" + soldier1.getId() + " at (0,0) for player " + player.getName());
            System.out.println("Placed unit id=" + soldier2.getId() + " at (1,0) for player " + player.getName());
            System.out.println("Placed unit id=" + archer.getId()   + " at (2,0) for player " + player.getName());


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
    int currentIndex = gameManager.getCurrentPlayerIndex();

    // Player at index 0 = human, other = AI
    if (currentIndex == 0) {

        // Human turn with menu
        ui.displayGameStatus();

        boolean turnComplete = false;
        while (!turnComplete) {
            int choice = ui.displayTurnMenu();
            switch (choice) {
                case 1:
                    ui.displayUnits(currentPlayer);
                    break;
                case 2:
                    ui.displayBuildings(currentPlayer);
                    break;
                case 3:
                    ui.displayMap(gameManager.getGameMap());
                    break;
                case 4:
                    trainUnit(currentPlayer);
                    break;
                case 5:
                    buildBuilding(currentPlayer);
                    break;
                case 6:
                    moveUnit(currentPlayer);
                    break;
                case 7:
                    attack(currentPlayer);
                    break;
                case 8:
                    currentPlayer.endTurn();
                    gameManager.nextTurn();
                    turnComplete = true;
                    break;
                default:
                    ui.showError("Invalid choice!");
            }
        }
    } else {
        // AI turn
        aiController.playTurn(currentPlayer, gameManager);
        gameManager.nextTurn();
    }

    // Check win condition
    if (gameManager.isGameOver()) {
        currentState = GameState.GAME_OVER;
        isRunning = false;
    }
}


    /**
     * Handles unit training. 
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


    /**
     * Attack units or buildings of the ennemy 
     * @param attackerPlayer
     */
    
    private void attack(Player attackerPlayer) {
    Player defenderPlayer = gameManager.getPlayers().stream()
            .filter(p -> p != attackerPlayer)
            .findFirst()
            .orElse(null);

    if (defenderPlayer == null) {
        ui.showError("No enemy player found!");
        return;
    }

    // Choix du type de cible
    ui.showMessage("Choose attack target type:");
    ui.showMessage("1. Enemy unit");
    ui.showMessage("2. Enemy building");
    int targetType = ui.readInt();

    if (targetType == 1) {
        // === ATTAQUE D'UNITE  ===
        if (attackerPlayer.getUnits().isEmpty() || defenderPlayer.getUnits().isEmpty()) {
            ui.showError("No units available for combat!");
            return;
        }
        // choisir l'unité attaquante
        ui.showMessage("Choose your attacking unit:");
        ui.displayUnits(attackerPlayer);
        int attackerIndex = ui.readInt() -1;
        if (attackerIndex < 0 || attackerIndex >= attackerPlayer.getUnits().size()) {
            ui.showError("Invalid unit selection!");
            return;
        }
        //choisir l'unité à cibler
        ui.showMessage("Choose enemy unit to attack:");
        ui.displayUnits(defenderPlayer);
        int defenderIndex = ui.readInt() -1;
        if (defenderIndex < 0 || defenderIndex >= defenderPlayer.getUnits().size()) {
            ui.showError("Invalid target selection!");
            return;
        }

        com.strategicgame.units.Unit attacker =
                attackerPlayer.getUnits().get(attackerIndex);
        com.strategicgame.units.Unit defender =
                defenderPlayer.getUnits().get(defenderIndex);

        CombatResolver resolver = new CombatResolver();
        boolean defenderKilled = resolver.resolveCombat(attacker, defender);

        ui.showMessage(attacker.getName() + " attacked "
                + defender.getName() + " (HP now: " + defender.getHealth() + ")");

        if (defenderKilled) {
            ui.showMessage("The enemy unit was defeated!");
            defenderPlayer.removeUnit(defender);
        }

        } else if (targetType == 2) {
        // === ATTAQUE DE BUILDING (CommandCenter, etc.) ===
        if (defenderPlayer.getBuildings().isEmpty()) {
            ui.showError("Enemy has no buildings to attack!");
            return;
        }

        // Choisir l’unité attaquante
        if (attackerPlayer.getUnits().isEmpty()) {
            ui.showError("You have no units to attack with!");
            return;
        }

        ui.showMessage("Choose your attacking unit:");
        ui.displayUnits(attackerPlayer);
        int attackerIndex = ui.readInt() -1;
        if (attackerIndex < 0 || attackerIndex >= attackerPlayer.getUnits().size()) {
            ui.showError("Invalid unit selection!");
            return;
        }

        com.strategicgame.units.Unit attacker =
                attackerPlayer.getUnits().get(attackerIndex);

        // Choisir le bâtiment à cibler
        ui.showMessage("Choose enemy building to attack:");
        ui.displayBuildings(defenderPlayer);
        int buildingIndex = ui.readInt() -1;
        if (buildingIndex < 0 || buildingIndex >= defenderPlayer.getBuildings().size()) {
            ui.showError("Invalid building selection!");
            return;
        }

        com.strategicgame.buildings.Building targetBuilding =
                defenderPlayer.getBuildings().get(buildingIndex);

        // Appliquer les dégâts 
        int damage = attacker.getAttack(); 
        targetBuilding.takeDamage(damage);

        ui.showMessage(attacker.getName() + " attacked building "
                + targetBuilding.getName() + " (HP now: "
                + targetBuilding.getCurrentHealth() + ")");

        // Si détruit, retirer le bâtiment
        if (targetBuilding.isDestroyed()) {
            ui.showMessage("The enemy building was destroyed!");
            defenderPlayer.removeBuilding(targetBuilding);
        }

    } else {
        ui.showError("Invalid choice!");
    }
    }


  /** Deplacement sur map
   * @param player
   */

    private void moveUnit(Player player) {
    System.out.println("=== Move Unit ===");

    // 1. Sélection de l'unité
    System.out.println("Your units:");
    for (Unit u : player.getUnits()) {
    System.out.println(" - id=" + u.getId() + ", name=" + u.getName());
}

    System.out.print("Enter unit ID to move: ");
    int unitId = ui.readInt();
    
    Unit unit = player.getUnitById(unitId);
    if (unit == null) {
        System.out.println("No unit found with this ID.");
        return;
    }
    
    System.out.println("Trying to move unit id=" + unit.getId() + " of player " + player.getName());
    Position debugPos = gameManager.getGameMap().findUnitPosition(unit);
    System.out.println("Debug position = " + debugPos);


    // 2. Saisie de la position cible
    System.out.print("Enter target X (0.." + (gameManager.getGameMap().getWidth() - 1) + "): ");
    int x = ui.readInt();
    System.out.print("Enter target Y (0.." + (gameManager.getGameMap().getHeight() - 1) + "): ");
    int y = ui.readInt();

    Position target = new Position(x, y);

    // 3. Vérifications

    // 3.1. Limites de la carte
    if (!(gameManager.getGameMap()).isWithinBounds(target)) {
        System.out.println("Target position is out of bounds.");
        return;
    }

    Tile targetTile = gameManager.getGameMap().getTile(target);

    // 3.2. Passable ?
    if (!targetTile.isPassable()) {
        System.out.println("You cannot move to this tile (not passable).");
        return;
    }

    // 3.3. Occupation
    if (!targetTile.isEmpty()) {
        System.out.println("This tile is already occupied.");
        return;
    }

    // 4. Trouver la position actuelle de l'unité
    Position currentPos = gameManager.getGameMap().findUnitPosition(unit);
    if (currentPos == null) {
        System.out.println("Could not find the current position of this unit on the map.");
        return;
    }

    // 5. Appliquer le mouvement
    Tile currentTile = gameManager.getGameMap().getTile(currentPos);
    currentTile.clearOccupant();     // enlève l'unité de l'ancienne case
    targetTile.setOccupant(unit);    // place l'unité sur la nouvelle case

    // 6. Feedback
    System.out.println("Unit " + unitId + " moved from " +
            "(" + currentPos.getX() + "," + currentPos.getY() + ") to " +
            "(" + target.getX() + "," + target.getY() + ").");
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
