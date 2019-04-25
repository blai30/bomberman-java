import gameobjects.GameObject;
import gameobjects.GameObjectCollection;
import gameobjects.Wall;
import util.ResourceCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;

    private BufferedImage world;
    private Graphics2D buffer;
    private BufferedImage bg;

    private String mapFile;
    private ArrayList<ArrayList<String>> mapLayout;
    private BufferedReader bufferedReader;

    public GamePanel(String filename) {
        this.setFocusable(true);
        this.requestFocus();
        this.bg = ResourceCollection.Images.BACKGROUND.getImage();
        this.loadMapFile(filename);
    }

    public void init() {
        GameObjectCollection.init();
        this.world = new BufferedImage(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.generateMap();
        this.running = true;
    }

    private void loadMapFile(String mapFile) {
        this.mapFile = mapFile;

        // Loading map file
        try {
            this.bufferedReader = new BufferedReader(new FileReader(mapFile));
        } catch (IOException | NullPointerException e) {
            // Load default map when map file could not be loaded
            System.err.println(e + ": Cannot load map file, loading default map");
            this.bufferedReader = new BufferedReader(ResourceCollection.Files.DEFAULT_MAP.getFile());
        }

        // Parsing map data from file
        this.mapLayout = new ArrayList<>();
        try {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                // Split row into array of strings and add to array list
                mapLayout.add(new ArrayList<>(Arrays.asList(currentLine.split(","))));
            }
        } catch (IOException | NullPointerException e) {
            System.out.println(e + ": Error parsing map data");
            e.printStackTrace();
        }
    }

    private void generateMap() {
        // Map dimensions
        int mapWidth = mapLayout.get(0).size();
        int mapHeight = mapLayout.size();

        this.world = new BufferedImage(mapWidth * 32, mapHeight * 32, BufferedImage.TYPE_INT_RGB);
//        this.gameHUD = new GameHUD(this.world);

        // Generate entire map
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                switch (mapLayout.get(y).get(x)) {
                    case ("S"):
                        BufferedImage sprSoftWall = ResourceCollection.Images.SOFT_WALL.getImage();
                        Wall softWall = new Wall(x * 32, y * 32, sprSoftWall, true);
                        GameObjectCollection.wallObjects.add(softWall);
                        break;

                    case ("H"):
                        // Code used to choose tile based on adjacent tiles
                        int code = 0;
                        if (y > 0 && mapLayout.get(y - 1).get(x).equals("H")) {
                            code += 1;  // North
                        }
                        if (y < mapHeight - 1 && mapLayout.get(y + 1).get(x).equals("H")) {
                            code += 4;  // South
                        }
                        if (x > 0 && mapLayout.get(y).get(x - 1).equals("H")) {
                            code += 8;  // West
                        }
                        if (x < mapWidth - 1 && mapLayout.get(y).get(x + 1).equals("H")) {
                            code += 2;  // East
                        }
                        BufferedImage sprHardWall = ResourceCollection.getHardWallTile(code);
                        Wall hardWall = new Wall(x * 32, y * 32, sprHardWall, false);
                        GameObjectCollection.wallObjects.add(hardWall);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public void addNotify() {
        super.addNotify();

        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
    }

    private void update() {
        try {
            Thread.sleep(1000 / 144);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        final double NS = 1000000000.0 / 60.0; // Locked ticks per second to 60
        double delta = 0;
        int fps = 0;    // Frames per second
        int ticks = 0;  // Ticks/Updates per second; should be 60 at all times

        // Count FPS, Ticks, and execute updates
        while (this.running) {
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / NS;
            lastTime = currentTime;
            if (delta >= 1) {
                this.update();
                ticks++;
                delta--;
            }
            this.repaint();
            fps++;

            // Update FPS and Ticks counter every second
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.println("FPS: " + fps + ", Ticks: " + ticks);
                GameLauncher.window.setTitle(GameWindow.TITLE + " | " + "FPS: " + fps + ", Ticks: " + ticks);
                fps = 0;
                ticks = 0;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.world.createGraphics();
        this.buffer.clearRect(0, 0, this.world.getWidth(), this.world.getHeight());
        super.paintComponent(g2);

        // Set window background to black
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw background
        for (int i = 0; i < this.world.getWidth(); i += this.bg.getWidth()) {
            for (int j = 0; j < this.world.getHeight(); j += this.bg.getHeight()) {
                this.buffer.drawImage(this.bg, i, j, null);
            }
        }

        // Draw game objects
        for (int i = 0; i < GameObjectCollection.wallObjects.size(); i++) {
            GameObject obj = GameObjectCollection.wallObjects.get(i);
            obj.drawImage(this.buffer);
        }
        for (int i = 0; i < GameObjectCollection.bombObjects.size(); i++) {
            GameObject obj = GameObjectCollection.bombObjects.get(i);
            obj.drawImage(this.buffer);
        }
        for (int i = 0; i < GameObjectCollection.explosionObjects.size(); i++) {
            GameObject obj = GameObjectCollection.explosionObjects.get(i);
            obj.drawImage(this.buffer);
        }
        for (int i = 0; i < GameObjectCollection.bomberObjects.size(); i++) {
            GameObject obj = GameObjectCollection.bomberObjects.get(i);
            obj.drawImage(this.buffer);
        }

        g2.drawImage(this.world, 16, 48, null);

        g2.dispose();
        this.buffer.dispose();
    }

}
