package gameobjects;

import util.GameObjectCollection;
import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Bomb objects that are created by bombers.
 */
public class Bomb extends TileObject {

    // Original bomber that placed this bomb
    private Bomber bomber;

    // Animation
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;

    // Stats
    private int firepower;
    private boolean pierce;
    private int timeToDetonate;
    private int timeElapsed;

    // Kicking bomb
    private boolean kicked;
    private KickDirection kickDirection;

    /**
     * Constructs a bomb object with values passed in by a bomber object.
     * @param position Coordinates of this object in the game world
     * @param firepower Strength of the bomb explosionContact
     * @param pierce Whether or not the explosions will pierce soft walls
     * @param timer How long before the bomb detonates
     * @param bomber Original bomber that placed this bomb
     */
    public Bomb(Point2D.Float position, int firepower, boolean pierce, int timer, Bomber bomber) {
        super(position, pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites()[0][0] : ResourceCollection.SpriteMaps.BOMB.getSprites()[0][0]);
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

        // Animation
        this.sprites = pierce ? ResourceCollection.SpriteMaps.BOMB_PIERCE.getSprites() : ResourceCollection.SpriteMaps.BOMB.getSprites();
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Stats
        this.firepower = firepower;
        this.pierce = pierce;
        this.timeToDetonate = timer;
        this.bomber = bomber;
        this.timeElapsed = 0;
        this.breakable = true;

        // Kicking bomb
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
    }

    /**
     * Bomb detonates upon destroy and creates explosions. Also replenishes ammo for original bomber.
     */
    private void explode() {
        // Snap bombs to the grid on the map before exploding
        this.snapToGrid();
        GameObjectCollection.spawn(new Explosion.Horizontal(this.position, this.firepower, this.pierce));
        GameObjectCollection.spawn(new Explosion.Vertical(this.position, this.firepower, this.pierce));
        this.bomber.restoreAmmo();
    }

    public void setKicked(boolean kicked, KickDirection kickDirection) {
        this.kicked = kicked;
        this.kickDirection = kickDirection;
    }

    public boolean isKicked() {
        return this.kicked;
    }

    public void stopKick() {
        this.kicked = false;
        this.kickDirection = KickDirection.Nothing;
        this.snapToGrid();
    }

    /**
     * Retrieve original bomber object.
     * @return Original bomber that placed this bomb
     */
    public Bomber getBomber() {
        return this.bomber;
    }

    /**
     * Controls animation and detonation timer.
     */
    @Override
    public void update() {
        this.collider.setRect(this.position.x, this.position.y, this.width, this.height);

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
        if (this.timeElapsed++ >= this.timeToDetonate) {
            this.destroy();
        }

        // Continue traveling when kicked
        if (this.kicked) {
            this.position.setLocation(this.position.x + this.kickDirection.getVelocity().x,
                    this.position.y + this.kickDirection.getVelocity().y);
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
    public void handleCollision(Bomber collidingObj) {
        Point2D.Float temp = new Point2D.Float((float) this.collider.getCenterX() + this.kickDirection.getVelocity().x, (float) this.collider.getCenterY() + this.kickDirection.getVelocity().y);
//        if (this.kicked && (Math.abs(temp.x - collidingObj.collider.getCenterX()) >= 2 || Math.abs(temp.y - collidingObj.collider.getCenterY()) >= 2)) {
//            System.out.println("Stop kick called");
//            this.solidCollision(collidingObj);
//            this.stopKick();
//        }

        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        if (this.kicked && (Math.abs(temp.x - collidingObj.collider.getCenterX()) < intersection.getWidth() || Math.abs(temp.y - collidingObj.collider.getCenterY()) < intersection.getHeight())) {
            System.out.println("Stop kick called");
            this.solidCollision(collidingObj);
            this.stopKick();
        }
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
        this.stopKick();
    }

    /**
     * Bombs are immediately destroyed when colliding with explosionContact.
     * This is a different behavior than powerups and walls since they are not destroyed until the explosionContact animation finishes.
     * @param collidingObj Explosion that will detonate this bomb
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        this.destroy();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}

enum KickDirection {

    FromTop(new Point2D.Float(0, 2)),
    FromBottom(new Point2D.Float(0, -2)),
    FromLeft(new Point2D.Float(2, 0)),
    FromRight(new Point2D.Float(-2, 0)),
    Nothing(new Point2D.Float(0, 0));

    private Point2D.Float velocity;

    KickDirection(Point2D.Float velocity) {
        this.velocity = velocity;
    }

    public Point2D.Float getVelocity() {
        return this.velocity;
    }

}
