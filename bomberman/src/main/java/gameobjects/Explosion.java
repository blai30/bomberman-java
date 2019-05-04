package gameobjects;

import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Explosion extends GameObject {

    public static class Horizontal extends Explosion {

        Horizontal(int firepower, Vector2D spawnLocation) {
            this.position = new Vector2D(spawnLocation);

            float leftX = this.position.getX();
            outer: for (int i = 1; i <= firepower; i++) {
                leftX -= 32;
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(leftX, this.position.getY())) {
                        if (!obj.isBreakable()) {
                            leftX += 32;
                        }
                        break outer;
                    }
                }
            }

            float rightX = this.position.getX();
            outer: for (int i = 1; i <= firepower; i++) {
                rightX += 32;
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(rightX,  this.position.getY())) {
                        if (!obj.isBreakable()) {
                            rightX -= 32;
                        }
                        break outer;
                    }
                }
            }

            Rectangle2D.Double recH = new Rectangle2D.Double(leftX, this.position.getY(), rightX - leftX + 32, 32);
            this.collider = recH;
            this.width = (float) this.collider.width;
            this.height = (float) this.collider.height;
            this.originOffset = new Vector2D(this.width / 2, this.height / 2);
            this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);

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

        Vertical(int firepower, Vector2D spawnLocation) {
            this.position = new Vector2D(spawnLocation);

            float topY = this.position.getY();
            outer: for (int i = 1; i <= firepower; i++) {
                topY -= 32;
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(this.position.getX(), topY)) {
                        if (!obj.isBreakable()) {
                            topY += 32;
                        }
                        break outer;
                    }
                }
            }

            float bottomY = this.position.getY();
            outer: for (int i = 1; i <= firepower; i++) {
                bottomY += 32;
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(this.position.getX(), bottomY)) {
                        if (!obj.isBreakable()) {
                            bottomY -= 32;
                        }
                        break outer;
                    }
                }
            }

            Rectangle2D.Double recV = new Rectangle2D.Double(this.position.getX(), topY, 32, bottomY - topY + 32);
            this.collider = recV;
            this.width = (float) this.collider.width;
            this.height = (float) this.collider.height;
            this.originOffset = new Vector2D(this.width / 2, this.height / 2);
            this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);

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

    Explosion() {

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

        this.position = new Vector2D();
        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);

        this.sprite = this.sprites[0];
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);

        this.spriteIndex = 0;
        this.spriteTimer = 0;
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
