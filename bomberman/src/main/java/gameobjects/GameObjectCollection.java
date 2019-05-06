package gameobjects;

import java.util.ArrayList;
import java.util.List;

public class GameObjectCollection {

    public static List<List<? extends GameObject>> gameObjects;

    public static ArrayList<Wall> wallObjects;
    public static ArrayList<Bomb> bombObjects;
    public static ArrayList<Explosion> explosionObjects;
    public static ArrayList<Bomber> bomberObjects;

    public static void init() {
        gameObjects = new ArrayList<>();

        wallObjects = new ArrayList<>();
        bombObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();
        bomberObjects = new ArrayList<>();

        gameObjects.add(wallObjects);
        gameObjects.add(bombObjects);
        gameObjects.add(explosionObjects);
        gameObjects.add(bomberObjects);
    }

    public static void spawn(GameObject spawnObj) {
        if (spawnObj instanceof Bomb) {
            bombObjects.add((Bomb) spawnObj);
        } else if (spawnObj instanceof Explosion) {
            explosionObjects.add((Explosion) spawnObj);
        }
    }

}
