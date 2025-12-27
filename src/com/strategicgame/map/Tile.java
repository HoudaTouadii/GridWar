package com.strategicgame.map;

import com.strategicgame.player.Player;

/**
 * Tile represents a single cell on the game map.
 * Encapsulates tile type, owner, and any entities placed on it.
 * 
 * @author Strategic Game Team
 * @version 1.0
 */
public class Tile {
    private final Position position;
    private final TileType type;
    private Player owner;
    private Object occupant; // Can be a Unit or Building

    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
        this.owner = null;
        this.occupant = null;
    }

    /**
     * Gets the position of this tile.
     * 
     * @return The position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Gets the tile type.
     * 
     * @return The tile type
     */
    public TileType getType() {
        return type;
    }

    /**
     * Gets the owner of this tile.
     * 
     * @return The owning player, or null if unowned
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of this tile.
     * 
     * @param owner The new owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Checks if this tile is passable by units.
     * 
     * @return true if units can move through this tile
     */
    public boolean isPassable() {
        return type.isPassable() && occupant == null;
    }

    /**
     * Gets the occupant on this tile.
     * 
     * @return The occupant (Unit or Building), or null if empty
     */
    public Object getOccupant() {
        return occupant;
    }

    /**
     * Sets the occupant on this tile.
     * 
     * @param occupant The entity to place (Unit or Building)
     */
    public void setOccupant(Object occupant) {
        this.occupant = occupant;
    }

    /**
     * Removes the occupant from this tile.
     */
    public void clearOccupant() {
        this.occupant = null;
    }

    /**
     * Checks if this tile is empty.
     * 
     * @return true if no occupant
     */
    public boolean isEmpty() {
        return occupant == null;
    }

    @Override
    public String toString() {
        return String.format("Tile[%s, %s, Owner=%s]", 
            position, type.getDisplayName(), 
            owner != null ? owner.getName() : "None");
    }
}
