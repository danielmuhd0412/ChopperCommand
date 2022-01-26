package de.thdeg.stud.choppercommand.graphics.base;

import de.thdeg.stud.choppercommand.gameview.GameView;

import java.util.ArrayList;

/**
 * Game objects that can actively collide with other objects, e.g. a bullet.
 */
public abstract class CollidingGameObject extends CollidableGameObject {
    protected final ArrayList<CollidableGameObject> objectsToCollideWith;

    protected CollidingGameObject(GameView gameView, ArrayList<CollidableGameObject> objectsToCollideWith) {
        super(gameView);
        this.objectsToCollideWith = objectsToCollideWith;
    }

    @Override
    public void update() {
        super.update();
        checkCollisions();
    }

    /**
     * Used to check possible collisions that are actively caused by this game object.
     * Both parties are notified about the collision.
     */
    private void checkCollisions() {
        for (CollidableGameObject collidableGameObject : objectsToCollideWith) {
            if (collidesWith(collidableGameObject)) {
                reactToCollision(collidableGameObject);
                collidableGameObject.reactToCollision(this);
            }
        }
    }

    /**
     * Determines if this game object collided with another game object.
     *
     * @param other The other game object.
     * @return <code>true</code> if a collision occurred.
     */
    protected final boolean collidesWith(CollidableGameObject other) {
        return this.hitBox.intersects(other.hitBox);
    }

}