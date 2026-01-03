package com.strategicgame.units;

import java.util.Random;

/**
 * Soldier unit - balanced infantry unit with moderate stats.
 * Example of Inheritance and Polymorphism.
 * 
 */
public class Soldier extends Unit {
    private static final int HEALTH = 20;
    private static final int ATTACK = 10;
    private static final int DEFENSE = 5;
    private static final int RANGE = 1;
    private static final int COST = 50;
    private static final int MOVEMENT_SPEED = 3;

    public Soldier() {
        super("Soldier", HEALTH, ATTACK, DEFENSE, RANGE, COST, MOVEMENT_SPEED);
    }

    @Override
    public int calculateDamage(Unit target) {
        // Base damage minus target defense with variance
        int baseDamage = Math.max(1, this.getAttack() - target.getDefense());
        Random random = new Random();
        int variance = random.nextInt(5) - 2; // -2 to +2
        int finalDamage = baseDamage + variance;
        return Math.max(1, finalDamage);
    }
}
