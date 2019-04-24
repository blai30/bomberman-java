package gameobjects;

import java.awt.image.BufferedImage;

public class Bomber extends Player implements Observable {

    public Bomber(float xPos, float yPos, float rotation, BufferedImage sprite) {
        super(xPos, yPos, rotation, sprite);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

    @Override
    public void update() {

    }

}
