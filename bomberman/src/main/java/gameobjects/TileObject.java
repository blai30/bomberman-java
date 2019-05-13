package gameobjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Bombs, walls, powerups; stationary objects that take up a tile on the map and may be destructible by explosions.
 */
public abstract class TileObject extends GameObject {

    // The explosionContact object that will destroy this object after the explosionContact animation finishes
    // and if this object is breakable
    protected Explosion explosionContact;
    protected boolean breakable;

    /**
     * Construct a tile-based object that is aligned with the grid-based map.
     * @param position Coordinates of this object in the game world
     * @param sprite Sprite of this object
     */
    TileObject(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        this.snapToGrid();
    }

    /**
     * Check if this object is allowed to be destroyed by other objects.
     * @return true = breakable, false = unbreakable
     */
    public abstract boolean isBreakable();

    /**
     * Checks if the explosion that is in contact with this object has been destroyed.
     * Used to destroy this object the moment the explosion animation finishes.
     * @return true = explosion animation finished, false = explosion is still animating
     */
    protected boolean checkExplosion() {
        return this.isBreakable() && this.explosionContact != null && this.explosionContact.isDestroyed();
    }

    /**
     * Snaps this object to be aligned to the grid with unit size 32x32.
     */
    protected void snapToGrid() {
        // Snap bombs to the grid on the map
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round(this.position.getY() / 32) * 32;
        this.position.setLocation(x, y);
    }

    /**
     * First explosionContact to collide this wall will destroy this object once its animation finishes
     * @param collidingObj First explosionContact to collide this wall
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        if (this.isBreakable()) {
            if (this.explosionContact == null) {
                this.explosionContact = collidingObj;
            }
        }
    }

}
