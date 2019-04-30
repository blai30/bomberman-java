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
        window = new GameWindow(game);

        System.gc();
    }

}

class GameWindow extends JFrame {

    static final int HUD_HEIGHT = 48;
    static final String TITLE = "Bomberman by Brian Lai";

    GameWindow(GamePanel game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
