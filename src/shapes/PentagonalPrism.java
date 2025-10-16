package shapes;

public class PentagonalPrism extends Prism {
	public PentagonalPrism(double height, double side) {
		super(height, side);
	}
	
	@Override
    public double getBaseArea() {
        return (5 * Math.pow(side, 2) * Math.tan(Math.toRadians(54))) / 4;
    }

    @Override
    public double getVolume() {
        return getBaseArea() * height;
    }
}