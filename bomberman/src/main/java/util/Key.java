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

    public static Key up = new Key();
    public static Key down = new Key();
    public static Key left = new Key();
    public static Key right = new Key();
    public static Key action = new Key();

}
