package de.thdeg.stud.choppercommand.game.managers;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.GameObject;
import de.thdeg.stud.choppercommand.graphics.movable.*;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.Bullet;
import de.thdeg.stud.choppercommand.graphics.stationary.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Manages game objects.
 */
class GameObjectManager {

    private final PlayerHeli playerHeli;
    private final LinkedList<Truck> trucks;
    private final LinkedList<EnemyHeli> enemyHelis;
    private final LinkedList<Bullet> bullets;
    private final Background background;
    private final Overlay overlay;
    private final Score score;
    private final Lives lives;
    private final LinkedList<GameObject> gameObjects;

    /**
     * Constructor for gameObjectManager.
     *
     * @param gameView Window in which the game object is drawn.
     */
    GameObjectManager(GameView gameView) {
        this.playerHeli = new PlayerHeli(gameView, new ArrayList<>());
        this.trucks = new LinkedList<>();
        this.enemyHelis = new LinkedList<>();
        this.bullets = new LinkedList<>();
        this.background = new Background(gameView);
        this.overlay = new Overlay(gameView);
        this.score = new Score(gameView);
        this.lives = new Lives(gameView);
        this.gameObjects = new LinkedList<>();
    }

    void updateGameObjects() {
        // background objects added earlier, foreground ones later
        gameObjects.clear();
        gameObjects.add(background);
        gameObjects.add(score);
        gameObjects.add(lives);
        gameObjects.addAll(enemyHelis);
        gameObjects.addAll(trucks);
        gameObjects.addAll(bullets);
        gameObjects.add(playerHeli);
        gameObjects.add(overlay);
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            gameObject.addToCanvas();
        }
//        System.out.println("All: " + gameObjects.size() + "  Bullets: " + bullets.size());
    }

    void moveWorld(double adaptX, double adaptY) {

        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Truck.class || gameObject.getClass() == EnemyHeli.class) {
                gameObject.adaptPosition(adaptX, adaptY);
            }
        }
    }

    /**
     * @return The current player helicopter object.
     */
    public PlayerHeli getPlayerHeli() {
        return playerHeli;
    }

    /**
     * @return A linked list of trucks.
     */
    public LinkedList<Truck> getTrucks() {
        return trucks;
    }

    /**
     * @return A linked list of enemy helicopters.
     */
    public LinkedList<EnemyHeli> getEnemyHelis() {
        return enemyHelis;
    }

    /**
     * @return A linked list of bullets.
     */
    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * @return The current score (background) object.
     */
    public Score getScore() {
        return score;
    }

    /**
     * @return The current lives (background) object.
     */
    public Lives getLives() {
        return lives;
    }

    /**
     *
     * @return The current overlay object.
     */
    public Overlay getOverlay() {
        return overlay;
    }


}
