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

    TileObject(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
    }

    public abstract boolean isBreakable();

    protected boolean checkExplosion() {
        return this.isBreakable() && this.explosionContact != null && this.explosionContact.isDestroyed();
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
