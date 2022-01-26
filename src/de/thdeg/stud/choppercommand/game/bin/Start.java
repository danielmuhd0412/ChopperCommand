package de.thdeg.stud.choppercommand.game.bin;

import de.thdeg.stud.choppercommand.game.managers.GameLoopManager;
import de.thdeg.stud.choppercommand.game.util.NoMoreLevelsAvailableException;

/**
 * Launches the game.
 */
public class Start {

    public static void main(String[] args) throws NoMoreLevelsAvailableException {

        GameLoopManager gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();
    }
}
