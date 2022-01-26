package de.thdeg.stud.choppercommand.graphics.movable.bullets;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Bullets shot by the enemy helicopters and the player helicopter.
 */
public abstract class Bullet extends CollidingGameObject implements MovingGameObject {

    protected static final String BULLET = "L";
    protected boolean flyFromLeftToRight;
    protected boolean flyFromUpToDown;

    /**
     * Generates an instance of a bullet.
     * @param gameView Window in which the bullet is drawn.
     * @param objectsToCollideWith Objects that react to collision with the bullet.
     */
    public Bullet(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView, objectsToCollideWith);
    }

    /**
     * Adds the bullet to the game window.
     */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(BULLET, position.x, position.y, size, rotation);
//        gameView.addRectangleToCanvas(hitBox.x, hitBox.y, hitBox.width, hitBox.height, 2, false, Color.red);

    }

    @Override
    protected void updateHitBoxPosition() {
        hitBox.x = (int) position.x;
        hitBox.y = (int) position.y;
    }

    /**
     * Sets the position of the object at a certain point.
     *
     * @param position desired position of the object
     */
    public void setPosition(Position position) {
        this.position = position;
    }


}
