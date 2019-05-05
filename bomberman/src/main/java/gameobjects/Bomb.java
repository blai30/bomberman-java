package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Bomb extends GameObject {

    private Bomber bomber;

    private int firepower;
    private int timer;
    private int startTime;
    private boolean detonated;

    public Bomb(Point2D.Float spawnLocation, int firepower, int timer, Bomber bomber) {
        super(ResourceCollection.Images.BOMB.getImage());
        this.position = spawnLocation;
        this.collider = new Rectangle2D.Float(this.position.x, this.position.y, this.width, this.height);
        this.firepower = firepower;
        this.timer = timer;
        this.bomber = bomber;
        this.startTime = 0;
        this.detonated = false;
    }

    private void explode() {
        this.instantiate(new Explosion.Horizontal(this.firepower, this.position));
        this.instantiate(new Explosion.Vertical(this.firepower, this.position));
        this.bomber.restoreAmmo();
    }

    @Override
    public void update() {
        if (this.startTime++ >= this.timer) {
            this.destroy();
        }
    }

    @Override
    public void onDestroy() {
        this.explode();
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.collides(this);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

}
