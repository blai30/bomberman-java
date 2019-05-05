package gameobjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GameObject implements CollisionHandling, Comparable<GameObject> {

    BufferedImage sprite;
    Point2D.Float position;
    Rectangle2D.Float collider;
    float rotation;
    float width;
    float height;

    private boolean destroyed;

    // Should not be used
    GameObject() {
        this.position = new Point2D.Float();
        this.rotation = 0;
    }

    GameObject(Point2D.Float position) {
        this.position = new Point2D.Float(position.x, position.y);
        this.rotation = 0;
    }

    GameObject(float xPos, float yPos) {
        this.position = new Point2D.Float(xPos, yPos);
        this.rotation = 0;
    }

    // Use super() in constructors of subclasses
    GameObject(float xPos, float yPos, BufferedImage sprite) {
        this(sprite);
        this.position = new Point2D.Float(xPos, yPos);
        this.rotation = 0;
        this.collider = new Rectangle2D.Float(xPos, yPos, this.width, this.height);
    }

    GameObject(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
    }

    /**
     * This instantiation method is not needed for the bomberman game.
     * It was useful in the tank game because the tanks spawned bullets from the center of the tank.
     * In the bomberman game, I am initializing positions and colliders in the game objects' respective constructors.
     */
//    protected void instantiate(GameObject spawnObj, Vector2D spawnLocation, float rotation) {
//        float x = spawnLocation.getX() - spawnObj.originOffset.getX();
//        float y = spawnLocation.getY() - spawnObj.originOffset.getY();
//        Vector2D spawnPoint = new Vector2D(x, y);
//        spawnObj.position.set(spawnPoint);
//        spawnObj.rotation = rotation;
//        spawnObj.collider.setRect(x, y, spawnObj.width, spawnObj.height);
//        GameObjectCollection.spawn(spawnObj);
//    }

    protected void instantiate(GameObject spawnObj) {
        GameObjectCollection.spawn(spawnObj);
    }

    protected void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    protected void solidCollision(GameObject obj) {
        Rectangle2D intersection = this.collider.createIntersection(obj.collider);
        // Vertical collision
        if (intersection.getWidth() >= intersection.getHeight()) {
            // From the top
            if (intersection.getMaxY() >= this.collider.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y - intersection.getHeight());
            }
            // From the bottom
            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y + intersection.getHeight());
            }

            // Smoothing around corners
            if (intersection.getWidth() < 16) {
                if (intersection.getMaxX() >= this.collider.getMaxX()) {
                    this.position.setLocation(this.position.x - 0.5, this.position.y);
                }
                if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                    this.position.setLocation(this.position.x + 0.5, this.position.y);
                }
            }
        }

        // Horizontal collision
        if (intersection.getHeight() >= intersection.getWidth()) {
            // From the left
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
                this.position.setLocation(this.position.x - intersection.getWidth(), this.position.y);
            }
            // From the right
            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                this.position.setLocation(this.position.x + intersection.getWidth(), this.position.y);
            }

            // Smoothing around corners
            if (intersection.getHeight() < 16) {
                if (intersection.getMaxY() >= this.collider.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y - 0.5);
                }
                if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y + 0.5);
                }
            }
        }
    }

    public Rectangle2D getCollider() {
        return this.collider;
    }

    public float getPositionY() {
        return this.position.y;
    }

    public abstract void update();
    public abstract void onDestroy();

    /**
     * Draws the game object in the game world to g.
     * (ie. the buffer which will be drawn to the screen)
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
        rotation.rotate(Math.toRadians(this.rotation), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    /**
     * Draw the game object's collider to the game world for debugging.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawCollider(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(this.collider);
    }

    @Override
    public int compareTo(GameObject o) {
        return Float.compare(this.getPositionY(), o.getPositionY());
    }

}

/**
 * Visitor pattern collision handling. Blank default methods so that subclasses only need to
 * override the ones they need to avoid overriding them in every subclass only to leave them empty.
 * Not all game objects interact with every other game object.
 */
interface CollisionHandling {

    void collides(GameObject collidingObj);

    default void handleCollision(Bomber collidingObj) {

    };

    default void handleCollision(Wall collidingObj) {

    };

    default void handleCollision(Explosion collidingObj) {

    };

    default void handleCollision(Bomb collidingObj) {

    };

}
