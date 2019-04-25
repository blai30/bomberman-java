import util.ResourceCollection;

import javax.swing.*;
import java.awt.*;

public class GameLauncher {

    protected static GameWindow window;

    public static void main(String[] args) {
        ResourceCollection.init();
        GamePanel game;
        try {
            game = new GamePanel(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e + ": Program args not given");
            game = new GamePanel(null);
        }
        game.init();
        game.setPreferredSize(new Dimension(GameWindow.SCREEN_WIDTH,GameWindow.SCREEN_HEIGHT));
        window = new GameWindow();
        window.add(game, BorderLayout.CENTER);
        window.pack();

        window.setVisible(true);

        System.gc();
    }

}

class GameWindow extends JFrame {

    static final int SCREEN_WIDTH = 480;
    static final int SCREEN_HEIGHT = 464;
    static final String TITLE = "Bomberman by Brian Lai";

    GameWindow() {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
