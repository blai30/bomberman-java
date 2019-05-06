package gameobjects;

import util.ResourceCollection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Explosion extends GameObject {

    public static class Horizontal extends Explosion {

        Horizontal(int firepower, Point2D.Float spawnLocation) {
            super(firepower, spawnLocation);

            float leftX = this.checkHorizontal(this.position, firepower, 32, true);
            float rightX = this.checkHorizontal(this.position, firepower, 32, false);

            Rectangle2D.Float recH = new Rectangle2D.Float(leftX, this.position.y, rightX - leftX + 32, 32);
            this.init(recH);

            this.animation = this.drawSprite((int) this.width, (int) this.height);

            this.sprite = this.animation[0];
        }

        /**
         * Check for walls to determine explosion range. Used for left and right.
         * @param position Original position of bomb prior to explosion
         * @param firepower Maximum range of explosion
         * @param blockWidth Size of each game object tile, negative for left, positive for right
         * @return Position of the explosion's maximum range in horizontal direction
         */
        private float checkHorizontal(Point2D.Float position, int firepower, int blockWidth, boolean isLeft) {
            float value = position.x;
            outer: for (int i = 1; i <= firepower; i++) {
                value = (isLeft) ? value - blockWidth : value + blockWidth;
                if (isLeft) {
                    this.centerOffsetH += blockWidth;
                }
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(value, position.y)) {
                        if (!obj.isBreakable()) {
                            value = (isLeft) ? value + blockWidth : value - blockWidth;
                            if (isLeft) {
                                this.centerOffsetH -= blockWidth;
                            }
                        }
                        break outer;
                    }
                }
            }

            return value;
        }

        private BufferedImage[] drawSprite(int width, int height) {
            BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.Images.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
            for (int i = 0; i < spriteAnimation.length; i++) {
                spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
            for (int i = 0; i < spriteAnimation.length; i++) {
                Graphics2D g2 = spriteAnimation[i].createGraphics();
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

                for (int j = 0; j < spriteAnimation[i].getWidth() / 32; j++) {
                    if (spriteAnimation[i].getWidth() / 32 == 1 || this.centerOffsetH == j * 32) {
                        g2.drawImage(this.sprites[0][i], j * 32, 0, null);
                    } else if (j == 0) {
                        g2.drawImage(this.sprites[3][i], j * 32, 0, null);
                    } else if (j == (spriteAnimation[i].getWidth() / 32) - 1) {
                        g2.drawImage(this.sprites[4][i], j * 32, 0, null);
                    } else {
                        g2.drawImage(this.sprites[1][i], j * 32, 0, null);
                    }
                }

                g2.dispose();
            }

            return spriteAnimation;
        }

    }

    public static class Vertical extends Explosion {

        Vertical(int firepower, Point2D.Float spawnLocation) {
            super(firepower, spawnLocation);

            float topY = this.checkVertical(this.position, firepower, 32, true);
            float bottomY = this.checkVertical(this.position, firepower, 32, false);

            Rectangle2D.Float recV = new Rectangle2D.Float(this.position.x, topY, 32, bottomY - topY + 32);
            this.init(recV);

            this.animation = this.drawSprite((int) this.width, (int) this.height);

            this.sprite = this.animation[0];
        }

        /**
         * Check for walls to determine explosion range. Used for top and bottom.
         * @param position Original position of bomb prior to explosion
         * @param firepower Maximum range of explosion
         * @param blockHeight Size of each game object tile, negative for top, positive for bottom
         * @return Position of the explosion's maximum range in vertical direction
         */
        private float checkVertical(Point2D.Float position, int firepower, int blockHeight, boolean isTop) {
            float value = position.y;
            outer: for (int i = 1; i <= firepower; i++) {
                value = (isTop) ? value - blockHeight : value + blockHeight;
                if (isTop) {
                    this.centerOffsetV += blockHeight;
                }
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    Wall obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(position.x, value)) {
                        if (!obj.isBreakable()) {
                            value = (isTop) ? value + blockHeight : value - blockHeight;
                            if (isTop) {
                                this.centerOffsetV -= blockHeight;
                            }
                        }
                        break outer;
                    }
                }
            }

            return value;
        }

        private BufferedImage[] drawSprite(int width, int height) {
            BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.Images.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
            for (int i = 0; i < spriteAnimation.length; i++) {
                spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
            for (int i = 0; i < spriteAnimation.length; i++) {
                Graphics2D g2 = spriteAnimation[i].createGraphics();
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

                for (int j = 0; j < spriteAnimation[i].getHeight() / 32; j++) {
                    if (spriteAnimation[i].getHeight() / 32 == 1 || this.centerOffsetV == j * 32) {
                        g2.drawImage(this.sprites[0][i], 0, j * 32, null);
                    } else if (j == 0) {
                        g2.drawImage(this.sprites[5][i], 0, j * 32, null);
                    } else if (j == (spriteAnimation[i].getHeight() / 32) - 1) {
                        g2.drawImage(this.sprites[6][i], 0, j * 32, null);
                    } else {
                        g2.drawImage(this.sprites[2][i], 0, j * 32, null);
                    }
                }

                g2.dispose();
            }

            return spriteAnimation;
        }

    }

    protected BufferedImage[][] sprites;
    protected BufferedImage[] animation;
    protected int firepower;
    protected int centerOffsetH;
    protected int centerOffsetV;
    private int spriteIndex;
    private int spriteTimer;

    Explosion(int firepower, Point2D.Float position) {
        super(position);
        this.firepower = firepower;
        this.centerOffsetH = 0;
        this.centerOffsetV = 0;

        BufferedImage spriteMap = ResourceCollection.Images.EXPLOSION_SPRITEMAP.getImage();
        int rows = ResourceCollection.Images.EXPLOSION_SPRITEMAP.getImage().getHeight() / 32;
        int cols = ResourceCollection.Images.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32;
        this.sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                this.sprites[row][column] = spriteMap.getSubimage(column * 32, row * 32, 32, 32);
            }
        }
    }

    protected void init(Rectangle2D.Float collider) {
        this.collider = collider;
        this.width = this.collider.width;
        this.height = this.collider.height;
        this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.animation.length) {
            this.destroy();
        } else {
            this.sprite = this.animation[this.spriteIndex];
        }
    }

    @Override
    public void onDestroy() {

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
