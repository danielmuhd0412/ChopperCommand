package de.thdeg.stud.choppercommand.graphics.movable;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.graphics.base.MovingGameObject;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.PlayerBullet;

import java.awt.*;
import java.util.Random;

/**
 * The enemy helicopters that target the trucks and the player-controlled helicopter using bullets.
 */
public class EnemyHeli extends CollidableGameObject implements MovingGameObject {

    private boolean flyFromLeftToRight;
    private Random random;
    private enum Status {STANDARD1, STANDARD2, STANDARD3, EXPLODING1, EXPLODING2, EXPLODING3, EXPLODING4}
    private Status status;
    private int animationDuration;
    private boolean exploding;

    /**
     * Generates an instance of an enemy helicopter.
     *
     * @param gameView Window in which the enemy helicopter is drawn.
     */
    public EnemyHeli(GameView gameView) {
        super(gameView);
        this.random = new Random();
        this.position = new Position(GameView.WIDTH * random.nextDouble(), GameView.HEIGHT * 0.5 * random.nextDouble());
        this.size = 1;
        this.width = 66;
        this.height = 55;
        this.rotation = 0;
        this.speedInPixel = 1;
        this.flyFromLeftToRight = true;
        this.objectID = "EnemyHeli" + position.x + position.y;
        this.hitBox.width = 57;
        this.hitBox.height = 37;
        this.status = Status.STANDARD1;
        this.animationDuration = 50;
        this.exploding = false;
        gameView.setTimer("shootEnemyBullet", objectID, random.nextInt(3000 - 1000) + 1000);

    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) position.x;
        hitBox.y = (int) position.y + 10;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() == PlayerBullet.class && !exploding) {
            status = Status.EXPLODING1;
            animationDuration = 100;
        }
    }

    /**
     * Adds the enemy helicopter to the game window.
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
                    return "planeRed1.png";
                } else return "planeRed1flip.png";
            case STANDARD2:
                if (flyFromLeftToRight) {
                    return "planeRed2.png";
                } else return "planeRed2flip.png";
            case STANDARD3:
                if (flyFromLeftToRight) {
                    return "planeRed3.png";
                } else return "planeRed3flip.png";
            case EXPLODING1:
                return "plane_explosion1.png";
            case EXPLODING2:
                return "plane_explosion2.png";
            case EXPLODING3:
                return "plane_explosion3.png";
            case EXPLODING4:
                return "plane_explosion4.png";
        }
        return  "planeRed1.png";
    }

    private void destroyEnemyHeli() {
        gamePlayManager.destroyEnemyHeli(this);
        gameView.playSound("hit1.wav", false);
    }

    /**
     * Moves the enemy helicopter right or left, depending on it's current position.
     * If the helicopter is on right edge of screen, it will start to left, and vice-versa.
     */
    @Override
    public void updatePosition() {
        if (flyFromLeftToRight) {
            moveRight();
        } else {
            moveLeft();
        }
    }

    /**
     * Animates and destroys the truck.
     */
    @Override
    public void updateStatus() {

        if (gameView.timerExpired("shootEnemyBullet", objectID)) {
            gameView.setTimer("shootEnemyBullet", objectID, random.nextInt(3000 - 1000) + 1000);
            gamePlayManager.shootEnemyBullet(new Position(position.x, position.y));
        }

        if (gameView.timerExpired("animatePlane", objectID)) {
            gameView.setTimer("animatePlane", objectID, animationDuration);
            animatePlane();
        }

        if (status == Status.EXPLODING4) {
            destroyEnemyHeli();
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
                    exploding = true;
                    break;
                case EXPLODING2:
                    status = Status.EXPLODING3;
                    break;
                case EXPLODING3:
                    status = Status.EXPLODING4;
                    break;
            }
        }

    private void moveRight() {
        if (position.x >= GameView.WIDTH - 6.0 * width) {
            flyFromLeftToRight = false;
        }
        position.right(speedInPixel);

    }

    private void moveLeft() {
        if (position.x <= width) {
            flyFromLeftToRight = true;
        }
        position.left(speedInPixel);
    }

}
