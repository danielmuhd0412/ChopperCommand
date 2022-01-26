package de.thdeg.stud.choppercommand.graphics.base;

import java.util.Objects;

/**
 * Used to control and track the movement of individual objects on screen.
 */
public class Position implements Cloneable, Comparable<Position> {

    /**
     * x-coordinate of the object.
     */
    public double x;

    /**
     * y-coordinate of the object.
     */
    public double y;

    /**
     * Generates a coordinate using given parameters.
     *
     * @param x initial x-coordinate
     * @param y initial y-coordinate
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Generates default coordinates of (0,0).
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Calculates the distance between the current and another position.
     *
     * @param other Position of which its distance from the current position is calculated.
     * @return The distance between the current position and the other position.
     */
    public double distance(Position other) {
        return Math.sqrt(
                Math.pow((this.x - other.x), 2) +
                        Math.pow((this.y - other.y), 2));
    }

    /**
     * Moves the object to the left by 1 pixel.
     */
    public void left() {
        this.x -= 1;
    }

    /**
     * Moves the object to the left by the given number of pixels.
     *
     * @param pixel the given number of pixels
     */
    public void left(double pixel) {
        this.x -= pixel;
    }

    /**
     * Moves the object to the right by 1 pixel.
     */
    public void right() {
        this.x += 1;
    }

    /**
     * Moves the object to the right by the given number of pixels.
     *
     * @param pixel the given number of pixels
     */
    public void right(double pixel) {
        this.x += pixel;
    }

    /**
     * Moves the object up by 1 pixel.
     */
    public void up() {
        this.y -= 1;
    }

    /**
     * Moves the object up by the given number of pixels.
     *
     * @param pixel the given number of pixels
     */
    public void up(double pixel) {
        this.y -= pixel;
    }

    /**
     * Moves the object down by 1 pixel.
     */
    public void down() {
        this.y += 1;
    }

    /**
     * Moves the object down by the given number of pixels.
     *
     * @param pixel the given number of pixels
     */
    public void down(double pixel) {
        this.y += pixel;
    }

    /**
     * Outputs the current position of an object.
     *
     * @return Position (x, y)
     */
    @Override
    public String toString() {
        return "Position ("
                + (int) Math.round(x)
                + ", "
                + (int) Math.round(y)
                + ")";
    }

    /**
     * Clones the current position.
     *
     * @return A clone of the current position.
     */
    @Override
    public Position clone() {
        Position other = null;
        try {
            other = (Position) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return other;
    }

    /**
     * Checks if the given position is identical to the current position.
     *
     * @param o The position to be checked.
     * @return True if it is the same object, or if the x and y values are identical. Otherwise returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position other = (Position) o;
        return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
    }

    /**
     * @return A hash code based on the x and y values.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compares the current position with another position p. Comparison based on distance to origin.
     *
     * @param p The position to be compared with.
     * @return 1 if distance is greater, -1 if smaller, 0 if identical
     */
    @Override
    public int compareTo(Position p) {
        double thisDistToZero = distance(new Position(0, 0));
        double pDistToZero = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2)); // alt: distance(new Position())
        return (int) Math.signum(thisDistToZero - pDistToZero);
    }
}
