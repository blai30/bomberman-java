package gameobjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GameObject implements CollisionHandling {

    BufferedImage sprite;
    Transform transform;
    float width;
    float height;
    Vector2D originOffset;
    Rectangle2D.Double collider;

    private boolean destroyed;

    // Should not be used
    GameObject() {}

    // Use super() in constructors of subclasses
    protected GameObject(float xPos, float yPos, float rotation, BufferedImage sprite) {
        this(sprite);
        this.transform = new Transform(xPos, yPos, rotation);
        this.collider = new Rectangle2D.Double(xPos, yPos, this.width, this.height);
    }

    protected GameObject(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
    }

    protected void instantiate(GameObject spawnObj, Transform spawnTransform) {
        float x = spawnTransform.getPositionX() - spawnObj.originOffset.getX();
        float y = spawnTransform.getPositionY() - spawnObj.originOffset.getY();
        Vector2D spawnPoint = new Vector2D(x, y);
        spawnObj.transform.setPosition(spawnPoint);
        spawnObj.transform.setRotation(spawnTransform.getRotation());
        spawnObj.collider.setRect(x, y, spawnObj.width, spawnObj.height);
        // TODO: spawn the object
    }

    protected void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Draws the game object in the game world to g.
     * (ie. the buffer which will be drawn to the screen)
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
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

}

interface CollisionHandling {

    void collides(GameObject collidingObj);
    void handleCollision(Bomber collidingObj);
    void handleCollision(Explosion collidingObj);

}

interface Observable {

    void update();

}
