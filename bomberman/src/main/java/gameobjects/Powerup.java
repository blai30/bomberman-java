package gameobjects;

import util.ResourceCollection;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup extends GameObject {

    enum Type {

        Bomb(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Fireup(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Firemax(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Speed(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Pierce(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        },

        Kick(ResourceCollection.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Bomber bomber) {

            }
        };

        private BufferedImage sprite;

        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        protected abstract void grantBonus(Bomber bomber);

    }

    private Type type;

    public Powerup() {
//        super();
//        this(this.randomPower());
    }

    public Powerup(Type type) {
        this.type = type;
    }

    // Random powerups
    private Powerup.Type[] powerups = Powerup.Type.values();
    private Random random = new Random();
    private final Powerup.Type randomPower() {
        return this.powerups[this.random.nextInt(this.powerups.length)];
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
