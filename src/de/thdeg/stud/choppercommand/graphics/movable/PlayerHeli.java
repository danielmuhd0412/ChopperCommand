package de.thdeg.stud.choppercommand.graphics.movable;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.graphics.base.CollidingGameObject;
import de.thdeg.stud.choppercommand.graphics.base.MovingGameObject;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.EnemyBullet;

import java.awt.*;
import java.util.ArrayList;

/**
 * The helicopter controlled by the player.
 */
public class PlayerHeli extends CollidingGameObject implements MovingGameObject {

    private boolean flyFromLeftToRight;
    private final int shotsPerSecond;

    private enum Status {STANDARD1, STANDARD2, STANDARD3, EXPLODING1, EXPLODING2, EXPLODING3, EXPLODING4}

    private Status status;
    private int animationDuration;


    /**
     * Generates the player helicopter.
     *
     * @param gameView             Window in which the player helicopter is drawn.
     * @param objectsToCollideWith List of objects that react to collision with the player helicopter.
     */
    public PlayerHeli(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
        this.position = new Position(10, 150);
        this.size = 1;
        this.width = 16;
        this.height = 8;
        this.rotation = 0;
        this.speedInPixel = 2;
        this.hitBox.width = 57;
        this.hitBox.height = 37;
        this.shotsPerSecond = 1000 / 5;// gamePlayManager.getLevel().shotsPerSecond;
        this.status = Status.STANDARD1;
        this.animationDuration = 50;
        this.flyFromLeftToRight = true;
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) position.x;
        hitBox.y = (int) position.y + 10;
    }

    @Override
    public void updatePosition() {

    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() == EnemyHeli.class || otherObject.getClass() == EnemyBullet.class || otherObject.getClass() == Truck.class) {
            if (gameView.timerExpired("collisionCooldown", "collisionCooldown")) {
                gameView.setTimer("collisionCooldown", "collisionCooldown", 2000);
                status = Status.EXPLODING1;
                animationDuration = 50;
                flyFromLeftToRight = true;
            }
        }
    }


    /**
     * Adds the player helicopter to the game window.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(getImage(), position.x, position.y, size, rotation);
//        gameView.addRectangleToCanvas(hitBox.x, hitBox.y, hitBox.width, hitBox.height, 2, false, Color.red);

    }


    private String getImage() {
        switch (status) {
            case STANDARD1:
                if (flyFromLeftToRight) {
                    return "planeGreen1.png";
                } else return "planeGreen1flip.png";
            case STANDARD2:
                if (flyFromLeftToRight) {
                    return "planeGreen2.png";
                } else return "planeGreen2flip.png";
            case STANDARD3:
                if (flyFromLeftToRight) {
                    return "planeGreen3.png";
                } else return "planeGreen3flip.png";
            case EXPLODING1:
                return "plane_explosion1.png";
            case EXPLODING2:
                return "plane_explosion2.png";
            case EXPLODING3:
                return "plane_explosion3.png";
            case EXPLODING4:
                return "plane_explosion4.png";
        }
        return "planeGreen1.png";
    }


    /**
     * Animates and destroys the player helicopter.
     */
    @Override
    public void updateStatus() {

        if (gameView.timerExpired("animatePlane", "playerHeli")) {
            gameView.setTimer("animatePlane", "playerHeli", animationDuration);
            animatePlane();
        }

        if (status == Status.EXPLODING4) {
            gamePlayManager.loseLife();
            destroyPlayerHeli();
            respawnPlayerHeli();
        }
    }

    private void animatePlane() {
        switch (status) {
            case STANDARD1:
                status = Status.STANDARD2;
                break;
            case STANDARD2:
                status = Status.STANDARD3;
                break;
            case STANDARD3:
                status = Status.STANDARD1;
                break;
            case EXPLODING1:
                status = Status.EXPLODING2;
                break;
            case EXPLODING2:
                status = Status.EXPLODING3;
                break;
            case EXPLODING3:
                status = Status.EXPLODING4;
                break;
        }
    }

    private void destroyPlayerHeli() {
        gameView.playSound("hit1.wav", false);
    }

    private void respawnPlayerHeli() {
        status = Status.STANDARD1;
        position.x = 10;
        position.y = 150;
    }


    /**
     * Moves the helicopter left.
     */
    public void left() {
        flyFromLeftToRight = false;
        if (position.x > 100) {
            position.left(speedInPixel);
        } else {
            gamePlayManager.playerHeliMovesLeft(speedInPixel * 0.8);
        }
    }

    /**
     * Moves the helicopter right.
     */
    public void right() {
        flyFromLeftToRight = true;
        if (position.x < GameView.WIDTH - width - 200) {
            position.right(speedInPixel);
        } else {
            gamePlayManager.playerHeliMovesRight(speedInPixel * 0.8);
        }
    }

    /**
     * Moves the helicopter up.
     */
    public void up() {
        position.up(speedInPixel);

    }

    /**
     * Moves the helicopter down.
     */
    public void down() {
        position.down(speedInPixel);

    }

    /**
     * The helicopter shoots.
     */
    public void shoot() {
        if (gameView.timerExpired("shootPlayerBullet", "playerBullet")) {
            gameView.setTimer("shootPlayerBullet", "playerBullet", shotsPerSecond);
            gamePlayManager.shootPlayerBullet(position);
            gameView.playSound("fire2.wav", false);
        }

    }

    /**
     * @return Direction the player helicopter is facing.
     */
    public boolean isFlyFromLeftToRight() {
        return flyFromLeftToRight;
    }

    /**
     * @return An array list of valid collidable game objects for the player helicopter.
     */
    public ArrayList<CollidableGameObject> getObjectsToCollideWith() {
        return objectsToCollideWith;
    }

    /**
     * Resets the position of the player helicopter to default.
     */
    public void resetPosition() {
        position.x = 10;
        position.y = 150;
    }
}
