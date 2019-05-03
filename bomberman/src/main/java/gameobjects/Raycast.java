package gameobjects;

import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Raycast extends GameObject {

    public static class Up extends Raycast {
        @Override
        public void update() {
            while (!this.isHit) {
                this.position.addY(-32);
            }
        }
    }

    public static class Down extends Raycast {
        @Override
        public void update() {
            while (!this.isHit) {
                this.position.addY(32);
            }
        }
    }

    public static class Left extends Raycast {
        @Override
        public void update() {
            while (!this.isHit) {
                this.position.addX(-32);
            }
        }
    }

    public static class Right extends Raycast {
        @Override
        public void update() {
            while (!this.isHit) {
                this.position.addX(32);
            }
        }
    }

    protected boolean isHit;
    private GameObject hitObject = null;

    Raycast() {
        this.isHit = false;
        this.position = new Vector2D();
        this.width = 2;
        this.height = 2;
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
    }

    public void hit(GameObject target) {
        this.isHit = true;
        this.hitObject = target;
    }

    public GameObject getHit() {
        System.out.println("RAYCAST HIT: " + this.hitObject);
        return this.hitObject;
    }

    @Override
    public void collides(GameObject collidingObj) {
    }

    @Override
    public void drawImage(Graphics g) {

    }

}
