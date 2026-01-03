package com.strategicgame.util;

/**
 * Constants class contains all game constants and configuration values.
 * 
 */
public class Constants {
    // Map Configuration
    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 20;
    
    // Game Configuration
    public static final int PLAYER_COUNT = 2;
    public static final int STARTING_GOLD = 500;
    public static final int STARTING_WOOD = 500;
    public static final int STARTING_STONE = 500;
    public static final int STARTING_FOOD = 500;
    
    // Combat Configuration
    public static final float CRITICAL_HIT_CHANCE = 0.15f;     // 15%
    public static final float CRITICAL_HIT_MULTIPLIER = 1.5f;  // 1.5x damage
    public static final int MIN_DAMAGE = 1;
    
    // Unit Configuration
    public static final int SOLDIER_HEALTH = 20;
    public static final int SOLDIER_ATTACK = 10;
    public static final int SOLDIER_DEFENSE = 5;
    public static final int SOLDIER_RANGE = 1;
    public static final int SOLDIER_COST = 50;
    public static final int SOLDIER_MOVEMENT = 3;
    
    public static final int ARCHER_HEALTH = 15;
    public static final int ARCHER_ATTACK = 12;
    public static final int ARCHER_DEFENSE = 3;
    public static final int ARCHER_RANGE = 4;
    public static final int ARCHER_COST = 60;
    public static final int ARCHER_MOVEMENT = 2;
    public static final int ARCHER_DAMAGE_MULTIPLIER = 2;
    
    public static final int CAVALRY_HEALTH = 25;
    public static final int CAVALRY_ATTACK = 14;
    public static final int CAVALRY_DEFENSE = 4;
    public static final int CAVALRY_RANGE = 1;
    public static final int CAVALRY_COST = 80;
    public static final int CAVALRY_MOVEMENT = 5;
    public static final int CAVALRY_CHARGE_BONUS = 5;
    
    // Building Configuration
    public static final int COMMAND_CENTER_HEALTH = 100;
    public static final int COMMAND_CENTER_ARMOR = 5;
    public static final int COMMAND_CENTER_CONSTRUCTION_TIME = 5;
    public static final int COMMAND_CENTER_PRODUCTION_RATE = 5;
    
    public static final int TRAINING_CAMP_HEALTH = 60;
    public static final int TRAINING_CAMP_ARMOR = 3;
    public static final int TRAINING_CAMP_CONSTRUCTION_TIME = 4;
    
    public static final int MINE_HEALTH = 40;
    public static final int MINE_ARMOR = 2;
    public static final int MINE_CONSTRUCTION_TIME = 3;
    public static final int MINE_PRODUCTION_RATE = 15;
    
    public static final int FARM_HEALTH = 35;
    public static final int FARM_ARMOR = 1;
    public static final int FARM_CONSTRUCTION_TIME = 2;
    public static final int FARM_PRODUCTION_RATE = 20;
    
    public static final int SAWMILL_HEALTH = 40;
    public static final int SAWMILL_ARMOR = 2;
    public static final int SAWMILL_CONSTRUCTION_TIME = 3;
    public static final int SAWMILL_PRODUCTION_RATE = 18;
    
    // Resource Production
    public static final int BASE_PRODUCTION_RATE = 10;
    
    // Game Rules
    public static final int FOOD_CONSUMPTION_PER_UNIT = 2;
    public static final int MAX_GAME_TURNS = 100;
    
    // UI Configuration
    public static final boolean SHOW_DETAILED_COMBAT = true;
    public static final boolean AUTO_SAVE_ENABLED = false;
    
    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}
