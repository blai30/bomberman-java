package gameobjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class Bomber extends Player {

    private BufferedImage[][] sprites;
    private BufferedImage baseSprite;

    private Bomb bomb;

    private int direction;  // 0: up, 1: down, 2: left, 3: right
    private int spriteIndex;
    private int spriteTimer;

    private LinkedHashMap<String, Integer> statsCollection;
    private int moveSpeed;
    private int firePower;
    private int bombAmmo;
    private int bombTimer;
    private int score;

    public Bomber(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 2, this.position.y + 16 + 2, this.width - 4, this.height - 16 - 4);

        this.sprites = spriteMap;
        this.baseSprite = sprite;

        this.direction = 1;
        this.spriteIndex = 0;
        this.spriteTimer = 0;

        this.statsCollection = new LinkedHashMap<>();
        this.moveSpeed = 1;
        this.firePower = 1;
        this.bombAmmo = 3;
        this.bombTimer = 250;
    }

    private void moveUp() {
        this.direction = 0;
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }
    private void moveDown() {
        this.direction = 1;
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }
    private void moveLeft() {
        this.direction = 2;
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }
    private void moveRight() {
        this.direction = 3;
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    private void plantBomb() {
        float x = Math.round(this.position.getX() / 32) * 32;
        float y = Math.round((this.position.getY() + 16) / 32) * 32;
        Point2D.Float spawnLocation = new Point2D.Float(x, y);
        for (int i = 0; i < GameObjectCollection.bombObjects.size(); i++) {
            GameObject obj = GameObjectCollection.bombObjects.get(i);
            if (obj.collider.contains(spawnLocation)) {
                return; // Only one bomb allowed per tile; Cannot place a bomb on a bomb
            }
        }
        this.bomb = new Bomb(spawnLocation, this.firePower, this.bombTimer, this);
        this.instantiate(this.bomb);
        this.bombAmmo--;
    }

    public void restoreAmmo() {
        this.bombAmmo++;
    }

    public BufferedImage getBaseSprite() {
        return this.baseSprite;
    }

    public LinkedHashMap<String, Integer> getStats() {
        this.statsCollection.put("Speed", this.moveSpeed);
        this.statsCollection.put("Power", this.firePower);
        this.statsCollection.put("Bombs", this.bombAmmo);
        this.statsCollection.put("Timer", this.bombTimer);
        this.statsCollection.put("Score", this.score);

        return this.statsCollection;
    }

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
    public void onDestroy() {

    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

}
