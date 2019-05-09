package gameobjects;

import util.GameObjectCollection;
import util.ResourceCollection;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Base class for two types of explosions: horizontal and vertical.
 */
public abstract class Explosion extends GameObject {

    /**
     * Horizontal explosion class.
     */
    public static class Horizontal extends Explosion {

        /**
         * Constructs a horizontal explosion that varies in length depending on firepower and pierce.
         * @param position Coordinates of this object in the game world
         * @param firepower Strength of this explosion
         * @param pierce Whether or not this explosion will pierce soft walls
         */
        Horizontal(Point2D.Float position, int firepower, boolean pierce) {
            super(position);

            float leftX = this.checkHorizontal(this.position, firepower, pierce, 32, true);
            float rightX = this.checkHorizontal(this.position, firepower, pierce, 32, false);

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
        private float checkHorizontal(Point2D.Float position, int firepower, boolean pierce, int blockWidth, boolean isLeft) {
            float value = position.x;   // Start at the origin tile

            outer: for (int i = 1; i <= firepower; i++) {
                // Expand one tile at a time
                value = (isLeft) ? value - blockWidth : value + blockWidth;

                // Move offset; The offset is used to draw the center explosion sprite
                if (isLeft) {
                    this.centerOffset += blockWidth;
                }

                // Check this tile for wall collision
                for (int index = 0; index < GameObjectCollection.tileObjects.size(); index++) {
                    TileObject obj = GameObjectCollection.tileObjects.get(index);
                    if (obj.collider.contains(value, position.y)) {
                        if (!obj.isBreakable()) {
                            // Hard wall found, move value back to the tile before
                            value = (isLeft) ? value + blockWidth : value - blockWidth;

                            // Move offset back too
                            if (isLeft) {
                                this.centerOffset -= blockWidth;
                            }
                        }

                        // Stop checking for tile objects after the first breakable is found
                        if (!pierce) {
                            break outer;
                        }
                    }
                }
            }

            return value;
        }

        /**
         * Draws the explosion sprite after determining its length and center.
         * @param width Explosion width
         * @param height Explosion height
         * @return Array of sprites for animation
         */
        private BufferedImage[] drawSprite(int width, int height) {
            // Initialize each image in the array to be drawn to
            BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
            for (int i = 0; i < spriteAnimation.length; i++) {
                spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }

            // Draw to each image in the array
            for (int i = 0; i < spriteAnimation.length; i++) {
                Graphics2D g2 = spriteAnimation[i].createGraphics();
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

                for (int j = 0; j < spriteAnimation[i].getWidth() / 32; j++) {
                    if (spriteAnimation[i].getWidth() / 32 == 1 || this.centerOffset == j * 32) {
                        // Center sprite
                        g2.drawImage(this.sprites[0][i], j * 32, 0, null);
                    } else if (j == 0) {
                        // Leftmost sprite
                        g2.drawImage(this.sprites[3][i], j * 32, 0, null);
                    } else if (j == (spriteAnimation[i].getWidth() / 32) - 1) {
                        // Rightmost sprite
                        g2.drawImage(this.sprites[4][i], j * 32, 0, null);
                    } else {
                        // Horizontal between sprite
                        g2.drawImage(this.sprites[1][i], j * 32, 0, null);
                    }
                }

                g2.dispose();
            }

            return spriteAnimation;
        }

    }

    /**
     * Vertical explosion class.
     */
    public static class Vertical extends Explosion {

        /**
         * Constructs a horizontal explosion that varies in length depending on firepower and pierce.
         * @param position Coordinates of this object in the game world
         * @param firepower Strength of this explosion
         * @param pierce Whether or not this explosion will pierce soft walls
         */
        Vertical(Point2D.Float position, int firepower, boolean pierce) {
            super(position);

            float topY = this.checkVertical(this.position, firepower, pierce, 32, true);
            float bottomY = this.checkVertical(this.position, firepower, pierce, 32, false);

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
        private float checkVertical(Point2D.Float position, int firepower, boolean pierce, int blockHeight, boolean isTop) {
            float value = position.y;   // Start at the origin tile

            outer: for (int i = 1; i <= firepower; i++) {
                // Expand one tile at a time
                value = (isTop) ? value - blockHeight : value + blockHeight;

                // Move offset; The offset is used to draw the center explosion sprite
                if (isTop) {
                    this.centerOffset += blockHeight;
                }

                // Check this tile for wall collision
                for (int index = 0; index < GameObjectCollection.tileObjects.size(); index++) {
                    TileObject obj = GameObjectCollection.tileObjects.get(index);
                    if (obj.collider.contains(position.x, value)) {
                        if (!obj.isBreakable()) {
                            // Hard wall found, move value back to the tile before
                            value = (isTop) ? value + blockHeight : value - blockHeight;

                            // Move offset back too
                            if (isTop) {
                                this.centerOffset -= blockHeight;
                            }
                        }

                        // Stop checking for tile objects after the first breakable is found
                        if (!pierce) {
                            break outer;
                        }
                    }
                }
            }

            return value;
        }

        /**
         * Draws the explosion sprite after determining its length and center.
         * @param width Explosion width
         * @param height Explosion height
         * @return Array of sprites for animation
         */
        private BufferedImage[] drawSprite(int width, int height) {
            // Initialize each image in the array to be drawn to
            BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
            for (int i = 0; i < spriteAnimation.length; i++) {
                spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }

            // Draw to each image in the array
            for (int i = 0; i < spriteAnimation.length; i++) {
                Graphics2D g2 = spriteAnimation[i].createGraphics();
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

                for (int j = 0; j < spriteAnimation[i].getHeight() / 32; j++) {
                    if (spriteAnimation[i].getHeight() / 32 == 1 || this.centerOffset == j * 32) {
                        // Center sprite
                        g2.drawImage(this.sprites[0][i], 0, j * 32, null);
                    } else if (j == 0) {
                        // Topmost sprite
                        g2.drawImage(this.sprites[5][i], 0, j * 32, null);
                    } else if (j == (spriteAnimation[i].getHeight() / 32) - 1) {
                        // Bottommost sprite
                        g2.drawImage(this.sprites[6][i], 0, j * 32, null);
                    } else {
                        // Vertical between sprite
                        g2.drawImage(this.sprites[2][i], 0, j * 32, null);
                    }
                }

                g2.dispose();
            }

            return spriteAnimation;
        }

    }


    // --- BASE CLASS ---

    protected BufferedImage[][] sprites;
    protected BufferedImage[] animation;
    protected int centerOffset;
    private int spriteIndex;
    private int spriteTimer;

    /**
     * Constructor called in horizontal and vertical constructors.
     * @param position Coordinates of this object in the game world
     */
    Explosion(Point2D.Float position) {
        super(position);
        this.sprites = ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getSprites();

        this.centerOffset = 0;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
    }

    /**
     * Called later in the constructor to set collider.
     * @param collider Collider for this to be set to
     */
    protected void init(Rectangle2D.Float collider) {
        this.collider = collider;
        this.width = this.collider.width;
        this.height = this.collider.height;
        this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Controls animation and destroy when it finishes
     */
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
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    /**
     * Draw based on the collider's position instead of this object's own position.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.collider.x, this.collider.y);
        rotation.rotate(Math.toRadians(this.rotation), this.collider.width / 2.0, this.collider.height / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

}
