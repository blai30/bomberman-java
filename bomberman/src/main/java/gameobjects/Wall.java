package gameobjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    private Explosion explosion = null;    // The explosion object that will destroy this wall

    private boolean breakable;

    public Wall(Point2D.Float position, BufferedImage sprite, boolean isBreakable) {
        super(position, sprite);
        this.breakable = isBreakable;
    }

    public boolean isBreakable() {
        return this.breakable;
    }

    @Override
    public void update() {
        // Destroy wall when explosion animation finishes
        if (this.isBreakable() && this.explosion != null && this.explosion.isDestroyed()) {
            this.destroy();
        }
    }

    @Override
    public void onDestroy() {
        double random = Math.random();
        if (random < 0.2) {
            // Random powerup at 20% chance excluding power max
            Powerup powerup = new Powerup();
//            this.instantiate(powerup);
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        if (this.isBreakable()) {
            // First explosion to collide wall
            if (this.explosion == null) {
                this.explosion = collidingObj;
            }
        }
    }

}
