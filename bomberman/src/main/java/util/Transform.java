package util;

public class Transform {

    private Vector2D position;
    private float rotation;

    public Transform() {
        this.position = new Vector2D();
        this.rotation = 0;
    }

    public Transform(float xPos, float yPos, float rotation) {
        this.position = new Vector2D(xPos, yPos);
        this.rotation = rotation;
    }

    public Transform(Transform transform) {
        this.position = new Vector2D(transform.getPositionX(), transform.getPositionY());
        this.rotation = transform.getRotation();
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public float getPositionX() {
        return this.position.getX();
    }

    public float getPositionY() {
        return this.position.getY();
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setTransform(float xPos, float yPos, float rotation) {
        this.position.set(xPos, yPos);
        this.rotation = rotation;
    }

    public void setPosition(Vector2D vec) {
        this.position.set(vec);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
