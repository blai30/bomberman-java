package gameobjects;

import util.Vector2D;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bomber extends Player {

    private BufferedImage[][] sprites;
    private BufferedImage baseSprite;

    private Bomb bomb;

    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;

    private int moveSpeed;
    private int firePower;
    private int bombAmmo;
    private int bombTimer;

    public Bomber(float xPos, float yPos, BufferedImage spriteMap) {
        super(xPos, yPos - 16);

        // Each individually sliced sprite is 32x48
        this.sprites = new BufferedImage[spriteMap.getHeight() / 48][spriteMap.getWidth() / 32];
        for (int row = 0; row < spriteMap.getHeight() / 48; row++) {
            for (int column = 0; column < spriteMap.getWidth() / 32; column++) {
                this.sprites[row][column] = spriteMap.getSubimage(column * 32, row * 48, 32, 48);
            }
        }

        this.baseSprite = this.sprites[1][0];
        this.sprite = this.baseSprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, (this.height / 2) + 8);
        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY() + 16, this.width, this.height - 16);

        this.direction = 1;
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        this.moveSpeed = 1;
        this.firePower = 1;
        this.bombAmmo = 1;
        this.bombTimer = 600;
    }

    private void moveUp() {
        this.direction = 0;
        this.position.addY(-this.moveSpeed);
    }
    private void moveDown() {
        this.direction = 1;
        this.position.addY(this.moveSpeed);
    }
    private void moveLeft() {
        this.direction = 2;
        this.position.addX(-this.moveSpeed);
    }
    private void moveRight() {
        this.direction = 3;
        this.position.addX(this.moveSpeed);
    }

    private void plantBomb() {
        this.bomb = new Bomb(this.firePower, this.bombTimer, this);
        float x = Math.round(this.position.getX() / 32) * 32 + this.originOffset.getX();
        float y = Math.round((this.position.getY() + 16) / 32) * 32 + this.originOffset.getY() - 16;
        this.instantiate(this.bomb, new Vector2D(x, y), this.rotation);
        this.bombAmmo--;
    }

    public BufferedImage getBaseSprite() {
        return this.baseSprite;
    }

    @Override
    public void update() {
        this.collider.setRect(this.position.getX(), this.position.getY() + 16, this.width, this.height - 16);

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
        if (this.spriteTimer++ >= 5) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if ((!this.UpPressed && !this.DownPressed && !this.LeftPressed && !this.RightPressed) || (this.spriteIndex >= this.sprites[0].length)) {
            this.spriteIndex = 0;
        }
        this.sprite = this.sprites[this.direction][this.spriteIndex];
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomber collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    @Override
    public void handleCollision(Explosion collidingObj) {

    }

}
