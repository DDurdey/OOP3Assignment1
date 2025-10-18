package appDomain;
import java.util.Comparator;

public class ShapeComparator {
    // Comparator for comparing Shape objects by their base area
    // Uses Double.compare for precise floating-point comparison
    // Returns:
    //   negative if s1.baseArea < s2.baseArea
    //   zero if s1.baseArea == s2.baseArea  
    //   positive if s1.baseArea > s2.baseArea
	public static final Comparator<Shape> By_Base_Area = new Comparator<Shape>() {
		@Override
		public int compare(Shape s1, Shape s2) {
			return Double.compare(s1.getBaseArea(), s2.getBaseArea());
		}
	};
	
    // Comparator for comparing Shape objects by their volume
    // Uses Double.compare for precise floating-point comparison
    // Returns:
    //   negative if s1.volume < s2.volume
    //   zero if s1.volume == s2.volume  
    //   positive if s1.volume > s2.volume
	public static final Comparator<Shape> By_Volume = new Comparator<Shape>() {
		@Override
		public int compare(Shape s1, Shape s2) {
			return Double.compare(s1.getVolume(), s2.getVolume());
		}
	};
}
