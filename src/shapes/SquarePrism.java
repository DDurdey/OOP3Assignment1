package shapes;

public class SquarePrism extends Prism {
	public SquarePrism(double height, double side) {
		super(height, side);
	}
	
	@Override
    public double getBaseArea() {
        return Math.pow(side, 2);
    }

    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}