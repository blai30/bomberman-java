package gameobjects;

import java.awt.image.BufferedImage;

public class Explosion extends GameObject implements Observable {

    private int firepower;

    Explosion(float xPos, float yPos, float rotation, BufferedImage sprite, int firepower) {
        // power * sprite length * 2 for vertical and horizontal sprite
        // two colliders, one for each
        // center sprite
        super(xPos, yPos, rotation, sprite);
        this.firepower = firepower;
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

    @Override
    public void update() {

    }

}
