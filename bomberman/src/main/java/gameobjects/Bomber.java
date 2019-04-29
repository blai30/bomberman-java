package gameobjects;

import util.Vector2D;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bomber extends Player {

    private BufferedImage[][] sprites;

    private int moveSpeed;

    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;

    public Bomber(float xPos, float yPos, BufferedImage spriteMap) {
        super(xPos, yPos - 16);

        // Each sprite is 32x48. Sprite map is 6x4 sprites
        this.sprites = new BufferedImage[spriteMap.getHeight() / 48][spriteMap.getWidth() / 32];
        for (int row = 0; row < spriteMap.getHeight() / 48; row++) {
            for (int column = 0; column < spriteMap.getWidth() / 32; column++) {
                this.sprites[row][column] = spriteMap.getSubimage(column * 32, row * 48, 32, 48);
            }
        }

        this.sprite = this.sprites[1][0];
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.position.getX() + 4, this.position.getY() + 22, this.width - 8, this.height - 22);

        this.moveSpeed = 1;
        this.direction = 1;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
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

    @Override
    public void update() {
        this.collider.setRect(this.position.getX() + 4, this.position.getY() + 24, this.width - 8, this.height - 24);

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
