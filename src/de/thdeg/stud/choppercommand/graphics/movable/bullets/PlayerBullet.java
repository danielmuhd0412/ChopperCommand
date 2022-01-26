package de.thdeg.stud.choppercommand.graphics.movable.bullets;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.EnemyHeli;

import java.util.ArrayList;

/**
 * Bullets shot by the player helicopter.
 */
public class PlayerBullet extends Bullet {

    /**
     * Generates an instance of a bullet.
     * @param gameView Window in which the bullet is drawn.
     * @param objectsToCollideWith Objects that react to collision with the bullet.
     * @param flyFromLeftToRight Direction of the bullet. True = Bullet moves rightwards.
     */
    public PlayerBullet(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, boolean flyFromLeftToRight) {
        super(gameView, objectsToCollideWith);
        this.position = new Position();
        this.size = 8;
        this.width = 4;
        this.height = 4;
        this.speedInPixel = 10;
        this.flyFromLeftToRight = flyFromLeftToRight;
        this.hitBox.width = width * 2;
        this.hitBox.height = height * 2;
    }

    @Override
    protected void updateStatus() {
        if (position.x >= GameView.WIDTH || position.x < 0) {
            gamePlayManager.destroyBullet(this);
        }
    }

    /**
     * Moves the bullet.
     */
    @Override
    public void updatePosition() {
        if (!flyFromLeftToRight) {
            position.left(speedInPixel);
        } else {
            position.right(speedInPixel);
        }
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() == EnemyHeli.class || otherObject.getClass() == EnemyBullet.class) {
            gamePlayManager.destroyBullet(this);
        }
    }
}
