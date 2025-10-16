package shapes;

public class OctagonalPrism extends Prism {
	public OctagonalPrism(double height, double side) {
		super(height, side);
	}
	
	@Override
    public double getBaseArea() {
        return 2 * (1 + Math.sqrt(2)) * Math.pow(side, 2);
    }

    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}