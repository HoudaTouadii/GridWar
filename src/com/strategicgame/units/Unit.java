package com.strategicgame.units;

import com.strategicgame.map.Position;
import com.strategicgame.player.Player;

/**
 * Abstract base class for all game units.
 * Defines the contract for unit behavior - HP, attack, defense, movement.
 * Implements Encapsulation and Abstraction principles from POO.
 * 
 */
public abstract class Unit {
    private final String name;
    private final int baseCost;
    private int health;
    private final int maxHealth;
    private final int attack;
    private final int defense;
    private final int range;
    private final int movementSpeed;
    
    private Position position;
    private Player owner;
    private boolean hasMovedThisTurn;

    protected Unit(String name, int health, int attack, int defense, 
                   int range, int cost, int movementSpeed) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.range = range;
        this.baseCost = cost;
        this.movementSpeed = movementSpeed;
        this.hasMovedThisTurn = false;
    }

    /**
     * Calculates damage this unit deals to a target.
     * To be overridden by subclasses for specialized damage calculations.
     * 
     * @param target Target unit
     * @return Damage amount
     */
    public abstract int calculateDamage(Unit target);

    /**
     * Gets the unit type name. 
     * @return Unit type name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets current health. 
     * @return Health points
     */
    public int getHealth() {
        return health;
    }

    /**
     * Takes damage and reduces health. 
     * @param damage Damage to take
     */
    public void takeDamage(int damage) {
        this.health = Math.max(0, health - damage);
    }

    /**
     * Heals the unit (up to max health). 
     * @param amount Amount to heal
     */
    public void heal(int amount) {
        this.health = Math.min(maxHealth, health + amount);
    }

    /**
     * Checks if unit is alive.
     * @return true if health > 0
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Gets attack power. 
     * @return Attack value
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Gets defense value. 
     * @return Defense value
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Gets attack range. 
     * @return Range in tiles
     */
    public int getRange() {
        return range;
    }

    /**
     * Gets cost to produce this unit. 
     * @return Resource cost
     */
    public int getCost() {
        return baseCost;
    }

    /**
     * Gets movement speed per turn. 
     * @return Movement range
     */
    public int getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Gets unit position. 
     * @return Current position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets unit position. 
     * @param position New position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets unit owner. 
     * @return Owning player
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets unit owner. 
     * @param owner New owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Checks if unit has already moved this turn. 
     * @return true if moved
     */
    public boolean hasMovedThisTurn() {
        return hasMovedThisTurn;
    }

    /**
     * Marks unit as having moved this turn.
     */
    public void moveTurn() {
        this.hasMovedThisTurn = true;
    }

    /**
     * Resets movement for new turn.
     */
    public void resetTurn() {
        this.hasMovedThisTurn = false;
    }

    /**
     * Gets health percentage. 
     * @return Health as percentage (0-100)
     */
    public int getHealthPercentage() {
        return (health * 100) / maxHealth;
    }

    /**
     * Affichage au format : nom(HP:currentHP/maxHP, ATK, DEF, RNG)
     */
    @Override
    public String toString() {
        return String.format("%s(HP:%d/%d, ATK:%d, DEF:%d, RNG:%d)", 
            name, health, maxHealth, attack, defense, range);
    }


    protected int id;
    /**
     * id de l'unité dans la liste des unités du joueur
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
