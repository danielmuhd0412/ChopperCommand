package de.thdeg.stud.choppercommand.graphics.base;

import de.thdeg.stud.choppercommand.game.managers.GamePlayManager;
import de.thdeg.stud.choppercommand.gameview.GameView;

import java.util.Objects;

/**
 * Base class for all movable and displayable game objects.
 */
public abstract class GameObject implements Cloneable {

    // size, width, height corresponds to dimensions of pngs in resources
    /**
     * The size (in pixels) of the game object.
     */
    protected double size;

    /**
     * The angle of rotation (in degrees) of the game object.
     */
    protected double rotation;

    /**
     * The speed (in pixels) of the game object.
     */
    protected double speedInPixel;

    /**
     * The width (in pixels) of the game object.
     */
    protected int width;

    /**
     * The height (in pixels) of the game object.
     */
    protected int height;

    /**
     * The position (x, y) of the game object.
     */
    protected Position position;

    /**
     * Parameter to pass for the object to be shown on the canvas.
     */
    protected final GameView gameView;

    /**
     * Handles the gameplay.
     */
    protected GamePlayManager gamePlayManager;

    /**
     * A unique ID for use in timers.
     */
    protected String objectID;

    /**
     * Base constructor for all game objects.
     *
     * @param gameView Window in which the game object is drawn.
     */
    public GameObject(GameView gameView) {
        this.gameView = gameView;
        this.position = new Position();

    }


    /**
     * Base method to update an object's status.
     */
    protected abstract void updateStatus();

    /**
     * Base method to add an object to the canvas.
     */
    public abstract void addToCanvas();

    /**
     * Updates the position and current status of the object.
     */
    public void update() {
        if (this instanceof MovingGameObject) {
            ((MovingGameObject) this).updatePosition();
        }
        this.updateStatus();
    }

    /**
     * Offsets the coordinates of the object by the the given values.
     *
     * @param adaptX Offsets the x-value by the given amount.
     * @param adaptY Offsets the y-value by the given amount.
     */
    public void adaptPosition(double adaptX, double adaptY) {
        position.x += adaptX;
        position.y += adaptY;
    }

    /**
     * Outputs the current position of a game object.
     *
     * @return Name_Of_Object : Position (x, y)
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + position.toString();
    }

    /**
     * Obtains the position of the object from the {@link Position} class.
     *
     * @return current coordinates of the object
     * @see Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the gamePlayManager.
     *
     * @param gamePlayManager Class that manages gameplay.
     */
    public void setGamePlayManager(GamePlayManager gamePlayManager) {
        this.gamePlayManager = gamePlayManager;
    }

    /**
     * Obtains the ID of the object.
     *
     * @return the object's ID
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Clones the current game object.
     *
     * @return A clone of the current game object.
     */
    @Override
    public GameObject clone() {
        GameObject other = null;
        try {
            other = (GameObject) super.clone();
            other.position = position.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return other;
    }

    /**
     * Checks if the given game object is identical to the current game object.
     *
     * @param o The game object to be checked.
     * @return True if it is the same object, or if the speed in pixels, size, width, and position are identical. Otherwise returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject other = (GameObject) o;
        return Double.compare(other.speedInPixel, speedInPixel) == 0 &&
                Double.compare(other.size, size) == 0 &&
                other.width == width &&
                other.position.equals(this.position);
    }

    /**
     * @return A hash code based on position, speed in pixels, size, width, and height of the game object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(position, speedInPixel, size, width, height);
    }

}
