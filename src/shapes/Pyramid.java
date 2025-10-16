package shapes;

import appDomain.Shape;

public class Pyramid extends Shape {
	private double side;
	
	public Pyramid(double height, double side) {
		super(height);
		this.side = side;
	}
	
	@Override
    public double getBaseArea() {
        return Math.pow(side, 2);
    }

    @Override
    public double getVolume() {
        return (1.0 / 3.0) * Math.pow(side, 2) * height;
    }
}