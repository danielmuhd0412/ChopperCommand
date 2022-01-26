package de.thdeg.stud.choppercommand.game.util;

/**
 * Handles level information.
 */
public class Level {

    /**
     * The name of the level.
     */
    public String name;
    /**
     * The maximum number of enemies.
     */
    public int numberOfEnemies;
    /**
     * The maximum number of trucks.
     */
    public int numberOfTrucks;
    /**
     * The maximum number of shots the player can shoot per second.
     */
    public int shotsPerSecond;

    private int level;
    /**
     * Generates the first level of the game.
     */
    public Level(String name, int levelNumber, int numberOfEnemies, int numberOfTrucks, int shotsPerSecond) {
        this.name = name;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTrucks = numberOfTrucks;
        this.shotsPerSecond = shotsPerSecond;
        this.level = levelNumber;
    }

}
