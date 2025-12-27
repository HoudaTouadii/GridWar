package com.strategicgame.combat;

import com.strategicgame.units.Unit;
import java.util.Random;

/**
 * CombatResolver handles all combat calculations and resolutions.
 * Implements the Single Responsibility Principle.
 * Encapsulates combat logic including damage calculations and outcomes.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class CombatResolver {
    private final Random random;
    private CombatObserver observer;

    public CombatResolver() {
        this.random = new Random();
        this.observer = null;
    }

    /**
     * Sets combat observer for event notifications.
     * 
     * @param observer The observer to notify of combat events
     */
    public void setObserver(CombatObserver observer) {
        this.observer = observer;
    }

    /**
     * Initiates combat between two units.
     * 
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @return true if defender is killed, false otherwise
     */
    public boolean resolveCombat(Unit attacker, Unit defender) {
        if (!canAttack(attacker, defender)) {
            return false;
        }

        // Check critical hit
        boolean isCritical = random.nextDouble() < 0.15;
        
        // Calculate damage
        int baseDamage = calculateDamage(attacker, defender);
        int finalDamage = isCritical ? (int)(baseDamage * 1.5) : baseDamage;

        // Apply damage
        defender.takeDamage(finalDamage);

        // Notify observer
        if (observer != null) {
            observer.onCombat(attacker, defender, finalDamage, isCritical);
        }

        // Return if defender is killed
        return !defender.isAlive();
    }

    /**
     * Checks if attacker can target defender.
     * 
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @return true if attack is valid
     */
    private boolean canAttack(Unit attacker, Unit defender) {
        if (!attacker.isAlive() || !defender.isAlive()) {
            return false;
        }

        if (attacker.getOwner() == defender.getOwner()) {
            return false; // Can't attack own units
        }

        // Check range (simplified - no actual position checking here)
        return attacker.getRange() >= 1;
    }

    /**
     * Calculates damage using unit's calculateDamage method.
     * 
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @return Damage amount
     */
    private int calculateDamage(Unit attacker, Unit defender) {
        return attacker.calculateDamage(defender);
    }

    /**
     * Simulates a multi-turn skirmish between units.
     * 
     * @param unit1 First unit
     * @param unit2 Second unit
     * @return The surviving unit, or null if both die
     */
    public Unit simulateSkirmish(Unit unit1, Unit unit2) {
        int rounds = 0;
        int maxRounds = 20; // Prevent infinite loops

        while (unit1.isAlive() && unit2.isAlive() && rounds < maxRounds) {
            resolveCombat(unit1, unit2);
            if (unit2.isAlive()) {
                resolveCombat(unit2, unit1);
            }
            rounds++;
        }

        if (unit1.isAlive()) return unit1;
        if (unit2.isAlive()) return unit2;
        return null;
    }
}
