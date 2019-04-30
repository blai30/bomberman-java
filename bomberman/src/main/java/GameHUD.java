import gameobjects.Bomber;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    private Bomber[] players;
    private BufferedImage[] playerInfo;

    private int height;
    private int infoWidth;

    public GameHUD() {
        this.players = new Bomber[4];
        this.playerInfo = new BufferedImage[4];

        this.height = GameWindow.HUD_HEIGHT;
        this.infoWidth = GameWindow.SCREEN_WIDTH / 4;   // 4 players, 4 info boxes

        this.playerInfo[0] = new BufferedImage(this.infoWidth, this.height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[1] = new BufferedImage(this.infoWidth, this.height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[2] = new BufferedImage(this.infoWidth, this.height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[3] = new BufferedImage(this.infoWidth, this.height, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getP1info() {
        return this.playerInfo[0];
    }

    public BufferedImage getP2info() {
        return this.playerInfo[1];
    }

    public BufferedImage getP3info() {
        return this.playerInfo[2];
    }

    public BufferedImage getP4info() {
        return this.playerInfo[3];
    }

    public void assignPlayer(Bomber player, int playerID) {
        this.players[playerID] = player;
    }

    public void drawHUD() {
        Graphics[] playerGraphics = {
                this.playerInfo[0].createGraphics(),
                this.playerInfo[1].createGraphics(),
                this.playerInfo[2].createGraphics(),
                this.playerInfo[3].createGraphics()};

        playerGraphics[0].clearRect(0, 0, playerInfo[0].getWidth(), playerInfo[0].getHeight());
        playerGraphics[1].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());
        playerGraphics[2].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());
        playerGraphics[3].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());

        playerGraphics[0].setColor(Color.RED);      // Player 1 info box border color
        playerGraphics[1].setColor(Color.BLUE);     // Player 2 info box border color
        playerGraphics[2].setColor(Color.YELLOW);   // Player 3 info box border color
        playerGraphics[3].setColor(Color.GREEN);    // Player 4 info box border color

        // Iterate loop for each player
        for (int i = 0; i < playerGraphics.length; i++) {
            playerGraphics[i].drawRect(1, 1, this.playerInfo[i].getWidth() - 2, this.playerInfo[i].getHeight() - 2);
            playerGraphics[i].drawImage(this.players[i].getBaseSprite(), 0, 0, null);
        }
    }

}
