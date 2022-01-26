package de.thdeg.stud.choppercommand.graphics.base;

import de.thdeg.stud.choppercommand.gameview.GameView;

import java.awt.*;
import java.util.Objects;

/**
 * Game objects that are able to collide with another object.
 */
public abstract class CollidableGameObject extends GameObject implements Cloneable {
    protected Rectangle hitBox;

    protected CollidableGameObject(GameView gameView) {
        super(gameView);
        this.hitBox = new Rectangle(-100_000, -100_000, 0, 0);
    }

    @Override
    public void update() {
        super.update();
        updateHitBoxPosition();
    }

    /**
     * Used to update the hitbox position of the game object.
     */
    protected abstract void updateHitBoxPosition();

    /**
     * The game object reacts to a collision with another game object.
     *
     * @param otherObject The other game object that is involved in the collision.
     */
    public abstract void reactToCollision(CollidableGameObject otherObject);

    @Override
    public void adaptPosition(double adaptX, double adaptY) {
        super.adaptPosition(adaptX, adaptY);
        updateHitBoxPosition();
    }

    /**
     * Clones the current collidable game object.
     *
     * @return A clone of the current collidable game object.
     */
    @Override
    public CollidableGameObject clone() {
        return (CollidableGameObject) super.clone();
    }

    /**
     * Checks if the given collidable game object is identical to the current collidable game object.
     *
     * @param o The game object to be checked.
     * @return True if it is the same object, or if the checked values in the super method and hitbox are identical. Otherwise returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CollidableGameObject other = (CollidableGameObject) o;
        return Objects.equals(hitBox, other.hitBox);
    }

    /**
     * @return A hash code based on the hash code of the superclass and hitbox of the collidable game object.
     * @see GameObject#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hitBox);
    }

}

