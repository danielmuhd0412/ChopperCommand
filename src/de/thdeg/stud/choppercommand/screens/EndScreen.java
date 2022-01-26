package de.thdeg.stud.choppercommand.screens;

import de.thdeg.stud.choppercommand.game.managers.GamePlayManager;
import de.thdeg.stud.choppercommand.gameview.GameView;

/**
 * The end screen shown after a game over.
 */
public class EndScreen {

    private String message;
    private GameView gameView;
    private GamePlayManager gamePlayManager;

    /**
     * Generates the end screen.
     * @param gameView Window in which the screen is drawn.
     */
    public EndScreen(GameView gameView) {
        this.gameView = gameView;
        this.message = "";

    }

    /**
     * Shows the end screen on the game window.
     */
    public void showEndScreen() {
        message = "You scored " + gamePlayManager.getPlayer().currentScore + " points. Good job!";
        gameView.showEndScreen(message);
    }

    /**
     * Sets the gamePlayManager.
     *
     * @param gamePlayManager Class that manages gameplay.
     */
    public void setGamePlayManager(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }
}
