package de.thdeg.stud.choppercommand.graphics.movable;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.graphics.base.MovingGameObject;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.bullets.EnemyBullet;

import java.awt.*;
import java.util.Random;

/**
 * The trucks that the player helicopter must protect from being shot at by the enemy helicopters.
 */
public class Truck extends CollidableGameObject implements MovingGameObject, Cloneable {

    private enum Status {STANDARD, EXPLODING1, EXPLODING2, EXPLODING3, EXPLODING4}

    private boolean movingLeftToRight;
    private final Random random;
    private Status status;
    private String image;
    private int animationDuration;

    /**
     * Generates an instance of a truck.
     *
     * @param gameView Window in which the truck is drawn.
     */
    public Truck(GameView gameView) {
        super(gameView);
        this.random = new Random();
        this.position = new Position(0, GameView.HEIGHT * 0.8125);
        this.objectID = "Truck" + random.nextDouble() + position.y;
        this.size = 1;
        this.width = 78;
        this.height = 54;
        this.rotation = 0;
        this.speedInPixel = 1;
        this.movingLeftToRight = random.nextBoolean();
        this.hitBox.width = 62;
        this.hitBox.height = 41;
        this.status = Status.STANDARD;
        this.image = "tank.png";
        this.animationDuration = 100;
    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) position.x + 10;
        hitBox.y = (int) position.y + 11;
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {

        if (otherObject.getClass() == EnemyBullet.class || otherObject.getClass() == PlayerHeli.class) {
            status = Status.EXPLODING1;
        }

        if (otherObject.getClass() == Truck.class) {
            movingLeftToRight = !movingLeftToRight;
        }
    }


    /**
     * Adds the truck to the game window.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(getImage(), position.x, position.y, size, rotation);
//        gameView.addRectangleToCanvas(hitBox.x, hitBox.y, hitBox.width, hitBox.height, 2, false, Color.red);
    }

    private String getImage() {
        switch (status) {
            case STANDARD:
                if (movingLeftToRight) {
                    image = "tank.png";
                } else image = "tankflip.png";
                break;
            case EXPLODING1:
                image = "tank_explosion1.png";
                break;
            case EXPLODING2:
                image = "tank_explosion2.png";
                break;
            case EXPLODING3:
                image = "tank_explosion3.png";
                break;
            case EXPLODING4:
                image = "tank_explosion4.png";
                break;
        }
        return image;
    }

    /**
     * Moves the truck right or left, depending on it's current position.
     * If the truck is on right edge of screen, it will start to move left, and vice-versa.
     */
    @Override
    public void updatePosition() {
        if (movingLeftToRight) {
            moveRight();
        } else {
            moveLeft();
        }
    }

    private void moveRight() {
        if (position.x >= GameView.WIDTH - 6.0 * width) {
            movingLeftToRight = false;
        }
        position.right(speedInPixel);

    }

    private void moveLeft() {
        if (position.x <= width) {
            movingLeftToRight = true;
        }
        position.left(speedInPixel);
    }

    /**
     * Animates and destroys the truck.
     */
    @Override
    public void updateStatus() {
        if (gameView.timerExpired("animateTruck", objectID)) {
            gameView.setTimer("animateTruck", objectID, animationDuration);
            animateTruck();
        }

        if (status == Status.EXPLODING4) {
            destroyTruck();
        }
    }

    private void animateTruck() {
            switch (status) {
                case EXPLODING1:
                    status = Status.EXPLODING2;
//                    System.out.println(status);
                    break;
                case EXPLODING2:
                    status = Status.EXPLODING3;
//                    System.out.println(status);
                    break;
                case EXPLODING3:
                    status = Status.EXPLODING4;
//                    System.out.println(status);
                    break;
        }
    }

    private void destroyTruck() {
        gamePlayManager.destroyTruck(this);
        gameView.playSound("hit1.wav", false);
    }

}
