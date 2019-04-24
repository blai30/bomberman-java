package gameobjects;

import java.awt.image.BufferedImage;

public class Bomberman extends Player implements Observable {

    public Bomberman(float xPos, float yPos, float rotation, BufferedImage sprite) {
        super(xPos, yPos, rotation, sprite);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomberman collidingObj) {

    }

    @Override
    public void update() {

    }

}
