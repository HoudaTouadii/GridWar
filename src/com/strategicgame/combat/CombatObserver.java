package com.strategicgame.combat;

import com.strategicgame.units.Unit;

/**
 * CombatObserver interface - implements Observer Design Pattern.
 * Allows systems to be notified when combat events occur.
 * Supports loose coupling between combat and other systems.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public interface CombatObserver {
    
    /**
     * Called when combat occurs between two units.
     * 
     * @param attacker The attacking unit
     * @param defender The defending unit
     * @param damage Damage dealt
     * @param isCritical Whether the hit was critical
     */
    void onCombat(Unit attacker, Unit defender, int damage, boolean isCritical);

    /**
     * Called when a unit is killed.
     * 
     * @param unit The unit that was killed
     * @param killer The unit that caused the death
     */
    void onUnitKilled(Unit unit, Unit killer);

    /**
     * Called when a unit is severely wounded.
     * 
     * @param unit The wounded unit
     * @param healthPercent Current health percentage
     */
    void onUnitWounded(Unit unit, int healthPercent);
}
