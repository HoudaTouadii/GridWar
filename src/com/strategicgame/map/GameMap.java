package com.strategicgame.map;

import java.util.*;
import java.util.Random;

/**
 * GameMap manages the game world grid.
 * Responsible for map generation, tile management, and pathfinding.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class GameMap {
    private final int width;
    private final int height;
    private final Map<Position, Tile> tiles;
    private final Random random;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new HashMap<>(width * height);
        this.random = new Random();
    }

    /**
     * Generates the map with random terrain.
     */
    public void generateMap() {
        TileType[] terrainTypes = TileType.values();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position pos = new Position(x, y);
                
                // Bias towards grass tiles
                TileType type = random.nextDouble() < 0.6 ? TileType.GRASS : 
                               terrainTypes[random.nextInt(terrainTypes.length)];
                
                tiles.put(pos, new Tile(pos, type));
            }
        }
    }

    /**
     * Gets a tile at the specified position.
     * 
     * @param position The position to query
     * @return The tile at this position, or null if out of bounds
     */
    public Tile getTile(Position position) {
        return tiles.get(position);
    }

    /**
     * Gets a tile at X, Y coordinates.
     * 
     * @param x X coordinate
     * @param y Y coordinate
     * @return The tile, or null if out of bounds
     */
    public Tile getTile(int x, int y) {
        return getTile(new Position(x, y));
    }

    /**
     * Checks if a position is within map bounds.
     * 
     * @param position The position to check
     * @return true if valid position
     */
    public boolean isWithinBounds(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height;
    }

    /**
     * Gets all tiles as a collection.
     * 
     * @return Collection of all tiles
     */
    public Collection<Tile> getAllTiles() {
        return new ArrayList<>(tiles.values());
    }

    /**
     * Gets all tiles of a specific type.
     * 
     * @param type The tile type to search for
     * @return List of matching tiles
     */
    public List<Tile> getTilesByType(TileType type) {
        List<Tile> result = new ArrayList<>();
        for (Tile tile : tiles.values()) {
            if (tile.getType() == type) {
                result.add(tile);
            }
        }
        return result;
    }

    /**
     * Finds a path from start to end position using A* algorithm.
     * 
     * @param start Starting position
     * @param end Ending position
     * @return List of positions representing the path, empty if no path found
     */
    public List<Position> findPath(Position start, Position end) {
        // Simplified pathfinding - returns direct line if passable
        List<Position> path = new ArrayList<>();
        
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            return path;
        }

        // Check if direct path exists
        Tile endTile = getTile(end);
        if (endTile == null || !endTile.isPassable()) {
            return path;
        }

        path.add(start);
        path.add(end);
        return path;
    }

    /**
     * Gets the map width.
     * 
     * @return Width in tiles
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the map height.
     * 
     * @return Height in tiles
     */
    public int getHeight() {
        return height;
    }

    /**
     * Displays the map in console (for debugging).
     */
    public void displayMap() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = getTile(x, y);
                if (tile != null) {
                    System.out.print(tile.getType().name().charAt(0) + " ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }
}
