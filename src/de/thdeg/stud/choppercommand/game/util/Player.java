package de.thdeg.stud.choppercommand.game.util;

/**
 * Handles player-specific information. (Lives, score)
 */
public class Player {

    /**
     * The current number of lives the player has.
     */
    public int currentLives;
    /**
     * The current score of the player.
     */
    public int currentScore;
    /**
     * Specifies if the game is on easy difficulty.
     */
    public boolean difficultyIsSetToEasy;
    /**
     * The current level the player is on.
     */
    public int currentLevel;
    /**
     * The maximum number of lives.
     */
    public static final int MAX_LIVES = 3;

    // name, highscore?

    /**
     * Generates a new player.
     */
    public Player() {
        this.currentLevel = 1;
        this.currentLives = MAX_LIVES;
        this.currentScore = 0;
        this.difficultyIsSetToEasy = false;
    }
}
