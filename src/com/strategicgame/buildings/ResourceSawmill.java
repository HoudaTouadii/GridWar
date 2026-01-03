package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * ResourceSawmill building - produces wood resources.
 * Built on forest terrain for bonus production.
 * 
 */
public class ResourceSawmill extends Building {
    private static final int HEALTH = 40;
    private static final int ARMOR = 2;
    private static final int PRODUCTION_RATE = 18; // Wood per turn
    private static final int CONSTRUCTION_TIME = 3;

    public ResourceSawmill() {
        super("Sawmill", HEALTH, ARMOR, createCost(), CONSTRUCTION_TIME);
    }

    private static Map<ResourceType, Integer> createCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, 40);
        cost.put(ResourceType.STONE, 30);
        return cost;
    }

    @Override
    public void produce() {
        if (isConstructed() && getOwner() != null) {
            getOwner().getResourceManager().addResource(ResourceType.WOOD, PRODUCTION_RATE);
        }
    }

    @Override
    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    @Override
    protected void onConstructionComplete() {
        System.out.println("Sawmill construction complete - Wood production started!");
    }
}
