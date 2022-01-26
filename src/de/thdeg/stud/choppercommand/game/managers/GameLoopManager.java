package de.thdeg.stud.choppercommand.game.managers;

import de.thdeg.stud.choppercommand.game.util.NoMoreLevelsAvailableException;
import de.thdeg.stud.choppercommand.gameview.GameView;

/**
 * Manages the main game loop.
 */
public class GameLoopManager {

    private final GameView gameView;
    private final InputManager inputManager;
    private final GameObjectManager gameObjectManager;
    private final GamePlayManager gamePlayManager;

    /**
     * Creates the main game loop.
     */
    public GameLoopManager() {
        this.gameView = new GameView();
        this.gameView.setWindowTitle("Chopper Command");
        this.gameView.setStatusText("Bin Mohd Khir Muhammad Daniel - Java Programmierung SS 2021");
        this.gameView.setWindowIcon("icon.png");
        this.gameObjectManager = new GameObjectManager(gameView);
        this.inputManager = new InputManager(gameView, gameObjectManager.getPlayerHeli());
        this.gamePlayManager = new GamePlayManager(gameView, gameObjectManager);
    }

    /**
     * Starts the main game loop.
     */
    public void startGame() throws NoMoreLevelsAvailableException {
        while (true) { // The game loop
            gamePlayManager.updateGamePlay();
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();
        }
    }
}
