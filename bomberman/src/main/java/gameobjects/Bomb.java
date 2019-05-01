package gameobjects;

import util.ResourceCollection;
import util.Vector2D;

import java.awt.geom.Rectangle2D;

public class Bomb extends GameObject {

    private Bomber bomber;

    private int firepower;
    private int timer;
    private int startTime;


    public Bomb(int firepower, int timer, Bomber bomber) {
        super(ResourceCollection.Images.BOMB.getImage());
        this.position = new Vector2D();
        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
        this.firepower = firepower;
        this.timer = timer;
        this.bomber = bomber;
        this.startTime = 0;
    }

    private void explode() {
        this.bomber.restoreAmmo();
    }

    @Override
    public void update() {
        if (this.startTime++ >= this.timer) {
            this.explode();
            this.destroy();
        }
    }

    @Override
    public void collides(GameObject collidingObj) {

    }

    @Override
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {

    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

}
