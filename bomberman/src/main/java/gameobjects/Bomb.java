package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Bomb extends GameObject {

    private Bomber bomber;

    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;

    private int firepower;
    private int timer;
    private int startTime;

    public Bomb(Point2D.Float position, int firepower, int timer, Bomber bomber) {
        super(position, ResourceCollection.SpriteMaps.BOMB.getSprites()[0][0]);
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        this.sprites = ResourceCollection.SpriteMaps.BOMB.getSprites();
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        this.firepower = firepower;
        this.timer = timer;
        this.bomber = bomber;
        this.startTime = 0;
    }

    private void explode() {
        this.instantiate(new Explosion.Horizontal(this.position, this.firepower));
        this.instantiate(new Explosion.Vertical(this.position, this.firepower));
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
        this.sprite = this.sprites[0][this.spriteIndex];

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
//        collidingObj.onCollisionEnter(this);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

}
