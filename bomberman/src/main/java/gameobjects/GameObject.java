package gameobjects;

public abstract class GameObject implements CollisionHandling {



}

interface CollisionHandling {

//    void collides(GameObject collidingObj);
//    void handleCollision();

}

interface Observable {

    void update();

}
