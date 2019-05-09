package gameobjects;

import util.GameObjectCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The base class for various types of walls. Namely hard wall and soft wall.
 */
public class Wall extends TileObject {

    /**
     * Constructs a wall object that is either breakable or not.
     * @param position Coordinates of this object in the game world
     * @param sprite Sprite of this object
     * @param isBreakable true = soft wall, false = hard wall
     */
    public Wall(Point2D.Float position, BufferedImage sprite, boolean isBreakable) {
        super(position, sprite);
        this.breakable = isBreakable;
    }

    /**
     * Destroy wall when explosion animation finishes.
     */
    @Override
    public void update() {
        if (this.checkExplosion()) {
            this.destroy();
        }
    }

    /**
     * Chance for a random powerup to spawn upon destroy.
     */
    @Override
    public void onDestroy() {
        double random = Math.random();
        if (random < 0.5) {
            Powerup powerup = new Powerup(this.position, Powerup.randomPower());
            GameObjectCollection.spawn(powerup);
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    /**
     * Checks if this is a hard wall or soft wall
     * @return true = soft wall, false = hard wall
     */
    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}
