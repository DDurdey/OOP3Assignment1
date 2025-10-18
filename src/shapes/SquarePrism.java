package shapes;

/**
 * Represents a three-dimensional square prism shape.
 * A square prism has two parallel square bases connected by rectangular sides.
 * Inherits from Prism class which provides common prism functionality.
 * Note: A square prism is also known as a rectangular prism with square bases.
 */
public class SquarePrism extends Prism {
    
    /**
     * Constructs a new SquarePrism with the specified height and side length.
     *
     * @param height the height of the prism (distance between square bases)
     * @param side the length of one side of the square base
     */
    public SquarePrism(double height, double side) {
        super(height, side);
    }
    
    /**
     * Calculates and returns the base area of the square prism.
     * Formula for area of a square: side²
     *
     * @return the area of one square base of the prism
     */
    @Override
    public double getBaseArea() {
        return Math.pow(side, 2);
    }

    /**
     * Calculates and returns the volume of the square prism.
     * Formula: base area × height
     *
     * @return the volume of the square prism
     */
    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}
