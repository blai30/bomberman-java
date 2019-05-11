package gameobjects;

import util.GameObjectCollection;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Bomberman player object to be controlled by a user.
 */
public class Bomber extends Player {

    private Bomb bomb;
    private boolean dead;

    // Animation
    private BufferedImage[][] sprites;
    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;

    // Stats
    private float moveSpeed;
    private int firepower;
    private int maxBombs;
    private int bombAmmo;
    private int bombTimer;
    private boolean pierce;
    private boolean kick;
    private int score;

    /**
     * Constructs a bomber at position with a two-dimensional array of sprites.
     * @param position Coordinates of this object in the game world
     * @param spriteMap 2D array of sprites used for animation
     */
    public Bomber(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3, this.width - 6, this.height - 16 - 6);

        // Animation
        this.sprites = spriteMap;
        this.direction = 1;     // Facing down
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Default stats
        this.moveSpeed = 1;
        this.firepower = 6;
        this.maxBombs = 10;
        this.bombAmmo = this.maxBombs;
        this.bombTimer = 250;
        this.pierce = true;
        this.kick = true;
    }

    // --- MOVEMENT ---
    private void moveUp() {
        this.direction = 0;     // Using sprites that face up
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }
    private void moveDown() {
        this.direction = 1;     // Using sprites that face down
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }
    private void moveLeft() {
        this.direction = 2;     // Using sprites that face left
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }
    private void moveRight() {
        this.direction = 3;     // Using sprites that face right
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    // --- ACTION ---
    private void plantBomb() {
        // Snap bombs to the grid on the map
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round((this.position.getY() + 16) / 32) * 32;
        Point2D.Float spawnLocation = new Point2D.Float(x, y);

        // Only one tile object allowed per tile; Cannot place a bomb on another object
        for (int i = 0; i < GameObjectCollection.tileObjects.size(); i++) {
            GameObject obj = GameObjectCollection.tileObjects.get(i);
            if (obj.collider.contains(spawnLocation)) {
                return;
            }
        }

        // Spawn the bomb
        this.bomb = new Bomb(spawnLocation, this.firepower, this.pierce, this.bombTimer, this);
        GameObjectCollection.spawn(bomb);
        this.bombAmmo--;
    }

    public void restoreAmmo() {
        this.bombAmmo = Math.min(this.maxBombs, this.bombAmmo + 1);
    }

    // --- POWERUPS ---
    public void addAmmo(int value) {
        System.out.print("Bombs set from " + this.maxBombs);
        this.maxBombs = Math.min(6, this.maxBombs + value);
        this.restoreAmmo();
        System.out.println(" to " + this.maxBombs);
    }
    public void addFirepower(int value) {
        System.out.print("Firepower set from " + this.firepower);
        this.firepower = Math.min(6, this.firepower + value);
        System.out.println(" to " + this.firepower);
    }
    public void addSpeed(float value) {
        System.out.print("Move Speed set from " + this.moveSpeed);
        this.moveSpeed = Math.min(4, this.moveSpeed + value);
        System.out.println(" to " + this.moveSpeed);
    }
    public void setPierce(boolean value) {
        System.out.print("Pierce set from " + this.pierce);
        this.pierce = value;
        System.out.println(" to " + this.pierce);
    }
    public void setKick(boolean value) {
        System.out.print("Kick set from " + this.kick);
        this.kick = value;
        System.out.println(" to " + this.kick);
    }
    public void reduceTimer(int value) {
        System.out.print("Bomb Timer set from " + this.bombTimer);
        this.bombTimer = Math.max(160, this.bombTimer - value);
        System.out.println(" to " + this.bombTimer);
    }

    /**
     * Used in game HUD to draw the base sprite to the info box.
     * @return The sprite of the bomber facing down
     */
    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    /**
     * Controls movement, action, and animation.
     */
    @Override
    public void update() {
        this.collider.setRect(this.position.x + 3, this.position.y + 16 + 3, this.width - 6, this.height - 16 - 6);

        if (!this.dead) {
            // Animate sprite
            if ((this.spriteTimer += this.moveSpeed) >= 12) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }
            if ((!this.UpPressed && !this.DownPressed && !this.LeftPressed && !this.RightPressed) || (this.spriteIndex >= this.sprites[0].length)) {
                this.spriteIndex = 0;
            }
            this.sprite = this.sprites[this.direction][this.spriteIndex];

            // Movement
            if (this.UpPressed) {
                this.moveUp();
            }
            if (this.DownPressed) {
                this.moveDown();
            }
            if (this.LeftPressed) {
                this.moveLeft();
            }
            if (this.RightPressed) {
                this.moveRight();
            }

            // Action
            if (this.ActionPressed && this.bombAmmo > 0) {
                this.plantBomb();
            }
        } else {
            // Animate dying animation
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    this.sprite = this.sprites[4][this.spriteIndex];
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 300) {
                    this.destroy();
                }
            }
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }
    }

    /**
     * Bombs act as walls if the bomber is not already within the a certain distance as the bomb.
     * (ie. if the bomber is not standing on the bomb)
     * @param collidingObj Solid bomb
     */
    @Override
    public void handleCollision(Bomb collidingObj) {
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        if (this.kick && !collidingObj.isKicked()) {
            // Vertical collision
            if (intersection.getWidth() >= intersection.getHeight() && intersection.getHeight() <= 6 && Math.abs(this.collider.getCenterX() - collidingObj.collider.getCenterX()) <= 8) {
                // From the top
                if (intersection.getMaxY() >= this.collider.getMaxY() && this.DownPressed) {
                    collidingObj.setKicked(true, KickDirection.FromTop);
                }
                // From the bottom
                if (intersection.getMaxY() >= collidingObj.collider.getMaxY() && this.UpPressed) {
                    collidingObj.setKicked(true, KickDirection.FromBottom);
                }
                this.solidCollision(collidingObj);
            }
            // Horizontal collision
            if (intersection.getHeight() >= intersection.getWidth() && intersection.getWidth() <= 6 && Math.abs(this.collider.getCenterY() - collidingObj.collider.getCenterY()) <= 8) {
                // From the left
                if (intersection.getMaxX() >= this.collider.getMaxX() && this.RightPressed) {
                    collidingObj.setKicked(true, KickDirection.FromLeft);
                }
                // From the right
                if (intersection.getMaxX() >= collidingObj.collider.getMaxX() && this.LeftPressed) {
                    collidingObj.setKicked(true, KickDirection.FromRight);
                }
                this.solidCollision(collidingObj);
            }
        }
    }

    @Override
    public void handleCollision(Powerup collidingObj) {
        collidingObj.grantBonus(this);
        collidingObj.destroy();
    }

}
