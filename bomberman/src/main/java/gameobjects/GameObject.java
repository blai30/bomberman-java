package gameobjects;

public abstract class GameObject {



}

interface CollisionHandling {

    void collides(GameObject collidingObj);
    void handleCollision();

}

interface Observable {

    void update();

}
