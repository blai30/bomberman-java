package gameobjects;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Player class for game objects that will be controlled by the user.
 */
public abstract class Player extends GameObject {

    protected boolean UpPressed = false;
    protected boolean DownPressed = false;
    protected boolean LeftPressed = false;
    protected boolean RightPressed = false;
    protected boolean ActionPressed = false;

    /**
     * Passing parameters to GameObject constructor.
     * @param position
     * @param sprite
     */
    Player(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
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
