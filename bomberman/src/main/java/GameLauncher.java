import util.ResourceCollection;

import javax.swing.*;
import java.awt.*;

public class GameLauncher {

    public static void main(String[] args) {
        ResourceCollection.init();
        new GameWindow();
    }

}

class GameWindow extends JFrame {

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 960;
    static final String TITLE = "Bomberman by Brian Lai";

    GameWindow() {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

}
