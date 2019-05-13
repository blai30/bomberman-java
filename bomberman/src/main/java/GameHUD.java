import gameobjects.Bomber;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Displays various game information on the screen such as each player's score.
 */
public class GameHUD {

    private Bomber[] players;
    private BufferedImage[] playerInfo;
    private int[] playerScore;
    boolean matchSet;

    GameHUD() {
        this.players = new Bomber[4];
        this.playerInfo = new BufferedImage[4];
        this.playerScore = new int[4];
        this.matchSet = false;
    }

    void init() {
        // Height of the HUD
        int height = GameWindow.HUD_HEIGHT;
        // Width of each player's information in the HUD, 4 players, 4 info boxes
        int infoWidth = GamePanel.panelWidth / 4;

        this.playerInfo[0] = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[1] = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[2] = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
        this.playerInfo[3] = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
    }

    BufferedImage getP1info() {
        return this.playerInfo[0];
    }
    BufferedImage getP2info() {
        return this.playerInfo[1];
    }
    BufferedImage getP3info() {
        return this.playerInfo[2];
    }
    BufferedImage getP4info() {
        return this.playerInfo[3];
    }

    /**
     * Assign an info box to a player that shows the information on this player.
     * @param player The player to be assigned
     * @param playerID Used as an index for the array
     */
    void assignPlayer(Bomber player, int playerID) {
        this.players[playerID] = player;
    }

    /**
     * Checks if there is only one player alive left and increases their score.
     * The match set boolean is used to check if a point is already added so that the winner can freely
     * move around for a while before resetting the map. This also allows the winner to kill themselves without
     * affecting their score since the score was already updated.
     */
    public void updateScore() {
        int deadPlayers = 0;
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i].isDead()) {
                deadPlayers++;
            }
        }

        if (deadPlayers == this.players.length - 1) {
            for (int i = 0; i < this.players.length; i++) {
                if (!this.players[i].isDead()) {
                    this.playerScore[i]++;
                    this.matchSet = true;
                }
            }
        } else if (deadPlayers >= this.players.length) {
            this.matchSet = true;
        }
    }

    /**
     * Continuously redraw player information such as score.
     */
    void drawHUD() {
        Graphics[] playerGraphics = {
                this.playerInfo[0].createGraphics(),
                this.playerInfo[1].createGraphics(),
                this.playerInfo[2].createGraphics(),
                this.playerInfo[3].createGraphics()};

        playerGraphics[0].clearRect(0, 0, playerInfo[0].getWidth(), playerInfo[0].getHeight());
        playerGraphics[1].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());
        playerGraphics[2].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());
        playerGraphics[3].clearRect(0, 0, playerInfo[1].getWidth(), playerInfo[1].getHeight());

        playerGraphics[0].setColor(Color.WHITE);    // Player 1 info box border color
        playerGraphics[1].setColor(Color.GRAY);     // Player 2 info box border color
        playerGraphics[2].setColor(Color.RED);      // Player 3 info box border color
        playerGraphics[3].setColor(Color.BLUE);     // Player 4 info box border color

        // Iterate loop for each player
        for (int i = 0; i < playerGraphics.length; i++) {
            Font font = new Font("Courier New", Font.BOLD, 24);
            playerGraphics[i].drawRect(1, 1, this.playerInfo[i].getWidth() - 2, this.playerInfo[i].getHeight() - 2);
            playerGraphics[i].drawImage(this.players[i].getBaseSprite(), 0, 0, null);
            playerGraphics[i].setFont(font);
            playerGraphics[i].setColor(Color.WHITE);
            playerGraphics[i].drawString("" + this.playerScore[i], this.playerInfo[i].getWidth() / 2, 32);
        }
    }

}
