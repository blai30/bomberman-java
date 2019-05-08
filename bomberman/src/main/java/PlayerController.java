import gameobjects.Player;
import util.Key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * This class controls a player object through user input by listening for key events.
 */
public class PlayerController implements KeyListener {

    private Player player;
    private HashMap<Integer, Key> controls;

    /**
     * Assigns controls to a player game object.
     * @param obj The player game object to be controlled
     * @param controls The controls that will control the player game object
     */
    public PlayerController(Player obj, HashMap<Integer, Key> controls) {
        this.player = obj;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unneeded method; ignored
    }

    /**
     * Reads the keys pressed and performs certain actions based on the key.
     * @param e The key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.controls.get(e.getKeyCode()) == Key.up) {
            this.player.toggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.down) {
            this.player.toggleDownPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.left) {
            this.player.toggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.right) {
            this.player.toggleRightPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.action) {
            this.player.toggleActionPressed();
        }
    }

    /**
     * Reads the keys released and performs certain actions based on the key.
     * @param e The key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (this.controls.get(e.getKeyCode()) == Key.up) {
            this.player.unToggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.down) {
            this.player.unToggleDownPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.left) {
            this.player.unToggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.right) {
            this.player.unToggleRightPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Key.action) {
            this.player.unToggleActionPressed();
        }
    }

}
