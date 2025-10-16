package appDomain;

public abstract class Shape implements Comparable<Shape> {
	protected double height;
	
	public Shape(double height) {
		this.height = height;
	}
	
	public double getHeight() {
		return height;
	}
	
	public abstract double getBaseArea();
	public abstract double getVolume();
	
	@Override
	public int compareTo(Shape other) {
		if (this.height > other.height)
			return 1;
		else if (this.height < other.height)
			return -1;
		else
			return 0;
	}
	
	@Override
	public String toString() {
		return String.format("%s [height=%.3f, base area=%.3f, volume=%.3f]", getClass().getSimpleName(), getHeight(), getBaseArea(), getVolume());
	}
}
