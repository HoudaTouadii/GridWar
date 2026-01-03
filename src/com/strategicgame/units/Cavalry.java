package com.strategicgame.units;

import java.util.Random;

/**
 * Cavalry unit - fast, strong unit with high movement speed.
 * Specialized charge damage calculation.
 * 
 */
public class Cavalry extends Unit {
    private static final int HEALTH = 25;
    private static final int ATTACK = 14;
    private static final int DEFENSE = 4;
    private static final int RANGE = 1;
    private static final int COST = 80;
    private static final int MOVEMENT_SPEED = 5; // Fastest unit

    public Cavalry() {
        super("Cavalry", HEALTH, ATTACK, DEFENSE, RANGE, COST, MOVEMENT_SPEED);
    }

    @Override
    public int calculateDamage(Unit target) {
        // Cavalry charges deal extra damage
        int baseDamage = Math.max(1, this.getAttack() - target.getDefense());
        int chargeBonus = 5; // Fixed charge bonus
        int totalDamage = baseDamage + chargeBonus;
        
        Random random = new Random();
        int variance = random.nextInt(6) - 2; // -2 to +3
        
        return Math.max(2, totalDamage + variance);
    }
}
