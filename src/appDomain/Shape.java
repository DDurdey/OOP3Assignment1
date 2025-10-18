package appDomain;

/**
 * Abstract base class representing a geometric shape with height.
 * Implements Comparable to enable natural ordering by height.
 * 
 * @Team Link
 * @version 1.0
 */
public abstract class Shape implements Comparable<Shape> {
    
    /**
     * The height dimension of the shape. Protected to allow access by subclasses.
     * Used for volume and area calculations in concrete implementations.
     */
    protected final double height; // Made final for immutability
    
    /**
     * Constructs a new Shape with the specified height.
     *
     * @param height the height of the shape, must be positive
     * @throws IllegalArgumentException if height is not positive
     */
    public Shape(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive: " + height);
        }
        this.height = height;
    }
    
    /**
     * Returns the height of this shape.
     *
     * @return the height value
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * Calculates and returns the base area of the shape.
     * Concrete subclasses must provide their own implementation.
     *
     * @return the base area of the shape
     */
    public abstract double getBaseArea();
    
    /**
     * Calculates and returns the volume of the shape.
     * Concrete subclasses must provide their own implementation.
     *
     * @return the volume of the shape
     */
    public abstract double getVolume();
    
    /**
     * Compares this shape to another shape based on height.
     * Implements natural ordering for shapes.
     *
     * @param other the shape to compare to, must not be null
     * @return a negative integer, zero, or positive integer if this shape
     *         is less than, equal to, or greater than the specified shape
     * @throws NullPointerException if the specified shape is null
     */
    @Override
    public int compareTo(Shape other) {
        // Null check for defensive programming
        if (other == null) {
            throw new NullPointerException("Cannot compare to null shape");
        }
        
        // Using Double.compare for proper handling of floating-point precision
        return Double.compare(this.height, other.height);
    }
    
    /**
     * Returns a string representation of this shape.
     * Format: "ClassName [height=x.xxx, base area=x.xxx, volume=x.xxx]"
     *
     * @return a formatted string representation of the shape
     */
    @Override
    public String toString() {
        return String.format("%s [height=%.3f, base area=%.3f, volume=%.3f]", 
            getClass().getSimpleName(), 
            getHeight(), 
            getBaseArea(), 
            getVolume());
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     * Subclasses should override this method to provide proper equality comparison.
     * 
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Note: Subclasses should implement proper equals and hashCode
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Shape other = (Shape) obj;
        return Double.compare(other.height, height) == 0;
    }
    
    /**
     * Returns a hash code value for the object.
     * Subclasses should override this when overriding equals.
     * 
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        // Note: Subclasses should implement proper hashCode
        return Double.hashCode(height);
    }
}
