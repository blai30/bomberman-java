package gameobjects;

import java.util.ArrayList;
import java.util.List;

public class GameObjectCollection {

    public static List<List<? extends GameObject>> gameObjects;

    public static ArrayList<Wall> wallObjects;
    public static ArrayList<Bomb> bombObjects;
    public static ArrayList<Bomber> bomberObjects;
    public static ArrayList<Explosion> explosionObjects;

    public static void init() {
        gameObjects = new ArrayList<>();

        wallObjects = new ArrayList<>();
        bombObjects = new ArrayList<>();
        bomberObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();

        gameObjects.add(wallObjects);
        gameObjects.add(bombObjects);
        gameObjects.add(bomberObjects);
        gameObjects.add(explosionObjects);
    }

}
