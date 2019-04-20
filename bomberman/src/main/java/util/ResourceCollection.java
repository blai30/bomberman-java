package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceCollection {

    public enum Images {
        ICON,
        BACKGROUND,
        HARD_WALLS,
        BOMBER,
        BOMB,
        EXPLOSION;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public enum Files {
        DEFAULT_MAP;
    }

    public static void init() {
        try {
            System.out.println(System.getProperty("user.dir"));
            Images.ICON.image = ImageIO.read(ResourceCollection.class.getResource("/resources/icon.png"));
            Images.BACKGROUND.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bg.jpg"));
            Images.HARD_WALLS.image = ImageIO.read(ResourceCollection.class.getResource("/resources/hardWalls.jpg"));
            Images.BOMBER.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomber.jpg"));
            Images.BOMB.image = ImageIO.read(ResourceCollection.class.getResource("/resources/bomb.jpg"));
            Images.EXPLOSION.image = ImageIO.read(ResourceCollection.class.getResource("/resources/explosion.jpg"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
