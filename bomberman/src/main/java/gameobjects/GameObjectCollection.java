package gameobjects;

import java.util.ArrayList;

public class GameObjectCollection {

    static ArrayList<Bomber> bomberObjects;
    static ArrayList<Wall> wallObjects;
    static ArrayList<Bomb> bombObjects;
    static ArrayList<Explosion> explosionObjects;

    public static void init() {
        bomberObjects = new ArrayList<>();
        wallObjects = new ArrayList<>();
        bombObjects = new ArrayList<>();
        explosionObjects = new ArrayList<>();
    }

}
