package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Powerups with predefined types that spawn from breakable walls at random.
 * These powerups grant bombers various bonuses when collided with.
 */
public class Powerup extends TileObject {

    enum Type {
        // Additional bomb ammo
        Bomb(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addAmmo(1);
            }
        },

        // Increases firepower
        Fireup(ResourceCollection.Images.POWER_FIREUP.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(1);
            }
        },

        // Increases firepower to max
        Firemax(ResourceCollection.Images.POWER_FIREMAX.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(6);
            }
        },

        // Increases speed
        Speed(ResourceCollection.Images.POWER_SPEED.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addSpeed(0.5f);
            }
        },

        // Adds ability for explosions to pierce soft walls
        Pierce(ResourceCollection.Images.POWER_PIERCE.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setPierce(true);
            }
        },

        // Adds ability to kick bombs
        Kick(ResourceCollection.Images.POWER_KICK.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setKick(true);
            }
        },

        // Reduces time for bomb to detonate
        Timer(ResourceCollection.Images.POWER_TIMER.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.reduceTimer(15);
            }
        };

        private BufferedImage sprite;

        /**
         * Sets the sprite of the powerup type.
         * @param sprite Powerup sprite
         */
        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        /**
         * To be overridden by powerup types. Grants bonuses to bomber.
         * @param bomber Bomber object to be granted bonus
         */
        protected abstract void grantBonus(Bomber bomber);

    }

    private Type type;

    /**
     * Construct a powerup of type. Type can be random.
     * @param position Coordinates of this object in the game world
     * @param type Type of powerup
     */
    public Powerup(Point2D.Float position, Type type) {
        super(position, type.sprite);
        this.type = type;
        this.breakable = true;
    }

    // Random powerups
    private static Powerup.Type[] powerups = Powerup.Type.values();
    private static Random random = new Random();
    static final Powerup.Type randomPower() {
        return powerups[random.nextInt(powerups.length)];
    }

    /**
     * Grants bonuses to bomber.
     * @param bomber Bomber object to be granted bonus
     */
    void grantBonus(Bomber bomber) {
        this.type.grantBonus(bomber);
    }

    /**
     * Destroy powerup when explosion animation finishes.
     */
    @Override
    public void update() {
        if (this.checkExplosion()) {
            this.destroy();
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.destroy();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}
