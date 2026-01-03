package com.strategicgame.buildings;

import com.strategicgame.resources.ResourceType;
/**
 * BuildingFactory implements the Factory Design Pattern.
 * Responsible for creating buildings - encapsulates building instantiation.
 * easy to add new building types.
 * 
 */
public class BuildingFactory {
    
    /**
     * Creates a building based on the specified type.
     * 
     * @param buildingType Type of building to create (case-insensitive)
     * @return A new Building instance, or null if type not recognized
     */
    public static Building createBuilding(String buildingType) {
        switch (buildingType.toLowerCase()) {
            case "commandcenter":
            case "command":
                return new CommandCenter();
            case "trainingcamp":
            case "training":
                return new TrainingCamp();
            case "mine":
                return new ResourceMine();
            case "farm":
                return new ResourceFarm();
            case "sawmill":
                return new ResourceSawmill();
            default:
                System.err.println("Unknown building type: " + buildingType);
                return null;
        }
    }

    /**
     * Creates a building by enum value.
     * 
     * @param type BuildingType enum value
     * @return A new Building instance
     */
    public static Building createBuilding(BuildingType type) {
        return createBuilding(type.name());
    }

    /**
     * Gets all available building types. 
     * @return Array of building type names
     */
    public static String[] getAvailableBuildings() {
        return new String[]{"CommandCenter", "TrainingCamp", "Mine", "Farm", "Sawmill"};
    }

    /**
     * Gets cost of a building type without instantiating it. 
     * @param buildingType Type name
     * @return Cost map, or null if not found
     */
    public static java.util.Map<ResourceType, Integer> 
            getBuildingCost(String buildingType) {
        Building building = createBuilding(buildingType);
        return building != null ? building.getConstructionCost() : null;
    }
}
