package gameobjects;

import java.util.ArrayList;

public class GameObjectCollection {

    public static ArrayList<Bomber> bomberObjects;
    public static ArrayList<Wall> wallObjects;
    public static ArrayList<Bomb> bombObjects;
    public static ArrayList<Explosion> explosionObjects;

    public static void init() {
        bomberObjects = new ArrayList<>();
        wallObjects = new ArrayList<>();
        bombObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();
    }

}
