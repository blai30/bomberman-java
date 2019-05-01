package gameobjects;

import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GameObject implements CollisionHandling, Comparable<GameObject> {

    BufferedImage sprite;
    Vector2D position;
    float rotation;
    float width;
    float height;
    Vector2D originOffset;
    Rectangle2D.Double collider;

    private boolean destroyed;

    // Should not be used
    GameObject() {}

    protected GameObject(float xPos, float yPos) {
        this.position = new Vector2D(xPos, yPos);
        this.rotation = 0;
    }

    // Use super() in constructors of subclasses
    protected GameObject(float xPos, float yPos, BufferedImage sprite) {
        this(sprite);
        this.position = new Vector2D(xPos, yPos);
        this.rotation = 0;
        this.collider = new Rectangle2D.Double(xPos, yPos, this.width, this.height);
    }

    protected GameObject(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
    }

    protected void instantiate(GameObject spawnObj, Vector2D spawnLocation, float rotation) {
        float x = spawnLocation.getX() - spawnObj.originOffset.getX();
        float y = spawnLocation.getY() - spawnObj.originOffset.getY();
        Vector2D spawnPoint = new Vector2D(x, y);
        spawnObj.position.set(spawnPoint);
        spawnObj.rotation = rotation;
        spawnObj.collider.setRect(x, y, spawnObj.width, spawnObj.height);
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
                this.position.addY(-(float) intersection.getHeight());
            }
            // From the bottom
            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                this.position.addY((float) intersection.getHeight());
            }

            // Smoothing around corners
            if (intersection.getWidth() < 16) {
                if (intersection.getMaxX() >= this.collider.getMaxX()) {
                    this.position.addX(-(float) 0.5);
                }
                if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                    this.position.addX((float) 0.5);
                }
            }
        }

        // Horizontal collision
        if (intersection.getHeight() >= intersection.getWidth()) {
            // From the left
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
                this.position.addX(-(float) intersection.getWidth());
            }
            // From the right
            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                this.position.addX((float) intersection.getWidth());
            }

            // Smoothing around corners
            if (intersection.getHeight() < 16) {
                if (intersection.getMaxY() >= this.collider.getMaxY()) {
                    this.position.addY(-(float) 0.5);
                }
                if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                    this.position.addY((float) 0.5);
                }
            }
        }
    }

    public Rectangle2D getCollider() {
        return this.collider;
    }

    public float getPositionY() {
        return this.position.getY();
    }

    public abstract void update();

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
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {

    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

    @Override
    public void handleCollision(Bomb collidingObj) {

    }

    @Override
    public int compareTo(GameObject o) {
        return Float.compare(this.getPositionY(), o.getPositionY());
    }

}

interface CollisionHandling {

    void collides(GameObject collidingObj);
    void handleCollision(Bomber collidingObj);
    void handleCollision(Wall collidingObj);
    void handleCollision(Explosion collidingObj);
    void handleCollision(Bomb collidingObj);

}
