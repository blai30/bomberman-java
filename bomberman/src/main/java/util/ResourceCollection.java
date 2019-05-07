package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ResourceCollection {

    private static HashMap<Integer, BufferedImage> hardWallTiles;

    public enum Images {
        ICON,
        BACKGROUND,
        SOFT_WALL,
        POWER_BOMB,
        POWER_FIREUP,
        POWER_FIREMAX,
        POWER_SPEED,
        POWER_PIERCE,
        POWER_KICK;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public enum SpriteMaps {
        PLAYER_1,
        PLAYER_2,
        PLAYER_3,
        PLAYER_4,
        HARD_WALLS,
        BOMB,
        EXPLOSION_SPRITEMAP;

        private BufferedImage image = null;
        private BufferedImage[][] sprites = null;

        public BufferedImage getImage() {
            return this.image;
        }

        public BufferedImage[][] getSprites() {
            return this.sprites;
        }
    }

    public enum Files {
        DEFAULT_MAP;

        private InputStreamReader file = null;

        public InputStreamReader getFile() {
            return this.file;
        }
    }

    public static BufferedImage getHardWallTile(Integer key) {
        return hardWallTiles.get(key);
    }

    public static void readFiles() {
        try {
            System.out.println(System.getProperty("user.dir"));
            Images.ICON.image = ImageIO.read(ResourceCollection.class.getResource("/resources/icon.png"));
            Images.BACKGROUND.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bg.png"));
            Images.SOFT_WALL.image = ImageIO.read(ResourceCollection.class.getResource("/resources/softWall.png"));
            Images.POWER_BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_bomb.png"));
            Images.POWER_FIREUP.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_fireup.png"));
            Images.POWER_FIREMAX.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_firemax.png"));
            Images.POWER_SPEED.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_speed.png"));
            Images.POWER_PIERCE.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_pierce.png"));
            Images.POWER_KICK.image = ImageIO.read(ResourceCollection.class.getResource("/resources/power_kick.png"));

            SpriteMaps.PLAYER_1.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber1.png"));
            SpriteMaps.PLAYER_2.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber2.png"));
            SpriteMaps.PLAYER_3.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber3.png"));
            SpriteMaps.PLAYER_4.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber4.png"));
            SpriteMaps.HARD_WALLS.image = ImageIO.read(ResourceCollection.class.getResource("/resources/hardWalls.png"));
            SpriteMaps.BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomb.png"));
            SpriteMaps.EXPLOSION_SPRITEMAP.image = ImageIO.read(ResourceCollection.class.getResource("/resources/explosion.png"));

            Files.DEFAULT_MAP.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/resources/default.csv"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

    public static void init() {
        SpriteMaps.PLAYER_1.sprites = loadSpriteMap(SpriteMaps.PLAYER_1.image, 32, 48);
        SpriteMaps.PLAYER_2.sprites = loadSpriteMap(SpriteMaps.PLAYER_2.image, 32, 48);
        SpriteMaps.PLAYER_3.sprites = loadSpriteMap(SpriteMaps.PLAYER_3.image, 32, 48);
        SpriteMaps.PLAYER_4.sprites = loadSpriteMap(SpriteMaps.PLAYER_4.image, 32, 48);
        SpriteMaps.HARD_WALLS.sprites = loadSpriteMap(SpriteMaps.HARD_WALLS.image, 32, 32);
        SpriteMaps.BOMB.sprites = loadSpriteMap(SpriteMaps.BOMB.image, 32, 32);
        SpriteMaps.EXPLOSION_SPRITEMAP.sprites = loadSpriteMap(SpriteMaps.EXPLOSION_SPRITEMAP.image, 32, 32);
        loadTiles(SpriteMaps.HARD_WALLS.sprites);   // Load hard wall tiles into hashmap for bit masking
    }

    private static BufferedImage[][] loadSpriteMap(BufferedImage spriteMap, int spriteWidth, int spriteHeight) {
        int rows = spriteMap.getHeight() / spriteHeight;
        int cols = spriteMap.getWidth() / spriteWidth;
        BufferedImage[][] sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                assert SpriteMaps.PLAYER_4.image != null;
                sprites[row][col] = spriteMap.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        return sprites;
    }

    /**
     * Called in loadMap to load tile map for hard wall sprites.
     * The correct sprite is chosen for hard wall based on adjacent hard walls.
     * @param tiles Double array of sliced tile map
     */
    private static void loadTiles(BufferedImage[][] tiles) {
        hardWallTiles = new HashMap<>();
        /*
            [ ][1][ ]
            [8][X][2]
            [ ][4][ ]
            1st bit = north
            2nd bit = east
            3rd bit = south
            4th bit = west
            These bits indicate if there is an adjacent hard wall in that direction
         */
        hardWallTiles.put(0b0000, tiles[0][0]);  // 0

        hardWallTiles.put(0b0001, tiles[0][2]);  // N
        hardWallTiles.put(0b0010, tiles[0][3]);  // E
        hardWallTiles.put(0b0100, tiles[0][1]);  // S
        hardWallTiles.put(0b1000, tiles[0][4]);  // W

        hardWallTiles.put(0b0011, tiles[2][3]);  // N E
        hardWallTiles.put(0b1001, tiles[2][4]);  // N W
        hardWallTiles.put(0b0110, tiles[2][1]);  // S E
        hardWallTiles.put(0b1100, tiles[2][2]);  // S W

        hardWallTiles.put(0b1010, tiles[3][0]);  // W E
        hardWallTiles.put(0b0101, tiles[2][0]);  // N S

        hardWallTiles.put(0b1011, tiles[1][2]);  // N E W
        hardWallTiles.put(0b0111, tiles[1][3]);  // N E S
        hardWallTiles.put(0b1110, tiles[1][1]);  // S E W
        hardWallTiles.put(0b1101, tiles[1][4]);  // S W N

        hardWallTiles.put(0b1111, tiles[1][0]);  // N S W E
    }

}
