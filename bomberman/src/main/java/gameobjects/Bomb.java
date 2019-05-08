package gameobjects;

import util.GameObjectCollection;
import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Bomb objects that are created by bombers.
 */
public class Bomb extends GameObject {

    // Original bomber that placed this bomb
    private Bomber bomber;
    private boolean solidToBomber;

    // Animation
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;

    // Stats
    private int firepower;
    private boolean pierce;
    private int timeToDetonate;
    private int startTime;

    /**
     * Constructs a bomb object with values passed in by a bomber object.
     * @param position Coordinates of this object in the game world
     * @param firepower Strength of the bomb explosion
     * @param pierce Whether or not the explosions will pierce soft walls
     * @param timer How long before the bomb detonates
     * @param bomber Original bomber that placed this bomb
     */
    public Bomb(Point2D.Float position, int firepower, boolean pierce, int timer, Bomber bomber) {
        super(position, ResourceCollection.SpriteMaps.BOMB.getSprites()[0][0]);
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        // Animation
        this.sprites = ResourceCollection.SpriteMaps.BOMB.getSprites();
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Stats
        this.firepower = firepower;
        this.pierce = pierce;
        this.timeToDetonate = timer;
        this.bomber = bomber;
        this.startTime = 0;
        this.solidToBomber = false;
    }

    /**
     * Bomb detonates upon destroy and creates explosions. Also replenishes ammo for original bomber.
     */
    private void explode() {
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
        this.bomber.addAmmo(1);
    }

    /**
     * Retrieve original bomber object.
     * @return Original bomber that placed this bomb
     */
    public Bomber getBomber() {
        return this.bomber;
    }

    /**
     * Check if original bomber has stepped out of the bomb.
     * @return true = bomber has stepped out, false = bomber has not stepped out
     */
    public boolean getSolidToBomber() {
        return this.solidToBomber;
    }

    /**
     * Controls animation and detonation timer.
     */
    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.sprites[0].length) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[0][this.spriteIndex];

        // Detonate after timeToDetonate
        if (this.startTime++ >= this.timeToDetonate) {
            this.destroy();
        }
    }

    @Override
    public void onDestroy() {
        this.explode();
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

}
