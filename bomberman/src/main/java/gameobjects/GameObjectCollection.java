package gameobjects;

import java.util.ArrayList;
import java.util.List;

public class GameObjectCollection {

    public static List<List<? extends GameObject>> gameObjects;

    public static ArrayList<Bomber> bomberObjects;
    public static ArrayList<Wall> wallObjects;
    public static ArrayList<Bomb> bombObjects;
    public static ArrayList<Explosion> explosionObjects;

    public static void init() {
        gameObjects = new ArrayList<>();

        bomberObjects = new ArrayList<>();
        wallObjects = new ArrayList<>();
        bombObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();

        gameObjects.add(bomberObjects);
        gameObjects.add(wallObjects);
        gameObjects.add(bombObjects);
        gameObjects.add(explosionObjects);
    }

}
