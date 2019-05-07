package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup extends GameObject {

    enum Type {

        Bomb(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Fireup(ResourceCollection.Images.POWER_FIREUP.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Firemax(ResourceCollection.Images.POWER_FIREMAX.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Speed(ResourceCollection.Images.POWER_SPEED.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Pierce(ResourceCollection.Images.POWER_PIERCE.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Kick(ResourceCollection.Images.POWER_KICK.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        };

        private BufferedImage sprite;

        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        static Powerup.Type randomType() {
            return randomPower();
        }

        protected abstract void grantBonus(Bomber bomber);

    }

    private Type type;

    public Powerup(Point2D.Float position, Type type) {
        super(position, type.sprite);
    }

    // Random powerups
    private static Powerup.Type[] powerups = Powerup.Type.values();
    private static Random random = new Random();
    private static final Powerup.Type randomPower() {
        return powerups[random.nextInt(powerups.length)];
    }

    void grantBonus(Bomber bomber) {
        this.type.grantBonus(bomber);
    }

    @Override
    public void update() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

}
