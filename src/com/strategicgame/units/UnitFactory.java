package com.strategicgame.units;

/**
 * UnitFactory implements the Factory Design Pattern.
 * Responsible for creating units - encapsulates unit instantiation logic.
 * Supports the Open/Closed Principle - easy to add new unit types.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class UnitFactory {
    
    /**
     * Creates a unit based on the specified type.
     * 
     * @param unitType Type of unit to create (case-insensitive)
     * @return A new Unit instance, or null if type not recognized
     */
    public static Unit createUnit(String unitType) {
        switch (unitType.toLowerCase()) {
            case "soldier":
                return new Soldier();
            case "archer":
                return new Archer();
            case "cavalry":
                return new Cavalry();
            default:
                System.err.println("Unknown unit type: " + unitType);
                return null;
        }
    }

    /**
     * Creates a unit by enum value.
     * 
     * @param type UnitType enum value
     * @return A new Unit instance
     */
    public static Unit createUnit(UnitType type) {
        return createUnit(type.name());
    }

    /**
     * Gets all available unit types.
     * 
     * @return Array of unit type names
     */
    public static String[] getAvailableUnits() {
        return new String[]{"Soldier", "Archer", "Cavalry"};
    }

    /**
     * Gets cost of a unit type without instantiating it.
     * 
     * @param unitType Type name
     * @return Cost in resources
     */
    public static int getUnitCost(String unitType) {
        Unit unit = createUnit(unitType);
        return unit != null ? unit.getCost() : -1;
    }
}
