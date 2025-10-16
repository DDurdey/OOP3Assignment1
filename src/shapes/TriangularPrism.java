package shapes;

public class TriangularPrism extends Prism {
	public TriangularPrism(double height, double side) {
		super(height, side);
	}
	
	@Override
    public double getBaseArea() {
        return (Math.sqrt(3) / 4) * Math.pow(side, 2);
    }

    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}