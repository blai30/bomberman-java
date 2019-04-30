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
        PLAYER_1,
        PLAYER_2,
        PLAYER_3,
        PLAYER_4,
        SOFT_WALL,
        HARD_WALLS,
        BOMB,
        EXPLOSION;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
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

    public static void init() {
        try {
            System.out.println(System.getProperty("user.dir"));
            Images.ICON.image = ImageIO.read(ResourceCollection.class.getResource("/resources/icon.png"));
            Images.BACKGROUND.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bg.png"));
            Images.PLAYER_1.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber1.png"));
            Images.PLAYER_2.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber2.png"));
            Images.PLAYER_3.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber3.png"));
            Images.PLAYER_4.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber4.png"));
            Images.SOFT_WALL.image = ImageIO.read(ResourceCollection.class.getResource("/resources/softWall.png"));
            Images.HARD_WALLS.image = ImageIO.read(ResourceCollection.class.getResource("/resources/hardWalls.png"));
            Images.BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomb.png"));
            Images.EXPLOSION.image = ImageIO.read(ResourceCollection.class.getResource("/resources/explosion.png"));

            Files.DEFAULT_MAP.file = new InputStreamReader(ResourceCollection.class.getResourceAsStream("/resources/default.csv"));

            // Load hard wall tiles
            BufferedImage[][] tiles = new BufferedImage[5][4];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    assert Images.HARD_WALLS.image != null;
                    tiles[i][j] = Images.HARD_WALLS.image.getSubimage(i * 32, j * 32, 32, 32);
                }
            }
            loadTiles(tiles);
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
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

        hardWallTiles.put(0b0001, tiles[2][0]);  // N
        hardWallTiles.put(0b0010, tiles[3][0]);  // E
        hardWallTiles.put(0b0100, tiles[1][0]);  // S
        hardWallTiles.put(0b1000, tiles[4][0]);  // W

        hardWallTiles.put(0b0011, tiles[3][2]);  // N E
        hardWallTiles.put(0b1001, tiles[4][2]);  // N W
        hardWallTiles.put(0b0110, tiles[1][2]);  // S E
        hardWallTiles.put(0b1100, tiles[2][2]);  // S W

        hardWallTiles.put(0b1010, tiles[0][3]);  // W E
        hardWallTiles.put(0b0101, tiles[0][2]);  // N S

        hardWallTiles.put(0b1011, tiles[2][1]);  // N E W
        hardWallTiles.put(0b0111, tiles[3][1]);  // N E S
        hardWallTiles.put(0b1110, tiles[1][1]);  // S E W
        hardWallTiles.put(0b1101, tiles[4][1]);  // S W N

        hardWallTiles.put(0b1111, tiles[0][1]);  // N S W E
    }

}
