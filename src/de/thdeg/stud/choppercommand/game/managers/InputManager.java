package de.thdeg.stud.choppercommand.game.managers;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.movable.PlayerHeli;

import java.awt.event.KeyEvent;

/**
 * Manages user input.
 */
class InputManager {

    private final GameView gameView;
    private final PlayerHeli playerHeli;

    private final static boolean ALLOW_DIAGONAL_MOVEMENT = false;

    InputManager(GameView gameView, PlayerHeli playerHeli) {
        this.gameView = gameView;
        this.playerHeli = playerHeli;

    }

    void updateUserInputs() {
        Integer[] pressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        if (ALLOW_DIAGONAL_MOVEMENT) {
            for (int keyCode : pressedKeys) {
                movePlayerWith(keyCode);
            }
        } else {
            if (pressedKeys.length > 0) {
                movePlayerWith(pressedKeys[0]);
            }
        }
    }

    private void movePlayerWith(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                playerHeli.up();
                break;
            case KeyEvent.VK_DOWN:
                playerHeli.down();
                break;
            case KeyEvent.VK_LEFT:
                playerHeli.left();
                break;
            case KeyEvent.VK_RIGHT:
                playerHeli.right();
                break;
            case KeyEvent.VK_SPACE:
                playerHeli.shoot();
                break;
        }
    }
}
