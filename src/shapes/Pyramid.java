package shapes;

import appDomain.Shape;

/**
 * Represents a three-dimensional square pyramid shape.
 * A pyramid has a square base and triangular faces that meet at a common apex.
 * Inherits height from the Shape class and adds side length as a specific attribute.
 */
public class Pyramid extends Shape {
    
    /**
     * The length of one side of the square base of the pyramid.
     */
    private double side;
    
    /**
     * Constructs a new Pyramid with the specified height and base side length.
     *
     * @param height the vertical height of the pyramid from base to apex
     * @param side the length of one side of the square base
     */
    public Pyramid(double height, double side) {
        super(height);
        this.side = side;
    }
    
    /**
     * Calculates and returns the base area of the pyramid.
     * The base area is the area of the square base.
     * Formula: side²
     *
     * @return the area of the square base of the pyramid
     */
    @Override
    public double getBaseArea() {
        return Math.pow(side, 2);
    }

    /**
     * Calculates and returns the volume of the pyramid.
     * Formula: (1/3) × base area × height
     *
     * @return the volume of the pyramid
     */
    @Override
    public double getVolume() {
        return (1.0 / 3.0) * Math.pow(side, 2) * height;
    }
}
