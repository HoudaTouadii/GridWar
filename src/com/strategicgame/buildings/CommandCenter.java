package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * CommandCenter building - central control structure of each faction.
 * Required for base control. Provides bonuses to unit production.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class CommandCenter extends Building {
    private static final int HEALTH = 100;
    private static final int ARMOR = 5;
    private static final int PRODUCTION_RATE = 5; // Gold per turn
    private static final int CONSTRUCTION_TIME = 5;

    public CommandCenter() {
        super("CommandCenter", HEALTH, ARMOR, createCost(), CONSTRUCTION_TIME);
    }

    private static Map<ResourceType, Integer> createCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, 200);
        cost.put(ResourceType.WOOD, 150);
        cost.put(ResourceType.STONE, 100);
        return cost;
    }

    @Override
    public void produce() {
        if (isConstructed() && getOwner() != null) {
            getOwner().getResourceManager().addResource(ResourceType.GOLD, PRODUCTION_RATE);
        }
    }

    @Override
    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    @Override
    protected void onConstructionComplete() {
        System.out.println("CommandCenter construction complete!");
    }
}
