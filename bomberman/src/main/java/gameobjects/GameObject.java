package gameobjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Abstract game object class. All game objects extend this class.
 * The base class for all game objects with properties that allow it to exist in the game world.
 */
public abstract class GameObject implements Observable, Collidable, Comparable<GameObject> {

    // Common data fields for game objects
    BufferedImage sprite;
    Point2D.Float position;
    Rectangle2D.Float collider;
    float rotation;
    float width;
    float height;

    // Marked for deletion
    private boolean destroyed;

    /**
     * Creates a new position for this game object at position. Used for objects with no predefined sprite such as explosionContact.
     * @param position Position of this game object
     */
    GameObject(Point2D.Float position) {
        this.position = new Point2D.Float(position.x, position.y);
        this.rotation = 0;
    }

    /**
     * Creates a new position for this game object at position. Sets the sprite of this game object using constructor.
     * @param position Position of this game object
     * @param sprite Sprite of this game object
     */
    GameObject(Point2D.Float position, BufferedImage sprite) {
        this(sprite);
        this.position = new Point2D.Float(position.x, position.y);
        this.rotation = 0;
        this.collider = new Rectangle2D.Float(position.x, position.y, this.width, this.height);
    }

    /**
     * To be called by other constructors. Set the sprite of the game object and its width and height depending on the sprite.
     * @param sprite
     */
    private GameObject(BufferedImage sprite) {
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

    /**
     * Mark this game object for deletion.
     */
    void destroy() {
        this.destroyed = true;
    }

    /**
     * Check if this game object is destroyed.
     * @return If this game object is destroyed or not
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    void solidCollision(GameObject obj) {
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

    /**
     * Get the rectangle collider of this game object.
     * @return A Rectangle2D collider
     */
    public Rectangle2D.Float getCollider() {
        return this.collider;
    }

    /**
     * Get the center of the collider of this game object.
     * @return A Point2D at the center of the collider
     */
    public Point2D.Float getColliderCenter() {
        return new Point2D.Float((float) this.collider.getCenterX(), (float) this.collider.getCenterY());
    }

    /**
     * Get the maximum y position of this game object.
     * @return y position + height
     */
    public float getPositionY() {
        return this.position.y + this.height;
    }

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

    /**
     * Compares the y position of two game objects.
     * Used to sort game object collection so that drawing game objects will draw in the order of y positions.
     * This adds a kind of depth to the game world.
     * @param o Game object to be compared to
     * @return -1 = less than, 1 = greater than, 0 = equal
     */
    @Override
    public int compareTo(GameObject o) {
        return Float.compare(this.position.y, o.position.y);
    }

}

/**
 * Observer pattern game state updating. Game objects perform certain actions based on the state of the game.
 */
interface Observable {

    /**
     * Repeatedly called during the game loop.
     */
    default void update() {

    }

    /**
     * Called when the game object gets destroyed.
     */
    default void onDestroy() {

    }

}

/**
 * Visitor pattern collision handling. Blank default methods so that subclasses only need to
 * override the ones they need to avoid overriding them in every subclass only to leave them empty.
 * Not all game objects interact with every other game object.
 */
interface Collidable {

    /**
     * Called when two objects collide. Override this in GameObject subclasses.
     * Usage: collidingObj.handleCollision(this);   // Put this inside the method body
     * @param collidingObj The object that this is colliding with
     */
    void onCollisionEnter(GameObject collidingObj);

    default void handleCollision(Bomber collidingObj) {

    }

    default void handleCollision(Wall collidingObj) {

    }

    default void handleCollision(Explosion collidingObj) {

    }

    default void handleCollision(Bomb collidingObj) {

    }

    default void handleCollision(Powerup collidingObj) {

    }

}
