package shapes;
import appDomain.Shape;

public abstract class Prism extends Shape {
	protected double side;
	
	public Prism(double height, double side) {
		super(height);
		this.side = side;
	}
}