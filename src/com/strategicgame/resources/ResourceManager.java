package com.strategicgame.resources;

import java.util.*;

/**
 * ResourceManager handles all resource operations for a player.
 * Implements the Single Responsibility Principle - only manages resources.
 * Uses HashMap for O(1) resource lookups.
 * 
 */
public class ResourceManager {
    private final Map<ResourceType, Integer> resources;
    private final Map<ResourceType, Integer> productionRate;

    public ResourceManager() {
        this.resources = new HashMap<>();
        this.productionRate = new HashMap<>();
        
        // Initialize all resource types with starting amounts
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 500); // Starting resources
            productionRate.put(type, 10); // Base production per turn
        }
    }

    /**
     * Gets the current amount of a specific resource. 
     * @param type The resource type
     * @return Amount of resource
     */
    public int getResource(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }

    /**
     * Gets all current resources. 
     * @return Map of resource types to amounts
     */
    public Map<ResourceType, Integer> getAllResources() {
        return new HashMap<>(resources);
    }

    /**
     * Adds resources to the manager. 
     * @param type The resource type
     * @param amount Amount to add
     */
    public void addResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        resources.put(type, current + amount);
    }

    /**
     * Removes resources from the manager. 
     * @param type The resource type
     * @param amount Amount to remove
     * @return true if successful, false if insufficient resources
     */
    public boolean removeResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        if (current >= amount) {
            resources.put(type, current - amount);
            return true;
        }
        return false;
    }

    /**
     * Checks if player can afford a resource cost. 
     * @param cost Map of resources and amounts needed
     * @return true if player has enough of all resources
     */
    public boolean canAfford(Map<ResourceType, Integer> cost) {
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            if (getResource(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Spends multiple resources at once. 
     * @param cost Map of resources and amounts to spend
     * @return true if successful, false if insufficient resources
     */
    public boolean spend(Map<ResourceType, Integer> cost) {
        if (!canAfford(cost)) {
            return false;
        }
        
        for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
            removeResource(entry.getKey(), entry.getValue());
        }
        return true;
    }

    /**
     * Produces resources based on production rate.
     */
    public void produceResources() {
        for (ResourceType type : ResourceType.values()) {
            int production = productionRate.getOrDefault(type, 0);
            addResource(type, production);
        }
    }

    /**
     * Sets the production rate for a resource type. 
     * @param type The resource type
     * @param rate Production amount per turn
     */
    public void setProductionRate(ResourceType type, int rate) {
        productionRate.put(type, rate);
    }

    /**
     * Gets the production rate for a resource type. 
     * @param type The resource type
     * @return Production amount per turn
     */
    public int getProductionRate(ResourceType type) {
        return productionRate.getOrDefault(type, 0);
    }

    /**
     * Provides a string representation of current resources. 
     * @return Formatted resource string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Resources: ");
        for (ResourceType type : ResourceType.values()) {
            sb.append(type.getDisplayName()).append("=")
              .append(getResource(type)).append(" ");
        }
        return sb.toString();
    }
}
