package appDomain;
import java.util.Comparator;

public class ShapeComparator {
	public static final Comparator<Shape> By_Base_Area = new Comparator<Shape>() {
		@Override
		public int compare(Shape s1, Shape s2) {
			return Double.compare(s1.getBaseArea(), s2.getBaseArea());
		}
	};
	
	public static final Comparator<Shape> By_Volume = new Comparator<Shape>() {
		@Override
		public int compare(Shape s1, Shape s2) {
			return Double.compare(s1.getVolume(), s2.getVolume());
		}
	};
}
