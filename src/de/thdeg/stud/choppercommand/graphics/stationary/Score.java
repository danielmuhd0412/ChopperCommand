package de.thdeg.stud.choppercommand.graphics.stationary;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.base.GameObject;

import java.awt.*;

/**
 * Visual representation of the player's current score on the screen.
 */
public class Score extends GameObject {

    private final Color color;

    private int currentScore;

    /**
     * Generates a score display on the top left of the window.
     *
     * @param gameView Window in which the score display is drawn.
     */
    public Score(GameView gameView) {
        super(gameView);
        this.position = new Position(0, 0);
        this.size = 40.0;
        this.color = Color.black;
        this.rotation = 0;
        this.currentScore = 0;
    }

    /**
     * Adds the score to the game window.
     */
    @Override
    public void addToCanvas() {
        gameView.addTextToCanvas(String.valueOf(currentScore), position.x, position.y, size, color, rotation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStatus() {
    }

    /**
     * Sets the current score.
     *
     * @param currentScore The score to set.
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
}


