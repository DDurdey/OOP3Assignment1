package appDomain;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
public class Sorts {

    // bubble Sort
    public static void bubbleSort(Shape[] arr, Comparator<Shape> comp) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {

                if (comp.compare(arr[j], arr[j + 1]) < 0) {
                    Shape temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; 
        }
    }

    // selection Sort 
    public static void selectionSort(Shape[] arr, Comparator<Shape> comp) {
        int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        int maxIdx = i;
        for (int j = i + 1; j < n; j++) {
 
            if (comp.compare(arr[j], arr[maxIdx]) > 0) {
                maxIdx = j;
            }
        }
        Shape temp = arr[maxIdx];
        arr[maxIdx] = arr[i];
        arr[i] = temp; 
    }
    }

    // insertion Sort
    public static void insertionSort(Shape[] arr, Comparator<Shape> comp) {
        int n = arr.length;
    for (int i = 1; i < n; i++) {
        Shape key = arr[i];
        int j = i - 1;
        while (j >= 0 && comp.compare(arr[j], key) < 0) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key; 
    }
    }

    // merge Sort
    public static void mergeSort(Shape[] arr, Comparator<Shape> comp) {
        if (arr == null || arr.length < 2) return;
            mergeSortHelper(arr, 0, arr.length - 1, comp);    }

    private static void mergeSortHelper(Shape[] arr, int left, int right, Comparator<Shape> comp) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSortHelper(arr, left, mid, comp);
        mergeSortHelper(arr, mid + 1, right, comp);
        merge(arr, left, mid, right, comp);
        }
    }
    private static void merge(Shape[] arr, int left, int mid, int right, Comparator<Shape> comp) {  
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Shape[] L = new Shape[n1];
        Shape[] R = new Shape[n2];

        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (comp.compare(L[i], R[j]) >= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // quick Sort
    public static void quickSort(Shape[] arr, Comparator<Shape> comp) {
        quickSortHelper(arr, 0, arr.length - 1, comp);
    }

    private static void quickSortHelper(Shape[] arr, int low, int high, Comparator<Shape> comp) {
        if (low < high) {
            int pi = partition(arr, low, high, comp);
            quickSortHelper(arr, low, pi - 1, comp);
            quickSortHelper(arr, pi + 1, high, comp);
        }
    }

    private static int partition(Shape[] arr, int low, int high, Comparator<Shape> comp) {
        Shape pivot = arr[high];
            int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(arr[j], pivot) > 0) {
                i++;
                Shape temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    Shape temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
    }

        private static final Map<String, BiConsumer<Shape[], Comparator<Shape>>> SORT_DISPATCH;
    static {
        SORT_DISPATCH = new HashMap<>();
        SORT_DISPATCH.put("bubble", (a, c) -> bubbleSort(a, c));
        SORT_DISPATCH.put("selection", (a, c) -> selectionSort(a, c));
        SORT_DISPATCH.put("insertion", (a, c) -> insertionSort(a, c));
        SORT_DISPATCH.put("merge", (a, c) -> mergeSort(a, c));
        SORT_DISPATCH.put("quick", (a, c) -> quickSort(a, c));
        SORT_DISPATCH.put("heap", (a, c) -> heapSort(a, c));
    }


    // benchmark sort
    public static long benchmarkSort(Shape[] arr, Comparator<Shape> comp, String sortType) {
        long start = System.nanoTime();
        BiConsumer<Shape[], Comparator<Shape>> action = SORT_DISPATCH.get(sortType);
        if (action == null) throw new IllegalArgumentException("Unknown sort type: " + sortType);
        action.accept(arr, comp);
        long end = System.nanoTime();
        return end - start;
    }

    public static void printCheckpoints(Shape[] arr) {
        System.out.println("First: " + arr[0]);
        for (int i = 1000; i < arr.length; i += 1000) {
            System.out.println(i + "-th: " + arr[i]);
        }
        System.out.println("Last: " + arr[arr.length - 1]);
    }

    
    // Heap Sort  
    public static void heapify(Shape[] arr, int n, int i, Comparator<Shape> comp) { 
    	int largest = i;
    	int left = 2 * i + 1;
    	int right = 2 * i + 2;
    	
    	
    	if (left < n && comp.compare(arr[left], arr[largest]) > 0) {
    		largest = left;
    	}
    	
    	
    	if (right < n && comp.compare(arr[right], arr[largest]) > 0) {
    		largest = right;
    	}
    	
    	
    	if (largest != i) {
    		Shape swap = arr[i];
    		arr[i] = arr[largest];
    		arr[largest] = swap;
    		
    		
    		heapify(arr, n, largest, comp);
    	}
    	
    }

    
    public static void heapSort(Shape[] arr, Comparator<Shape> comp) {
    	int n = arr.length;
    	
    	
    	for (int i = n / 2 - 1; i >= 0; i--) {
    		heapify(arr, n, i, comp);
    	}
    	
    	
    	for (int i =n -1; i > 0; i--) {
    		
    		Shape temp = arr[0];
    		arr[0] = arr[i];
    		arr[i] = temp;
    		
    		heapify(arr, i, 0, comp);
    	}
    }
}
