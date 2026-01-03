package com.strategicgame.player;

import com.strategicgame.resources.ResourceManager;
import com.strategicgame.units.Unit;
import com.strategicgame.buildings.Building;
import java.util.*;

/**
 * Player class represents a player in the game (human or AI).
 * Manages resources, units, buildings, and faction information.
 * Uses composition with ResourceManager.
 * 
 */
public class Player {
    private final String name;
    private final int playerId;
    private final Faction faction;
    private final ResourceManager resourceManager;
    private final List<Unit> units;
    private int nextUnitId = 1;  // compteur d'ID des unités pour un joueur
    private final List<Building> buildings;
    private int score;
    private boolean hasLost;

    public Player(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
        this.faction = Faction.values()[playerId % Faction.values().length];
        this.resourceManager = new ResourceManager();
        this.units = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.score = 0;
        this.hasLost = false;
    }

    /**
     * Gets player name.
     * @return Player name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets player ID. 
     * @return Player ID
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Gets player faction.
     * @return Faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Gets resource manager. 
     * @return ResourceManager instance
     */
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * Gets all units owned by this player. 
     * @return List of units
     */
    public List<Unit> getUnits() {
        return units;
    }
  
    public Unit getUnitById(int id) {
    for (Unit u : units) {       // on parcourt les unités de CE joueur
        if (u.getId() == id) {   // on compare avec l'id stocké dans Unit
            return u;            // on a trouvé la bonne unité
        }
    }
    return null;                 // aucune unité avec cet id
}


    /**
     * Adds a unit to this player's army. 
     * @param unit The unit to add
     */
    public void addUnit(Unit unit) {
        unit.setOwner(this);
        unit.setId(nextUnitId);  // donner un ID à l'unité (1,2,3,...)
        nextUnitId++;
        units.add(unit);
    }

    /**
     * Removes a unit from this player's army. 
     * @param unit The unit to remove
     */
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    /**
     * Checks if player has any units. 
     * @return true if has units
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Gets all buildings owned by this player. 
     * @return List of buildings
     */
    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    /**
     * Adds a building to this player's territory. 
     * @param building The building to add
     */
    public void addBuilding(Building building) {
        building.setOwner(this);
        buildings.add(building);
    }

    /**
     * Removes a building from this player's territory. 
     * @param building The building to remove
     */
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    /**
     * Checks if player has any buildings. 
     * @return true if has buildings
     */
    public boolean hasBuildings() {
        return !buildings.isEmpty();
    }

    /**
     * Processes end of turn - produce resources and update buildings.
     */
    public void endTurn() {
        // Produce resources from buildings
        for (Building building : buildings) {
            if (building.isConstructed()) {
                building.produce();
            }
        }

        // Update construction progress
        for (Building building : buildings) {
            if (!building.isConstructed()) {
                building.updateConstruction();
            }
        }

        // Reset unit movement
        for (Unit unit : units) {
            unit.resetTurn();
        }

        // Manage population with food
        int foodAvailable = resourceManager.getResource(
            com.strategicgame.resources.ResourceType.FOOD);
        int unitsCount = units.size();
        int foodNeeded = unitsCount * 2; // 2 food per unit per turn

        if (foodAvailable < foodNeeded) {
            // Lose some units due to starvation
            int unitsToLose = (foodNeeded - foodAvailable) / 2;
            for (int i = 0; i < unitsToLose && !units.isEmpty(); i++) {
                removeUnit(units.get(0));
            }
        }
    }

    /**
     * Gets player score. 
     * @return Score value
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds to player score. 
     * @param points Points to add
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Checks if player has lost. 
     * @return true if defeated
     */
    public boolean hasLost() {
        return hasLost;
    }

    /**
     * Marks player as defeated.
     */
    public void lose() {
        this.hasLost = true;
    }

    /**
     * Gets a summary of player status. 
     * @return Status string
     */
    public String getStatus() {
        return String.format("%s [%s] - Units: %d, Buildings: %d, Score: %d",
            name, faction.getDisplayName(), units.size(), buildings.size(), score);
    }

    @Override
    public String toString() {
        return getStatus();
    }
}
