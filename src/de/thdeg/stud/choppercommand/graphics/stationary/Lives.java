package de.thdeg.stud.choppercommand.graphics.stationary;

import de.thdeg.stud.choppercommand.game.util.Player;
import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.base.GameObject;


/**
 * Visual representation of the player's lives on the screen.
 */
public class Lives extends GameObject {

    private static final String LIFE = "   LLL  \n" +
            "     LLL\n" +
            "     L  \n" +
            "LL  LLL \n" +
            " LLLLLLL\n" +
            "   LLLLL\n" +
            "    LLL \n" +
            "     L  \n" +
            "    LLL \n";

    private int currentLives;

    /**
     * Generates a life bar on the screen.
     *
     * @param gameView Window in which the life bar is drawn.
     */
    public Lives(GameView gameView) {
        super(gameView);
        this.position = new Position(GameView.WIDTH * 0.95, 0);
        this.size = 4.0;
        this.rotation = 0;
        this.currentLives = Player.MAX_LIVES;
    }

    /**
     * Adds the number of lives to the game window.
     */
    @Override
    public void addToCanvas() {
        for (int i = 0; i < currentLives; i++) {
            gameView.addBlockImageToCanvas(LIFE, position.x - i * 30, position.y, size, rotation);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStatus() {
    }

    /**
     * Sets the current number of lives.
     *
     * @param currentLives the current number of lives
     */
    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }
}
