package de.thdeg.stud.choppercommand.game.util;

/**
 * Exception that occurs when no more levels can be generated.
 */
public class NoMoreLevelsAvailableException extends Exception {

    /**
     * Generates a NoMoreLevelsAvailableException.
     * @param s Error message to show.
     */
    public NoMoreLevelsAvailableException(String s) {
        super(s);
    }
}
