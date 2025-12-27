package com.strategicgame.buildings;

import com.strategicgame.map.Position;
import com.strategicgame.player.Player;
import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * Abstract base class for all buildings in the game.
 * Defines contract for building behavior - construction, production, and effects.
 * Implements Abstraction principle - concrete buildings extend this.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public abstract class Building {
    private final String name;
    private final Map<ResourceType, Integer> constructionCost;
    private final int constructionTime;
    private final int health;
    private final int armor;
    
    private Position position;
    private Player owner;
    private int remainingConstructionTime;
    private boolean isConstructed;
    private int currentHealth;

    protected Building(String name, int health, int armor,
                      Map<ResourceType, Integer> cost, int constructionTime) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.constructionCost = new HashMap<>(cost);
        this.constructionTime = constructionTime;
        this.remainingConstructionTime = constructionTime;
        this.isConstructed = false;
        this.currentHealth = health;
    }

    /**
     * Gets the building name.
     * 
     * @return Building name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets construction cost.
     * 
     * @return Map of resource types and costs
     */
    public Map<ResourceType, Integer> getConstructionCost() {
        return new HashMap<>(constructionCost);
    }

    /**
     * Gets construction time in turns.
     * 
     * @return Construction turns
     */
    public int getConstructionTime() {
        return constructionTime;
    }

    /**
     * Gets health points.
     * 
     * @return Max health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets current health.
     * 
     * @return Current health
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Damages the building.
     * 
     * @param damage Damage to apply
     */
    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - armor);
        currentHealth = Math.max(0, currentHealth - actualDamage);
    }

    /**
     * Gets armor value.
     * 
     * @return Armor points
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Gets position of building.
     * 
     * @return Position on map
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets position of building.
     * 
     * @param position New position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets owner player.
     * 
     * @return Owning player
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets owner player.
     * 
     * @param owner New owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Updates construction progress.
     * 
     * @return true if construction completed
     */
    public boolean updateConstruction() {
        if (isConstructed) {
            return true;
        }
        
        remainingConstructionTime--;
        if (remainingConstructionTime <= 0) {
            isConstructed = true;
            onConstructionComplete();
            return true;
        }
        return false;
    }

    /**
     * Checks if building is fully constructed.
     * 
     * @return true if finished
     */
    public boolean isConstructed() {
        return isConstructed;
    }

    /**
     * Advances this building's construction.
     */
    public void completeConstruction() {
        isConstructed = true;
        remainingConstructionTime = 0;
        onConstructionComplete();
    }

    /**
     * Called when construction is completed - override for special effects.
     */
    protected void onConstructionComplete() {
        // Override in subclasses
    }

    /**
     * Gets remaining construction time.
     * 
     * @return Turns until complete
     */
    public int getRemainingConstructionTime() {
        return Math.max(0, remainingConstructionTime);
    }

    /**
     * Called each turn to produce resources or effects.
     */
    public abstract void produce();

    /**
     * Gets production rate per turn.
     * 
     * @return Production amount
     */
    public abstract int getProductionRate();

    /**
     * Checks if building is destroyed.
     * 
     * @return true if health <= 0
     */
    public boolean isDestroyed() {
        return currentHealth <= 0;
    }

    @Override
    public String toString() {
        return String.format("%s(HP:%d, Constructed:%b)", 
            name, currentHealth, isConstructed);
    }
}
