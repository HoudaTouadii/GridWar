package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
import java.util.*;

/**
 * ResourceMine building - produces stone resources.
 * Built on mountain terrain for bonus production.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class ResourceMine extends Building {
    private static final int HEALTH = 40;
    private static final int ARMOR = 2;
    private static final int PRODUCTION_RATE = 15; // Stone per turn
    private static final int CONSTRUCTION_TIME = 3;

    public ResourceMine() {
        super("Mine", HEALTH, ARMOR, createCost(), CONSTRUCTION_TIME);
    }

    private static Map<ResourceType, Integer> createCost() {
        Map<ResourceType, Integer> cost = new HashMap<>();
        cost.put(ResourceType.GOLD, 50);
        cost.put(ResourceType.WOOD, 50);
        return cost;
    }

    @Override
    public void produce() {
        if (isConstructed() && getOwner() != null) {
            getOwner().getResourceManager().addResource(ResourceType.STONE, PRODUCTION_RATE);
        }
    }

    @Override
    public int getProductionRate() {
        return PRODUCTION_RATE;
    }

    @Override
    protected void onConstructionComplete() {
        System.out.println("Mine construction complete - Stone production started!");
    }
}
