package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * TrainingCamp building - produces military units.
 * Required to train Soldiers, Archers, and Cavalry.
 * 
 */
public class TrainingCamp extends Building {
    private static final int HEALTH = 60;
    private static final int ARMOR = 3;
    private static final int PRODUCTION_RATE = 1; // Units per turn
    private static final int CONSTRUCTION_TIME = 4;

    public TrainingCamp() {
        super("TrainingCamp", HEALTH, ARMOR, createCost(), CONSTRUCTION_TIME);
    }

    private static Map<ResourceType, Integer> createCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, 100);
        cost.put(ResourceType.WOOD, 75);
        return cost;
    }

    @Override
    public void produce() {
        if (isConstructed() && getOwner() != null) {
            // Training camp doesn't directly produce resources
            // It enables unit production through the player
        }
    }

    @Override
    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    @Override
    protected void onConstructionComplete() {
        System.out.println("TrainingCamp construction complete - units can now be trained!");
    }
}
