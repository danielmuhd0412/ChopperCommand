package de.thdeg.stud.choppercommand.game.managers;

import de.thdeg.stud.choppercommand.game.util.NoMoreLevelsAvailableException;
import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.game.util.Level;
import de.thdeg.stud.choppercommand.game.util.Player;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.*;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.Bullet;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.EnemyBullet;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.PlayerBullet;
import de.thdeg.stud.choppercommand.screens.EndScreen;
import de.thdeg.stud.choppercommand.screens.StartScreen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Manages gameplay logic.
 */
public class GamePlayManager {

    private final GameView gameView;
    private final GameObjectManager gameObjectManager;
    private LevelManager levelManager;
    private final Random random;
    private double movementMultiplier;
    private int maxNumberOfTrucks;
    private int maxNumberOfEnemyHelis;
    private final Player player;
    private Level currentLevel;
    private final int scoreHeliDestroyed;
    private final int scoreTruckDestroyed;
    private boolean levelOver;
    private boolean gameEnded;


    /**
     * Constructor for GamePlayManager.
     *
     * @param gameView          Window in which the game object is drawn.
     * @param gameObjectManager Class that manages game objects.
     */
    public GamePlayManager(GameView gameView, GameObjectManager gameObjectManager) {
        this.gameView = gameView;
        this.gameObjectManager = gameObjectManager;
        this.levelManager = new LevelManager(false);
        this.random = new Random();
        this.player = new Player();
        gameObjectManager.getPlayerHeli().setGamePlayManager(this);
        gameObjectManager.getScore().setGamePlayManager(this);
        gameObjectManager.getLives().setGamePlayManager(this);
        this.movementMultiplier = 1.0;
        this.scoreHeliDestroyed = 500;
        this.scoreTruckDestroyed = 100;
        this.levelOver = false;
        this.gameEnded = false;
        initializeGame();
    }

    private void initializeGame() {
        gameEnded = false;
        StartScreen startScreen = new StartScreen(gameView);
        startScreen.showStartScreen();
        levelManager = new LevelManager(startScreen.isDifficultySetToEasy());
        gameObjectManager.getScore().setCurrentScore(0);
        gameObjectManager.getLives().setCurrentLives(Player.MAX_LIVES);
        player.currentLives = Player.MAX_LIVES;
        player.currentScore = 0;
        gameObjectManager.getTrucks().clear();
        gameObjectManager.getEnemyHelis().clear();
        gameObjectManager.getBullets().clear();
    }

    private void initializeLevel() throws NoMoreLevelsAvailableException {
        this.currentLevel = levelManager.getNextLevel();
        gameObjectManager.getOverlay().showMessage(currentLevel.name);
        this.maxNumberOfEnemyHelis = currentLevel.numberOfEnemies;
        this.maxNumberOfTrucks = currentLevel.numberOfTrucks;
        gameObjectManager.getPlayerHeli().resetPosition();
        spawnEnemyHelis();
        spawnTrucks();
    }

    private void nextGame() {
        gameObjectManager.getOverlay().showMessage("Too bad! Game over!");
        gameView.stopAllSounds();
        EndScreen endScreen = new EndScreen(gameView);
        endScreen.setGamePlayManager(this);
        endScreen.showEndScreen();

        initializeGame();
    }


    private void nextLevel() throws NoMoreLevelsAvailableException {
        if (gameView.timerExpired("levelOver", "levelOver")) {
            levelOver = false;
            initializeLevel();
        }
    }

    void updateGamePlay() throws NoMoreLevelsAvailableException {
        if (gameEnded) {
            nextGame();
        }
        if (levelOver) {
            nextLevel();
        }
        if (gameObjectManager.getEnemyHelis().size() == 0 || gameObjectManager.getTrucks().size() == 0) {
            levelOver = true;
            gameObjectManager.getEnemyHelis().clear();
            gameObjectManager.getTrucks().clear();
            if (gameView.timerExpired("levelOver", "levelOver")) {
                gameView.setTimer("levelOver", "levelOver", 1000);
            }
        }
        addPlayerCollidableObjects();
    }

    private void addPlayerCollidableObjects() {
        ArrayList<CollidableGameObject> collidableGameObjects = gameObjectManager.getPlayerHeli().getObjectsToCollideWith();
        collidableGameObjects.addAll(gameObjectManager.getEnemyHelis());
        collidableGameObjects.addAll(gameObjectManager.getBullets());
    }


    private void spawnTrucks() {
        LinkedList<Truck> trucks = gameObjectManager.getTrucks();
        int distanceX = 100;
        for (int i = 0; i < maxNumberOfTrucks; i++) {
            Truck truck = new Truck(gameView);
            truck.setGamePlayManager(this);
            truck.getPosition().x = distanceX;
            distanceX += 100;
            trucks.add(truck);
        }
    }

    private void spawnEnemyHelis() {
        LinkedList<EnemyHeli> enemyHelis = gameObjectManager.getEnemyHelis();
        for (int i = 0; i < maxNumberOfEnemyHelis; i++) {
            EnemyHeli enemyHeli = new EnemyHeli(gameView);
            enemyHeli.setGamePlayManager(this);
            enemyHelis.add(enemyHeli);
        }
    }


    /**
     * The player helicopter shoots a bullet. A new bullet is created.
     *
     * @param startPosition The starting position of the bullet. (Identical to the current position of the player helicopter.)
     */
    public void shootPlayerBullet(Position startPosition) {
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>(gameObjectManager.getEnemyHelis());
        boolean flyFromLeftToRight = gameObjectManager.getPlayerHeli().isFlyFromLeftToRight();
        PlayerBullet playerBullet = new PlayerBullet(gameView, collidableGameObjects, flyFromLeftToRight);
        playerBullet.getPosition().x = startPosition.x + 25;
        playerBullet.getPosition().y = startPosition.y + 25;
        playerBullet.setGamePlayManager(this);
        gameObjectManager.getBullets().add(playerBullet);
    }

    /**
     * The enemy helicopter shoots two bullets (up and down). Two new bullets are created.
     */
    public void shootEnemyBullet(Position startPosition) {
        ArrayList<CollidableGameObject> collidableGameObjects = new ArrayList<>();
        collidableGameObjects.add(gameObjectManager.getPlayerHeli());
        collidableGameObjects.addAll(gameObjectManager.getTrucks());

        createEnemyBullet(collidableGameObjects, startPosition, false);
        createEnemyBullet(collidableGameObjects, startPosition, true);

    }

    private void createEnemyBullet(ArrayList<CollidableGameObject> collidableGameObjects, Position startPosition,
                                   boolean direction) {
        EnemyBullet enemyBullet = new EnemyBullet(gameView, collidableGameObjects, direction);
        enemyBullet.getPosition().x = startPosition.x + 35;
        enemyBullet.getPosition().y = startPosition.y + 5;
        enemyBullet.setGamePlayManager(this);
        gameObjectManager.getBullets().add(enemyBullet);

    }

    /**
     * Offsets the world to the right when the player helicopter moves left.
     *
     * @param speedInPixel The speed (in pixels) of the player helicopter.
     */
    public void playerHeliMovesLeft(double speedInPixel) {
        gameObjectManager.moveWorld(speedInPixel * movementMultiplier, 0);
    }

    /**
     * Offsets the world to the left when the player helicopter moves right.
     *
     * @param speedInPixel The speed (in pixels) of the player helicopter.
     */
    public void playerHeliMovesRight(double speedInPixel) {
        gameObjectManager.moveWorld(-speedInPixel * movementMultiplier, 0);
    }

    /**
     * Destroys a bullet once it leaves the active screen.
     *
     * @param bullet Bullet pending destruction.
     */
    public void destroyBullet(Bullet bullet) {
        gameObjectManager.getBullets().remove(bullet);
    }

    /**
     * Destroys a truck.
     *
     * @param truck Truck pending destruction.
     */
    public void destroyTruck(Truck truck) {
        gameObjectManager.getTrucks().remove(truck);
        if (player.currentScore > 0) {
            player.currentScore -= scoreTruckDestroyed;
            gameObjectManager.getScore().setCurrentScore(player.currentScore);
        }
    }

    /**
     * Destroys an enemy helicopter.
     *
     * @param enemyHeli Enemy helicopter pending destruction.
     */
    public void destroyEnemyHeli(EnemyHeli enemyHeli) {
        gameObjectManager.getEnemyHelis().remove(enemyHeli);
        player.currentScore += scoreHeliDestroyed;
        gameObjectManager.getScore().setCurrentScore(player.currentScore);
    }

    /**
     * Manages player lives.
     */
    public void loseLife() {
        int currentLives = player.currentLives;
        if (currentLives > 1) {
            player.currentLives -= 1;
        } else {
            gameEnded = true;
        }
        gameObjectManager.getLives().setCurrentLives(player.currentLives);
    }

    /**
     * @return The current player object.
     * @see Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return The current level object.
     * @see Level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
}