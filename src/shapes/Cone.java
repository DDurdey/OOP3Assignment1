package shapes;
import appDomain.Shape;

/**
 * Represents a three-dimensional cone shape.
 * A cone has a circular base and tapers to a point (apex) at the top.
 * Inherits height from the Shape class and adds radius as a specific attribute.
 */
public class Cone extends Shape {
    
    /**
     * The radius of the circular base of the cone.
     * FieldMayBeFinal warning suppressed as radius is set in constructor and not modified.
     */
    @SuppressWarnings("FieldMayBeFinal")
    private double radius;
    
    /**
     * Constructs a new Cone with the specified height and radius.
     *
     * @param height the vertical height of the cone from base to apex
     * @param radius the radius of the circular base of the cone
     */
    public Cone(double height, double radius) {
        super(height);
        this.radius = radius;
    }
    
    /**
     * Calculates and returns the base area of the cone.
     * The base area is the area of the circular base.
     * Formula: π × radius²
     *
     * @return the area of the circular base of the cone
     */
    @Override
    public double getBaseArea() {
        return Math.PI * Math.pow(radius, 2);
    }
    
    /**
     * Calculates and returns the volume of the cone.
     * Formula: (1/3) × π × radius² × height
     *
     * @return the volume of the cone
     */
    @Override
    public double getVolume() {
        return (1.0/3.0) * Math.PI * Math.pow(radius, 2) * height;
    }
}
