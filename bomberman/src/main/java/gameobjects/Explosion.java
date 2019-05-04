package gameobjects;

import util.Vector2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Explosion extends GameObject {

    public static class Horizontal extends Explosion {

        Horizontal(int firepower, Vector2D spawnLocation) {
            this.position = new Vector2D(spawnLocation);

            Point2D pointL = new Point2D.Double(this.position.getX(), this.position.getY());
            outerloop:
            for (int i = 1; i <= firepower; i++) {
                pointL.setLocation(pointL.getX() - (i * 32), pointL.getY());
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    GameObject obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(pointL)) {
                        pointL.setLocation(pointL.getX() + 32, pointL.getY());
                        break outerloop;
                    }
                }
            }
            Point2D pointR = new Point2D.Double(this.position.getX(), this.position.getY());
            outerloop:
            for (int i = 1; i <= firepower; i++) {
                pointR.setLocation(pointR.getX() + (i * 32), pointR.getY());
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    GameObject obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(pointR)) {
                        pointR.setLocation(pointR.getX() - 32, pointR.getY());
                        break outerloop;
                    }
                }
            }

            Rectangle2D.Double recH = new Rectangle2D.Double(pointL.getX(), this.position.getY(), pointR.getX() - pointL.getX() + 32, 32);
            this.collider = recH;
            this.width = (float) this.collider.width;
            this.height = (float) this.collider.height;
            this.originOffset = new Vector2D(this.width / 2, this.height / 2);
            this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = this.sprite.createGraphics();
            g2.setColor(new Color(0, 0, 0, 0));
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

            Point2D pointT = new Point2D.Double(this.position.getX(), this.position.getY());
            outerloop:
            for (int i = 1; i <= firepower; i++) {
                pointT.setLocation(pointT.getX(), pointT.getY() - (i * 32));
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    GameObject obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(pointT)) {
                        pointT.setLocation(pointT.getX(), pointT.getY() + 32);
                        break outerloop;
                    }
                }
            }
            Point2D pointB = new Point2D.Double(this.position.getX(), this.position.getY());
            outerloop:
            for (int i = 1; i <= firepower; i++) {
                pointB.setLocation(pointB.getX(), pointB.getY() + (i * 32));
                for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                    GameObject obj = GameObjectCollection.wallObjects.get(index);
                    if (obj.collider.contains(pointB)) {
                        pointB.setLocation(pointB.getX(), pointB.getY() - 32);
                        break outerloop;
                    }
                }
            }

            Rectangle2D.Double recV = new Rectangle2D.Double(this.position.getX(), pointT.getY(), 32, pointB.getY() - pointT.getY() + 32);
            this.collider = recV;
            this.width = (float) this.collider.width;
            this.height = (float) this.collider.height;
            this.originOffset = new Vector2D(this.width / 2, this.height / 2);
            this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = this.sprite.createGraphics();
            g2.setColor(new Color(0, 0, 0, 0));
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

}
