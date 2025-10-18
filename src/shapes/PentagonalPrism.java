package shapes;

/**
 * Represents a three-dimensional pentagonal prism shape.
 * A pentagonal prism has two parallel regular pentagon bases connected by rectangular sides.
 * Inherits from Prism class which provides common prism functionality.
 */
public class PentagonalPrism extends Prism {
    
    /**
     * Constructs a new PentagonalPrism with the specified height and side length.
     *
     * @param height the height of the prism (distance between pentagonal bases)
     * @param side the length of one side of the regular pentagon base
     */
    public PentagonalPrism(double height, double side) {
        super(height, side);
    }
    
    /**
     * Calculates and returns the base area of the pentagonal prism.
     * Formula for area of a regular pentagon: (5 × side² × tan(54°)) ÷ 4
     * Note: Math.tan requires radians, so degrees are converted using Math.toRadians
     *
     * @return the area of one pentagonal base of the prism
     */
    @Override
    public double getBaseArea() {
        return (5 * Math.pow(side, 2) * Math.tan(Math.toRadians(54))) / 4;
    }

    /**
     * Calculates and returns the volume of the pentagonal prism.
     * Formula: base area × height
     *
     * @return the volume of the pentagonal prism
     */
    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }

}
