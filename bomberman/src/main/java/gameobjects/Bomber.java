package gameobjects;

import util.GameObjectCollection;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Bomberman player object to be controlled by a user.
 */
public class Bomber extends Player {

    // Animation
    private BufferedImage[][] sprites;
    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;

    // Stats
    private float moveSpeed;
    private int firepower;
    private int bombAmmo;
    private int bombTimer;
    private int maxBombs;
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
        this.collider.setRect(this.position.x + 2, this.position.y + 16 + 2, this.width - 4, this.height - 16 - 4);

        // Animation
        this.sprites = spriteMap;
        this.direction = 1;     // Facing down
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        // Default stats
        this.moveSpeed = 1;
        this.firepower = 10;
        this.bombAmmo = 10;
        this.bombTimer = 250;
        this.maxBombs = this.bombAmmo;
        this.pierce = false;
        this.kick = false;
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
        Bomb bomb = new Bomb(spawnLocation, this.firepower, this.pierce, this.bombTimer, this);
        GameObjectCollection.spawn(bomb);
        this.bombAmmo--;
    }

    public void restoreAmmo() {
        this.bombAmmo = Math.min(this.maxBombs, this.bombAmmo + 1);
    }

    // --- POWERUPS ---
    public void addAmmo(int value) {
        this.maxBombs = Math.min(6, this.maxBombs + value);
    }
    public void addFirepower(int value) {
        this.firepower = Math.min(6, this.firepower + value);
    }
    public void addSpeed(float value) {
        this.moveSpeed = Math.min(4, this.moveSpeed + value);
    }
    public void setPierce(boolean value) {
        this.pierce = value;
    }
    public void setKick(boolean value) {
        this.kick = value;
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
        this.collider.setRect(this.position.x + 2, this.position.y + 16 + 2, this.width - 4, this.height - 16 - 4);

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

        // Animate sprite
        if ((this.spriteTimer += this.moveSpeed) >= 12) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if ((!this.UpPressed && !this.DownPressed && !this.LeftPressed && !this.RightPressed) || (this.spriteIndex >= this.sprites[0].length)) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[this.direction][this.spriteIndex];
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    /**
     * Bombs act as walls if the bomber is not already within the a certain distance as the bomb.
     * (ie. if the bomber is not standing on the bomb)
     * @param collidingObj Solid bomb
     */
    @Override
    public void handleCollision(Bomb collidingObj) {
        if (collidingObj.getColliderCenter().distance(this.getColliderCenter()) >= 26) {
            this.solidCollision(collidingObj);
        }
    }

    @Override
    public void handleCollision(Powerup collidingObj) {
        collidingObj.grantBonus(this);
        collidingObj.destroy();
    }

}
