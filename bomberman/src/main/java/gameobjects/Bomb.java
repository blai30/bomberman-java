package gameobjects;

import util.ResourceCollection;
import util.Vector2D;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
//        Line2D line1 = new Line2D.Double(this.position.getX() + this.originOffset.getX(), this.position.getY() + this.originOffset.getY(), )
//        Point2D point1 = new Point2D.Double(this.position.getX() - (firepower * 32) + this.originOffset.getX(), this.position.getY() + this.originOffset.getY());
//        Rectangle2D recH = new Rectangle2D.Double(this.position.getX() - (firepower * 32), this.position.getY(), ((firepower * 2) + 1) * 32, 32);
//        Rectangle2D recV = new Rectangle2D.Double(this.position.getX(), this.position.getY() - (firepower * 32), 32, ((firepower * 2) + 1) * 32);
//        if (recH.intersects())
        Rectangle2D recH = new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height);
        for (int i = 0; i < this.firepower; i++) {
            recH.setRect(this.position.getX() - (i * 32), this.position.getY(), ((i * 2) + 1) * 32, 32);
            for (int index = 0; index < GameObjectCollection.wallObjects.size(); index++) {
                GameObject obj = GameObjectCollection.wallObjects.get(index);
                if (recH.intersects(obj.collider)) {
                    Rectangle2D intersection = recH.createIntersection(obj.collider);
                    // From the top
                    if (intersection.getMaxY() >= this.collider.getMaxY()) {

                    }
                    // From the bottom
                    if (intersection.getMaxY() >= obj.collider.getMaxY()) {

                    }
                    // From the left
                    if (intersection.getMaxX() >= this.collider.getMaxX()) {

                    }
                    // From the right
                    if (intersection.getMaxX() >= obj.collider.getMaxX()) {

                    }
                }
            }
        }
        this.instantiate(new Explosion.Horizontal(this.firepower), this.position.add(this.originOffset), 0);
        this.instantiate(new Explosion.Vertical(this.firepower), this.position.add(this.originOffset), 0);
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
