package de.thdeg.stud.choppercommand.screens;

import de.thdeg.stud.choppercommand.gameview.GameView;

/**
 * The starting screen shown at the beginning of the game.
 */
public class StartScreen {

    private String title;
    private String description;

    private GameView gameView;
    private boolean difficultySetToEasy;

    /**
     * Generates the start screen.
     * @param gameView Window in which the screen is drawn.
     */
    public StartScreen(GameView gameView) {
        this.gameView = gameView;
        this.title =
                "  #####  #     # ####### ######  ######  ####### ######      #####  ####### #     # #     #    #    #     # ######  \n"+
                        " #     # #     # #     # #     # #     # #       #     #    #     # #     # ##   ## ##   ##   # #   ##    # #     # \n"+
                        " #       #     # #     # #     # #     # #       #     #    #       #     # # # # # # # # #  #   #  # #   # #     # \n"+
                        " #       ####### #     # ######  ######  #####   ######     #       #     # #  #  # #  #  # #     # #  #  # #     # \n"+
                        " #       #     # #     # #       #       #       #   #      #       #     # #     # #     # ####### #   # # #     # \n"+
                        " #     # #     # #     # #       #       #       #    #     #     # #     # #     # #     # #     # #    ## #     # \n"+
                        "  #####  #     # ####### #       #       ####### #     #     #####  ####### #     # #     # #     # #     # ######  ";


        this.description = "        Defend the tanks; shoot the red planes!     ";
        this.difficultySetToEasy = false;

    }

    /**
     * Shows the start screen on the game window.
     */
    public void showStartScreen() {
        difficultySetToEasy = gameView.showSimpleStartScreen(title, description);
    }

    /**
     *
     * @return True if on easy difficulty, false if on normal.
     */
    public boolean isDifficultySetToEasy() {
        return difficultySetToEasy;

    }
}
