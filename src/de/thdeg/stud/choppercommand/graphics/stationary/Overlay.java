package de.thdeg.stud.choppercommand.graphics.stationary;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.GameObject;

import java.awt.*;

/**
 * Overlay to display messages on the screen.
 */
public class Overlay extends GameObject {

    private String message;

    /**
     * Generates the overlay.
     * @param gameView Window in which the overlay is drawn.
     */
    public Overlay(GameView gameView) {
        super(gameView);
        this.message = "";
    }

    @Override
    protected void updateStatus() {

    }

    @Override
    public void addToCanvas() {
        if (!gameView.timerExpired("message", "message")) {
            gameView.addTextToCanvas(message, 320, 150, 36, Color.blue, 0);
        }
    }

    /**
     * Displays a message on the game window.
     * @param message The message to show on the screen.
     */
    public void showMessage(String message) {
        if (gameView.timerExpired("message", "message")) {
            gameView.setTimer("message", "message", 500);
            this.message = message;
        }
    }
}
