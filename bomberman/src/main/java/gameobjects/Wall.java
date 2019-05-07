package gameobjects;

import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    private Explosion explosion = null;    // The explosion object that will destroy this wall

    private boolean breakable;

    public Wall(float xPos, float yPos, BufferedImage sprite, boolean isBreakable) {
        super(xPos, yPos, sprite);
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
