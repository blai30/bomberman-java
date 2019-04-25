package gameobjects;

import java.awt.image.BufferedImage;

public abstract class Player extends GameObject {

    protected boolean UpPressed = false;
    protected boolean DownPressed = false;
    protected boolean LeftPressed = false;
    protected boolean RightPressed = false;
    protected boolean ActionPressed = false;

    public Player(float xPos, float yPos, BufferedImage sprite) {
        super(xPos, yPos, sprite);
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleActionPressed() {
        this.ActionPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleActionPressed() {
        this.ActionPressed = false;
    }

}
