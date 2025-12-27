package com.strategicgame.util;

import com.strategicgame.map.Position;
import java.util.Random;

/**
 * GameUtils provides utility functions for the game.
 * Includes helper methods for calculations, validations, and operations.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class GameUtils {
    private static final Random random = new Random();

    /**
     * Calculates distance between two positions using Manhattan distance.
     * 
     * @param from Starting position
     * @param to Ending position
     * @return Manhattan distance
     */
    public static int calculateDistance(Position from, Position to) {
        return from.getManhattanDistance(to);
    }

    /**
     * Calculates distance using Euclidean formula.
     * 
     * @param from Starting position
     * @param to Ending position
     * @return Euclidean distance
     */
    public static double calculateEuclideanDistance(Position from, Position to) {
        return from.getEuclideanDistance(to);
    }

    /**
     * Generates random integer between min and max (inclusive).
     * 
     * @param min Minimum value
     * @param max Maximum value
     * @return Random integer
     */
    public static int getRandomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates random double between 0 and 1.
     * 
     * @return Random double
     */
    public static double getRandomDouble() {
        return random.nextDouble();
    }

    /**
     * Checks if a value is within a range.
     * 
     * @param value The value to check
     * @param min Minimum value
     * @param max Maximum value
     * @return true if value is within range
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Clamps a value between min and max.
     * 
     * @param value The value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return Clamped value
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a double value between min and max.
     * 
     * @param value The value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return Clamped value
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Calculates percentage of a value.
     * 
     * @param value The value
     * @param percentage The percentage
     * @return Calculated percentage value
     */
    public static int calculatePercentage(int value, int percentage) {
        return (value * percentage) / 100;
    }

    /**
     * Formats a number with commas for display.
     * 
     * @param number The number to format
     * @return Formatted string
     */
    public static String formatNumber(int number) {
        return String.format("%,d", number);
    }

    /**
     * Checks if position is valid (not null).
     * 
     * @param position Position to check
     * @return true if position is valid
     */
    public static boolean isValidPosition(Position position) {
        return position != null && position.getX() >= 0 && position.getY() >= 0;
    }

    /**
     * Gets time string for display (e.g., "5 turns", "1 turn").
     * 
     * @param turns Number of turns
     * @return Formatted time string
     */
    public static String formatTurns(int turns) {
        return turns == 1 ? "1 turn" : turns + " turns";
    }

    /**
     * Capitalizes first letter of a string.
     * 
     * @param str The string to capitalize
     * @return Capitalized string
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Gets plural form of a word.
     * 
     * @param word The word
     * @param count The count
     * @return Plural or singular form
     */
    public static String getPluralForm(String word, int count) {
        return count == 1 ? word : word + "s";
    }

    /**
     * Validates name string.
     * 
     * @param name The name to validate
     * @return true if name is valid
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() <= 50;
    }

    /**
     * Prevent instantiation of utility class.
     */
    private GameUtils() {
        throw new AssertionError("GameUtils class should not be instantiated");
    }
}
