package gameobjects;

import util.Vector2D;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {

    private BufferedImage[] sprites;
    private int spriteIndex;
    private int spriteTimer;

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

//
//        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
//
//        this.position = new Vector2D();
//        this.width = ((this.firepower * 2) + 1) * 32;
//        this.height = 32;
//        this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2 = this.sprite.createGraphics();
//        g2.setColor(new Color(0, 0, 0, 0));
//        g2.fillRect(0, 0, (int) this.width, (int) this.height);
//        g2.drawImage(this.sprites[0][0], ((this.firepower / 2) * 32) + 32, 0, null);
//        g2.drawImage(this.sprites[2][0], 0, 0, null);
//        g2.drawImage(this.sprites[2][0], (this.firepower * 2) * 32, 0, null);
//
//        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
    }

    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.sprites.length) {
            this.destroy();
        } else {
            this.sprite = this.sprites[this.spriteIndex];
        }
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

}
