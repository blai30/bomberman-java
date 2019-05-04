package gameobjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Explosion extends GameObject {

    public static class Horizontal extends Explosion {

        Horizontal(int firepower, Point2D.Float spawnLocation) {
            super(spawnLocation);

            float leftX = this.checkHorizontal(this.position, firepower, -32);
            float rightX = this.checkHorizontal(this.position, firepower, 32);

            Rectangle2D.Float recH = new Rectangle2D.Float(leftX, this.position.y, rightX - leftX + 32, 32);
            this.init(recH);

            Graphics2D g2 = this.sprite.createGraphics();
            g2.setColor(new Color(255, 0, 0, 50));
            g2.fillRect(0, 0, (int) this.width, (int) this.height);
        }

        @Override
        public void update() {
            super.update();

        }

    }

    public static class Vertical extends Explosion {

        Vertical(int firepower, Point2D.Float spawnLocation) {
            super(spawnLocation);

            float topY = this.checkVertical(this.position, firepower, -32);
            float bottomY = this.checkVertical(this.position, firepower, 32);

            Rectangle2D.Float recV = new Rectangle2D.Float(this.position.x, topY, 32, bottomY - topY + 32);
            this.init(recV);

            Graphics2D g2 = this.sprite.createGraphics();
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fillRect(0, 0, (int) this.width, (int) this.height);
        }

        @Override
        public void update() {
            super.update();

        }

    }

    private BufferedImage[] sprites;
    private int spriteIndex;
    private int spriteTimer;

    protected int firepower;

    Explosion(Point2D.Float position) {
        this.position = new Point2D.Float(position.x, position.y);
    }

    Explosion(BufferedImage spriteMap) {
        // power * sprite length * 2 for vertical and horizontal sprite
        // two colliders, one for each
        // center sprite

        // Sprite map should only be one row of sprites, like an animation strip. Sliced into 32x32 sprites
        this.sprites = new BufferedImage[spriteMap.getWidth() / 32];
        for (int column = 0; column < sprites.length; column++) {
            this.sprites[column] = spriteMap.getSubimage(column * 32, 0, 32, 32);
        }

//        this.position = new Vector2D();
//        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);

        this.sprite = this.sprites[0];
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();

        this.spriteIndex = 0;
        this.spriteTimer = 0;
    }

    protected void init(Rectangle2D.Float collider) {
        this.collider = collider;
        this.width = this.collider.width;
        this.height = this.collider.height;
        this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Check for walls to determine explosion range. Used for left and right.
     * @param position Original position of bomb prior to explosion
     * @param firepower Maximum range of explosion
     * @param blockWidth Size of each game object tile, negative for left, positive for right
     * @return Position of the explosion's maximum range in horizontal direction
     */
    protected float checkHorizontal(Point2D.Float position, int firepower, int blockWidth) {
        float value = position.x;
        outer: for (int i = 1; i <= firepower; i++) {
            value += blockWidth;
            for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                Wall obj = GameObjectCollection.wallObjects.get(index);
                if (obj.collider.contains(value, this.position.getY())) {
                    if (!obj.isBreakable()) {
                        value -= blockWidth;
                    }
                    break outer;
                }
            }
        }

        return value;
    }

    /**
     * Check for walls to determine explosion range. Used for top and bottom.
     * @param position Original position of bomb prior to explosion
     * @param firepower Maximum range of explosion
     * @param blockHeight Size of each game object tile, negative for top, positive for bottom
     * @return Position of the explosion's maximum range in vertical direction
     */
    protected float checkVertical(Point2D.Float position, int firepower, int blockHeight) {
        float value = position.y;
        outer: for (int i = 1; i <= firepower; i++) {
            value += blockHeight;
            for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                Wall obj = GameObjectCollection.wallObjects.get(index);
                if (obj.collider.contains(this.position.getX(), value)) {
                    if (!obj.isBreakable()) {
                        value -= blockHeight;
                    }
                    break outer;
                }
            }
        }

        return value;
    }

    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= 100) {
            this.destroy();
        }
//        } else {
//            this.sprite = this.sprites[this.spriteIndex];
//        }
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.collider.x, this.collider.y);
        rotation.rotate(Math.toRadians(this.rotation), this.collider.width / 2.0, this.collider.height / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

}
