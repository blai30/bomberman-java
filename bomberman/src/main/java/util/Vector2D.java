//package util;
//
//public class Vector2D {
//
//    private float x;
//    private float y;
//
//    public Vector2D() {
//        this.x = 0;
//        this.y = 0;
//    }
//
//    public Vector2D(float x, float y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public Vector2D(float num) {
//        this.x = num;
//        this.y = num;
//    }
//
//    public Vector2D(Vector2D vec) {
//        this.x = vec.x;
//        this.y = vec.y;
//    }
//
//    public float getX() {
//        return this.x;
//    }
//
//    public float getY() {
//        return this.y;
//    }
//
//    public void setX(float x) {
//        this.x = x;
//    }
//
//    public void setY(float y) {
//        this.y = y;
//    }
//
//    public void set(float x, float y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public void set(Vector2D vec) {
//        this.x = vec.x;
//        this.y = vec.y;
//    }
//
//    public void addX(float x) {
//        this.x += x;
//    }
//
//    public void addY(float y) {
//        this.y += y;
//    }
//
//    public void add(float x, float y) {
//        this.x += x;
//        this.y += y;
//    }
//
//    // Pseudo operator overloading
//    public Vector2D add(Vector2D vec) {
//        float x = this.x + vec.x;
//        float y = this.y + vec.y;
//        return new Vector2D(x, y);
//    }
//
//    public boolean equals(Vector2D vec) {
//        return this.x == vec.x && this.y == vec.y;
//    }
//
//    @Override
//    public String toString() {
//        return "(" + this.x + ", " + this.y + ")";
//    }
//
//}
