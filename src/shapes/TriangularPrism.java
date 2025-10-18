package shapes;

/**
 * Represents a three-dimensional triangular prism shape.
 * A triangular prism has two parallel triangular bases connected by rectangular sides.
 * Inherits from Prism class which provides common prism functionality.
 * This implementation assumes the base is an equilateral triangle.
 */
public class TriangularPrism extends Prism {
    
    /**
     * Constructs a new TriangularPrism with the specified height and side length.
     *
     * @param height the height of the prism (distance between triangular bases)
     * @param side the length of one side of the equilateral triangle base
     */
    public TriangularPrism(double height, double side) {
        super(height, side);
    }
    
    /**
     * Calculates and returns the base area of the triangular prism.
     * Formula for area of an equilateral triangle: (√3 / 4) × side²
     *
     * @return the area of one triangular base of the prism
     */
    @Override
    public double getBaseArea() {
        return (Math.sqrt(3) / 4) * Math.pow(side, 2);
    }

    /**
     * Calculates and returns the volume of the triangular prism.
     * Formula: base area × height
     *
     * @return the volume of the triangular prism
     */
    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}
