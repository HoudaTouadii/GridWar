package com.strategicgame.map;

import java.util.*;

/**
 * Position represents coordinates on the game map.
 * Provides utility methods for distance calculations and neighbor finding.
 * ! the development of some functionalities that concern position wasn't finished
 * ! that's why you may find some unused methodes
 */
public class Position {
    private final int x; //abscisse
    private final int y; //ordonnée

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets X coordinate.
     * @return X position
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Y coordinate.
     * @return Y position
     */
    public int getY() {
        return y;
    }

    /**
     * Calculates Manhattan distance to another position.
     * 
     * @param other The other position
     * @return Manhattan distance
     */
    public int getManhattanDistance(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    /**
     * Calculates Euclidean distance to another position.
     * 
     * @param other The other position
     * @return Euclidean distance
     */
    public double getEuclideanDistance(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Gets all adjacent positions (8-way adjacency).
     * 
     * @param maxX Maximum X coordinate
     * @param maxY Maximum Y coordinate
     * @return List of adjacent positions
     */
    public List<Position> getAdjacentPositions(int maxX, int maxY) {
        List<Position> adjacent = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int newX = x + dx;
                int newY = y + dy;
                if (newX >= 0 && newX < maxX && newY >= 0 && newY < maxY) {
                    adjacent.add(new Position(newX, newY));
                }
            }
        }
        return adjacent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Affichage au format Position(x,y)
     */
    @Override
    public String toString() {
        return String.format("Position(%d, %d)", x, y);
    }
}
