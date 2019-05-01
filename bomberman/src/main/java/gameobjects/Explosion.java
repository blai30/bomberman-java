package gameobjects;

public class Explosion extends GameObject {

    private int firepower;

    Explosion(int firepower) {
        // power * sprite length * 2 for vertical and horizontal sprite
        // two colliders, one for each
        // center sprite
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
