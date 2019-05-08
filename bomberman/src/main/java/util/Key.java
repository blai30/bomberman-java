package util;

/**
 * Key bindings used for player controls.
 * Create a HashMap object that binds these keys with keys on a keyboard for player controls.
 *
 * Example:
 * HashMap<Integer, Key> controls = new HashMap<>();
 * controls.put(KeyEvent.VK_UP, Key.up);
 * controls.put(KeyEvent.VK_DOWN, Key.down);
 * controls.put(KeyEvent.VK_LEFT, Key.left);
 * controls.put(KeyEvent.VK_RIGHT, Key.right);
 * controls.put(KeyEvent.VK_SLASH, Key.action);
 */
public class Key {

    public static Key up = new Key();       // Up movement
    public static Key down = new Key();     // Down movement
    public static Key left = new Key();     // Left movement
    public static Key right = new Key();    // Right movement
    public static Key action = new Key();   // Place bomb

}
