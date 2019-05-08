package gameobjects;

import util.ResourceCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup extends GameObject {

    enum Type {

        Bomb(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addAmmo(1);
            }
        },

        Fireup(ResourceCollection.Images.POWER_FIREUP.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(1);
            }
        },

        Firemax(ResourceCollection.Images.POWER_FIREMAX.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addFirepower(6);
            }
        },

        Speed(ResourceCollection.Images.POWER_SPEED.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.addSpeed(1);
            }
        },

        Pierce(ResourceCollection.Images.POWER_PIERCE.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setPierce(true);
            }
        },

        Kick(ResourceCollection.Images.POWER_KICK.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {
                bomber.setKick(true);
            }
        };

        private BufferedImage sprite;

        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        protected abstract void grantBonus(Bomber bomber);

    }

    private Type type;

    public Powerup(Point2D.Float position, Type type) {
        super(position, type.sprite);
        this.type = type;
    }

    // Random powerups
    private static Powerup.Type[] powerups = Powerup.Type.values();
    private static Random random = new Random();
    static final Powerup.Type randomPower() {
        return powerups[random.nextInt(powerups.length)];
    }

    void grantBonus(Bomber bomber) {
        this.type.grantBonus(bomber);
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {
//        this.grantBonus(collidingObj);
//        this.destroy();
    }

}
