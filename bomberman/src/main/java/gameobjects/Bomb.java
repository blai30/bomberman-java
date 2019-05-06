package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bomb extends GameObject {

    private Bomber bomber;

    private BufferedImage[] sprites;
    private int spriteIndex;
    private int spriteTimer;

    private int firepower;
    private int timer;
    private int startTime;

    public Bomb(Point2D.Float spawnLocation, int firepower, int timer, Bomber bomber) {
        BufferedImage spriteMap = ResourceCollection.Images.BOMB.getImage();
        int cols = spriteMap.getWidth() / 32;
        this.sprites = new BufferedImage[cols];
        for (int column = 0; column < cols; column++) {
            this.sprites[column] = spriteMap.getSubimage(column * 32, 0, 32, 32);
        }

        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.sprite = this.sprites[this.spriteIndex];
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();

        this.position = spawnLocation;
        this.collider = new Rectangle2D.Float(this.position.x, this.position.y, this.width, this.height);
        this.firepower = firepower;
        this.timer = timer;
        this.bomber = bomber;
        this.startTime = 0;
    }

    private void explode() {
        this.instantiate(new Explosion.Horizontal(this.firepower, this.position));
        this.instantiate(new Explosion.Vertical(this.firepower, this.position));
        this.bomber.restoreAmmo();
    }

    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.sprites.length) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[this.spriteIndex];

        // Detonate after timer
        if (this.startTime++ >= this.timer) {
            this.destroy();
        }
    }

    @Override
    public void onDestroy() {
        this.explode();
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.onCollisionEnter(this);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

}
