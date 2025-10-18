package shapes;
import appDomain.Shape;

/**
 * Represents a three-dimensional cylinder shape.
 * A cylinder has two parallel circular bases connected by a curved surface.
 * Inherits height from the Shape class and adds radius as a specific attribute.
 */
public class Cylinder extends Shape{
    /**
     * The radius of the circular base of the cylinder.
     */
    private double radius;
    
    /**
     * Constructs a new Cylinder with the specified height and radius.
     *
     * @param height the height of the cylinder (distance between bases)
     * @param radius the radius of the circular base of the cylinder
     */
    public Cylinder(double height,double radius) {
        super(height);
        this.radius = radius;
    }
    
    /**
     * Calculates and returns the base area of the cylinder.
     * The base area is the area of one circular base.
     * Formula: π × radius²
     *
     * @return the area of one circular base of the cylinder
     */
    @Override
    public double getBaseArea() {
        return Math.PI * Math.pow(radius, 2);
    }
    
    /**
     * Calculates and returns the volume of the cylinder.
     * Formula: π × radius² × height
     *
     * @return the volume of the cylinder
     */
    @Override
    public double getVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }

}
