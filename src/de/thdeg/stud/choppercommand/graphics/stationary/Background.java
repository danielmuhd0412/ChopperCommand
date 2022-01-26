package de.thdeg.stud.choppercommand.graphics.stationary;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.GameObject;


/**
 * The background of the game.
 */
public class Background extends GameObject {

    /**
     * Generates the background.
     *
     * @param gameView Window in which the background is drawn.
     */
    public Background(GameView gameView) {
        super(gameView);
    }

    @Override
    protected void updateStatus() {

    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("bg.png", 0, 0, 1, 0);
    }
}
