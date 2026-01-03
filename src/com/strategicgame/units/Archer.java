package com.strategicgame.units;

import java.util.Random;

/**
 * Archer unit - ranged unit with high range but lower health.
 * Specialized damage calculation with range bonus.
 * 
 */
public class Archer extends Unit {
    private static final int HEALTH = 15;
    private static final int ATTACK = 12;
    private static final int DEFENSE = 3;
    private static final int RANGE = 4; // High range
    private static final int COST = 60;
    private static final int MOVEMENT_SPEED = 2;

    public Archer() {
        super("Archer", HEALTH, ATTACK, DEFENSE, RANGE, COST, MOVEMENT_SPEED);
    }

    @Override
    public int calculateDamage(Unit target) {
        // Archers deal bonus damage at range
        int baseDamage = Math.max(1, this.getAttack() - target.getDefense());
        int rangeBonusMultiplier = 2; // Double damage at distance
        int totalDamage = baseDamage * rangeBonusMultiplier;
        
        Random random = new Random();
        int variance = random.nextInt(7) - 3; // -3 to +3
        
        return Math.max(1, totalDamage + variance);
    }
}
