package de.thdeg.stud.choppercommand.graphics.movable.bullets;

import de.thdeg.stud.choppercommand.gameview.GameView;
import de.thdeg.stud.choppercommand.graphics.base.CollidableGameObject;
import de.thdeg.stud.choppercommand.graphics.base.Position;
import de.thdeg.stud.choppercommand.graphics.movable.Truck;

import java.util.ArrayList;

/**
 * Bullets shot by enemy helicopters.
 */
public class EnemyBullet extends Bullet {

    /**
     * Generates an instance of a bullet.
     * @param gameView Window in which the bullet is drawn.
     * @param objectsToCollideWith Objects that react to collision with the bullet.
     * @param flyFromUpToDown Direction of the bullet. True = Bullet moves downwards, otherwise bullet moves right.
     */
    public EnemyBullet(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith, boolean flyFromUpToDown) {
        super(gameView, objectsToCollideWith);
        this.position = new Position();
        this.size = 8;
        this.width = 4;
        this.height = 4;
        this.speedInPixel = 3;
        this.flyFromUpToDown = flyFromUpToDown;
        this.hitBox.width = width * 2;
        this.hitBox.height = height * 2;
    }

    @Override
    protected void updateStatus() {
        if (position.y > GameView.HEIGHT || position.y < 0) {
            gamePlayManager.destroyBullet(this);
        }
        if (position.x >= GameView.WIDTH || position.x < 0) {
            gamePlayManager.destroyBullet(this);
        }
    }

    /**
     * Updates the position of the bullet.
     */
    @Override
    public void updatePosition() {
        if (flyFromUpToDown) {
            position.down(speedInPixel);
        } else {
            position.right(speedInPixel);
        }
    }

    @Override
    public void reactToCollision(CollidableGameObject otherObject) {
        if (otherObject.getClass() == Truck.class || otherObject.getClass() == PlayerBullet.class) {
            gamePlayManager.destroyBullet(this);
        }
    }
}
