package shapes;

/**
 * Represents a three-dimensional octagonal prism shape.
 * An octagonal prism has two parallel regular octagon bases connected by rectangular sides.
 * Inherits from Prism class which provides common prism functionality.
 */
public class OctagonalPrism extends Prism {
    
    /**
     * Constructs a new OctagonalPrism with the specified height and side length.
     *
     * @param height the height of the prism (distance between octagonal bases)
     * @param side the length of one side of the regular octagon base
     */
    public OctagonalPrism(double height, double side) {
        super(height, side);
    }
    
    /**
     * Calculates and returns the base area of the octagonal prism.
     * Formula for area of a regular octagon: 2 × (1 + √2) × side²
     *
     * @return the area of one octagonal base of the prism
     */
    @Override
    public double getBaseArea() {
        return 2 * (1 + Math.sqrt(2)) * Math.pow(side, 2);
    }

    /**
     * Calculates and returns the volume of the octagonal prism.
     * Formula: base area × height
     *
     * @return the volume of the octagonal prism
     */
    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }

}
