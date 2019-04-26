package gameobjects;

import java.awt.image.BufferedImage;

public class Explosion extends GameObject {

    private int firepower;

    Explosion(float xPos, float yPos, BufferedImage sprite, int firepower) {
        // power * sprite length * 2 for vertical and horizontal sprite
        // two colliders, one for each
        // center sprite
        super(xPos, yPos, sprite);
        this.firepower = firepower;
    }

    @Override
    public void update() {

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
