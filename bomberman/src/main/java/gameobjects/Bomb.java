package gameobjects;

import java.awt.image.BufferedImage;

public class Bomb extends GameObject implements Observable {

    private int firepower;

    public Bomb(float xPos, float yPos, BufferedImage sprite, int firepower) {
        super(xPos, yPos, sprite);
        this.firepower = firepower;
    }

    @Override
    public void collides(GameObject collidingObj) {

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
