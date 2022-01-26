package de.thdeg.stud.choppercommand.game.managers;

import de.thdeg.stud.choppercommand.game.util.Level;
import de.thdeg.stud.choppercommand.game.util.NoMoreLevelsAvailableException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages levels.
 */
class LevelManager {
    private Level levelOne;
    private Level levelTwo;
    private Level levelThree;
    private Level levelFour;
    private Level levelFive;

    private ArrayList<Level> levels;
    private Iterator<Level> levelIterator;

    LevelManager(boolean difficultyIsSetToEasy) {
        this.levelOne = new Level("Level 1", 1, 2, 2, 3);
        this.levelTwo = new Level("Level 2", 2, 3, 3, 3);
        this.levelThree = new Level("Level 3", 3, 4, 4, 3);
        this.levelFour = new Level("Level 4", 4, 5, 5, 3);
        this.levelFive = new Level("Level 5", 5, 6, 6, 3);
        levels = new ArrayList<>(List.of(levelOne, levelTwo, levelThree, levelFour, levelFive));
        levelIterator = levels.iterator();
        if (difficultyIsSetToEasy) {
            for (Level level : levels) {
                level.shotsPerSecond = 5;
            }
        }
    }

    boolean hasNextLevel() {
        return levelIterator.hasNext();
    }

    Level getNextLevel() throws NoMoreLevelsAvailableException {
        if (hasNextLevel()) {
            return levelIterator.next();
        } else {
            levelIterator = levels.iterator();
//            throw new NoMoreLevelsAvailableException("No more levels!");
        }
        return levelIterator.next();
    }
}