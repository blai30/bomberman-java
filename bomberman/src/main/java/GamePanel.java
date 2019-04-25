import gameobjects.GameObjectCollection;
import util.ResourceCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            InputStreamReader defaultMap = new InputStreamReader(this.getClass().getResourceAsStream("/resources/defaultmap.csv"));
            this.bufferedReader = new BufferedReader(defaultMap);
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

        this.world = new BufferedImage(mapWidth * 64, mapHeight * 64, BufferedImage.TYPE_INT_RGB);
//        this.gameHUD = new GameHUD(this.world);

        // Generate entire map
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                switch (mapLayout.get(y).get(x)) {
                    case (""):
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

        g2.drawImage(this.world, 0, 0, null);

        g2.dispose();
        this.buffer.dispose();
    }

}
