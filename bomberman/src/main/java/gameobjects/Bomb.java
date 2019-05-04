package gameobjects;

import util.ResourceCollection;
import util.Vector2D;

import java.awt.geom.Rectangle2D;

public class Bomb extends GameObject {

    private Bomber bomber;

    private int firepower;
    private int timer;
    private int startTime;


    public Bomb(int firepower, int timer, Bomber bomber) {
        super(ResourceCollection.Images.BOMB.getImage());
        this.position = new Vector2D();
        this.collider = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
        this.firepower = firepower;
        this.timer = timer;
        this.bomber = bomber;
        this.startTime = 0;
    }

    private void explode() {
        Rectangle2D.Double recH = new Rectangle2D.Double(this.position.getX() + 1, this.position.getY() + 1, this.width - 2, this.height - 2);
        Rectangle2D.Double recV = new Rectangle2D.Double(this.position.getX() + 1, this.position.getY() + 1, this.width - 2, this.height - 2);
        boolean checkLeft = true;
        boolean checkRight = true;
        boolean checkTop = true;
        boolean checkBottom = true;
        for (int i = 0; i <= this.firepower; i++) {
            System.out.println("i = " + i);
            if (checkLeft) {
                recH.setRect(this.position.getX() - (i * 32), recH.y, recH.width * i, 32);
            }
            if (checkRight) {
                recH.setRect(recH.x, recH.y, ((i * 2) + 1) * 32, 32);
            }
            if (checkTop) {
                recV.setRect(recV.x, this.position.getY() - (i * 32), 32, recV.height * i);
            }
            if (checkBottom) {
                recV.setRect(recV.x, recV.y, 32, ((i * 2) + 1) * 32);
            }

            for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                System.out.println("index = " + index);
                GameObject obj = GameObjectCollection.wallObjects.get(index);

                if (recH.intersects(obj.collider)) {
                    System.out.println("recH intersects " + obj);
                    Rectangle2D intersection = recH.createIntersection(obj.collider);
                    // From the left
                    if (checkLeft && intersection.getMaxX() >= recH.getMaxX()) {
                        System.out.println("recH intersects from left");
                        checkLeft = false;
                        recH.setRect(this.position.getX() - (i * 32) + 32, recH.y, recH.width - 32, 32);
                    }
                    // From the right
                    if (checkRight && intersection.getMaxX() >= obj.collider.getMaxX()) {
                        System.out.println("recH intersects from right");
                        checkRight = false;
                        recH.setRect(recH.x, recH.y, recH.width - 32, 32);
                    }
                }

                if (recV.intersects(obj.collider)) {
                    System.out.println("recV intersects " + obj);
                    Rectangle2D intersection = recV.createIntersection(obj.collider);
                    // From the top
                    if (checkTop && intersection.getMaxY() >= recV.getMaxY()) {
                        System.out.println("recV intersects from top");
                        checkTop = false;
                        recV.setRect(recV.x, this.position.getY() - (i * 32) + 32, 32, recV.height - 32);
                    }
                    // From the bottom
                    if (checkBottom && intersection.getMaxY() >= obj.collider.getMaxY()) {
                        System.out.println("recV intersects from bottom");
                        checkBottom = false;
                        recV.setRect(recV.x, recV.y, 32, recV.height - 32);
                    }
                }
            }
        }

        this.instantiate(new Explosion.Horizontal(this.firepower, recH), this.position.add(this.originOffset), 0);
        this.instantiate(new Explosion.Vertical(this.firepower, recV), this.position.add(this.originOffset), 0);
        this.bomber.restoreAmmo();
    }

    @Override
    public void update() {
        if (this.startTime++ >= this.timer) {
            this.explode();
            this.destroy();
        }
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.collides(this);
    }

}
