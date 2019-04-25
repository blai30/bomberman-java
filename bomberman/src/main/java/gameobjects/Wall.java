package gameobjects;

import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(float xPos, float yPos, BufferedImage sprite) {
        super(xPos, yPos, sprite);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {

    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

}
