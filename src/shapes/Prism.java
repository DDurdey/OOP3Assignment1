package shapes;
import appDomain.Shape;

/**
 * Abstract base class representing a prism shape.
 * A prism is a polyhedron with two parallel congruent bases connected by rectangular lateral faces.
 * This class provides common functionality for all prism types and defines the structure
 * that concrete prism subclasses must implement.
 */
public abstract class Prism extends Shape {
    
    /**
     * The side length of the base polygon for the prism.
     * For regular polygonal prisms, this represents the length of one side
     * of the regular polygon that forms the base.
     */
    protected double side;
    
    /**
     * Constructs a new Prism with the specified height and side length.
     *
     * @param height the height of the prism (distance between the two parallel bases)
     * @param side the length of one side of the base polygon
     */
    public Prism(double height, double side) {
        super(height);
        this.side = side;
    }
}
