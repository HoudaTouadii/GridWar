package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * ResourceFarm building - produces food resources.
 * Built on grassland for best production.
 * 
 */
public class ResourceFarm extends Building {
    private static final int HEALTH = 35;
    private static final int ARMOR = 1;
    private static final int PRODUCTION_RATE = 20; // Food per turn
    private static final int CONSTRUCTION_TIME = 2;

    public ResourceFarm() {
        super("Farm", HEALTH, ARMOR, createCost(), CONSTRUCTION_TIME);
    }

    private static Map<ResourceType, Integer> createCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, 30);
        cost.put(ResourceType.WOOD, 40);
        return cost;
    }

    @Override
    public void produce() {
        if (isConstructed() && getOwner() != null) {
            getOwner().getResourceManager().addResource(ResourceType.FOOD, PRODUCTION_RATE);
        }
    }

    @Override
    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    @Override
    protected void onConstructionComplete() {
        System.out.println("Farm construction complete - Food production started!");
    }
}
