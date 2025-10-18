package appDomain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Utility class providing various sorting algorithms for Shape arrays.
 * Implements both simple and efficient sorting algorithms with consistent APIs.
 * All sorts are performed in-place and support custom comparators.
 * 
 * @Team Link
 * @version 1.0
 */
public final class Sorts {

    /**
     * Prevents instantiation of this utility class.
     */
    private Sorts() {
        throw new AssertionError("Sorts is a utility class and cannot be instantiated");
    }

    /**
     * Sorts an array of shapes using the bubble sort algorithm.
     * Time Complexity: O(n²) worst-case, O(n) best-case (when already sorted)
     * Space Complexity: O(1)
     * Stable: Yes
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void bubbleSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        
        int n = arr.length;
        boolean swapped;
        
        // Iterate through all elements
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            // Last i elements are already in place, so reduce range each iteration
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements and swap if they are in wrong order
                // Using > 0 for descending order as per the original logic
                if (comp.compare(arr[j], arr[j + 1]) < 0) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            
            // If no swapping occurred, array is already sorted
            if (!swapped) break; 
        }
    }

    /**
     * Sorts an array of shapes using the selection sort algorithm.
     * Time Complexity: O(n²) in all cases
     * Space Complexity: O(1)
     * Stable: No
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void selectionSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        
        int n = arr.length;
        
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i; // Assume current position has maximum
            
            // Find the maximum element in remaining unsorted array
            for (int j = i + 1; j < n; j++) {
                // Using > 0 for descending order as per the original logic
                if (comp.compare(arr[j], arr[maxIdx]) > 0) {
                    maxIdx = j;
                }
            }
            
            // Swap the found maximum element with the current element
            swap(arr, maxIdx, i);
        }
    }

    /**
     * Sorts an array of shapes using the insertion sort algorithm.
     * Time Complexity: O(n²) worst-case, O(n) best-case
     * Space Complexity: O(1)
     * Stable: Yes
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void insertionSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        
        int n = arr.length;
        
        // Start from the second element (index 1)
        for (int i = 1; i < n; i++) {
            Shape key = arr[i]; // Current element to be inserted
            int j = i - 1;
            
            // Move elements that are less than key to one position ahead
            // Using < 0 for descending order as per the original logic
            while (j >= 0 && comp.compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Place the key in its correct position
            arr[j + 1] = key; 
        }
    }

    /**
     * Sorts an array of shapes using the merge sort algorithm.
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(n)
     * Stable: Yes
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void mergeSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        
        // Base case: arrays with 0 or 1 elements are already sorted
        if (arr.length < 2) return;
        
        mergeSortHelper(arr, 0, arr.length - 1, comp);
    }

    /**
     * Recursive helper method for merge sort.
     * 
     * @param arr the array to be sorted
     * @param left the left index of the subarray
     * @param right the right index of the subarray
     * @param comp the comparator to determine the order of elements
     */
    private static void mergeSortHelper(Shape[] arr, int left, int right, Comparator<Shape> comp) {
        if (left < right) {
            // Find the middle point to divide the array into two halves
            int mid = left + (right - left) / 2; // Prevents integer overflow
            
            // Recursively sort first and second halves
            mergeSortHelper(arr, left, mid, comp);
            mergeSortHelper(arr, mid + 1, right, comp);
            
            // Merge the sorted halves
            merge(arr, left, mid, right, comp);
        }
    }

    /**
     * Merges two sorted subarrays into a single sorted subarray.
     * 
     * @param arr the original array containing both subarrays
     * @param left the left index of the first subarray
     * @param mid the middle index separating the two subarrays
     * @param right the right index of the second subarray
     * @param comp the comparator to determine the order of elements
     */
    private static void merge(Shape[] arr, int left, int mid, int right, Comparator<Shape> comp) {
        // Sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        Shape[] leftArray = new Shape[n1];
        Shape[] rightArray = new Shape[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++)
            leftArray[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArray[j] = arr[mid + 1 + j];

        // Merge the temporary arrays back into the original array
        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            // Using >= 0 for descending order as per the original logic
            if (comp.compare(leftArray[i], rightArray[j]) >= 0) {
                arr[k++] = leftArray[i++];
            } else {
                arr[k++] = rightArray[j++];
            }
        }

        // Copy remaining elements of leftArray if any
        while (i < n1) arr[k++] = leftArray[i++];
        
        // Copy remaining elements of rightArray if any
        while (j < n2) arr[k++] = rightArray[j++];
    }

    /**
     * Sorts an array of shapes using the quick sort algorithm.
     * Time Complexity: O(n log n) average-case, O(n²) worst-case
     * Space Complexity: O(log n) due to recursion stack
     * Stable: No
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void quickSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        quickSortHelper(arr, 0, arr.length - 1, comp);
    }

    /**
     * Recursive helper method for quick sort.
     * 
     * @param arr the array to be sorted
     * @param low the starting index of the subarray
     * @param high the ending index of the subarray
     * @param comp the comparator to determine the order of elements
     */
    private static void quickSortHelper(Shape[] arr, int low, int high, Comparator<Shape> comp) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pivotIndex = partition(arr, low, high, comp);
            
            // Recursively sort elements before and after partition
            quickSortHelper(arr, low, pivotIndex - 1, comp);
            quickSortHelper(arr, pivotIndex + 1, high, comp);
        }
    }

    /**
     * Partitions the array for quick sort using the last element as pivot.
     * 
     * @param arr the array to be partitioned
     * @param low the starting index
     * @param high the ending index
     * @param comp the comparator to determine the order of elements
     * @return the final position of the pivot element
     */
    private static int partition(Shape[] arr, int low, int high, Comparator<Shape> comp) {
        Shape pivot = arr[high]; // Choose rightmost element as pivot
        int i = low - 1; // Index of smaller element
        
        // Rearrange elements around pivot
        for (int j = low; j < high; j++) {
            // Using > 0 for descending order as per the original logic
            if (comp.compare(arr[j], pivot) > 0) {
                i++;
                swap(arr, i, j);
            }
        }
        
        // Place pivot in correct position
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Sorts an array of shapes using the heap sort algorithm.
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(1)
     * Stable: No
     * 
     * @param arr the array to be sorted
     * @param comp the comparator to determine the order of elements
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static void heapSort(Shape[] arr, Comparator<Shape> comp) {
        validateInput(arr, comp);
        
        int n = arr.length;
        
        // Build max heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, comp);
        }
        
        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            swap(arr, 0, i);
            
            // Call max heapify on the reduced heap
            heapify(arr, i, 0, comp);
        }
    }

    /**
     * Maintains the max heap property for a subtree rooted at index i.
     * 
     * @param arr the array representing the heap
     * @param n the size of the heap
     * @param i the root index of the subtree
     * @param comp the comparator to determine the order of elements
     */
    private static void heapify(Shape[] arr, int n, int i, Comparator<Shape> comp) {
        int largest = i;     // Initialize largest as root
        int left = 2 * i + 1; // Left child index
        int right = 2 * i + 2; // Right child index
        
        // If left child is larger than root
        if (left < n && comp.compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }
        
        // If right child is larger than current largest
        if (right < n && comp.compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }
        
        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest);
            
            // Recursively heapify the affected subtree
            heapify(arr, n, largest, comp);
        }
    }

    // Dispatch table for sorting algorithms
    private static final Map<String, BiConsumer<Shape[], Comparator<Shape>>> SORT_DISPATCH;
    
    static {
        SORT_DISPATCH = new HashMap<>();
        SORT_DISPATCH.put("bubble", Sorts::bubbleSort);
        SORT_DISPATCH.put("selection", Sorts::selectionSort);
        SORT_DISPATCH.put("insertion", Sorts::insertionSort);
        SORT_DISPATCH.put("merge", Sorts::mergeSort);
        SORT_DISPATCH.put("quick", Sorts::quickSort);
        SORT_DISPATCH.put("heap", Sorts::heapSort);
    }

    /**
     * Benchmarks the performance of a specified sorting algorithm.
     * 
     * @param arr the array to be sorted (will be modified)
     * @param comp the comparator to determine the order of elements
     * @param sortType the type of sort algorithm to use
     * @return the time taken to sort the array in nanoseconds
     * @throws IllegalArgumentException if the array, comparator, or sort type is invalid
     */
    public static long benchmarkSort(Shape[] arr, Comparator<Shape> comp, String sortType) {
        validateInput(arr, comp);
        
        if (sortType == null || sortType.trim().isEmpty()) {
            throw new IllegalArgumentException("Sort type cannot be null or empty");
        }
        
        String normalizedSortType = sortType.toLowerCase();
        BiConsumer<Shape[], Comparator<Shape>> sortAction = SORT_DISPATCH.get(normalizedSortType);
        
        if (sortAction == null) {
            throw new IllegalArgumentException("Unknown sort type: " + sortType + 
                ". Supported types: " + SORT_DISPATCH.keySet());
        }
        
        long startTime = System.nanoTime();
        sortAction.accept(arr, comp);
        long endTime = System.nanoTime();
        
        return endTime - startTime;
    }

    /**
     * Prints checkpoint elements from a sorted array for verification.
     * Displays first element, every 1000th element, and last element.
     * 
     * @param arr the sorted array to display checkpoints from
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static void printCheckpoints(Shape[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        System.out.println("First: " + arr[0]);
        
        // Print every 1000th element
        for (int i = 1000; i < arr.length; i += 1000) {
            System.out.println(i + "-th: " + arr[i]);
        }
        
        System.out.println("Last: " + arr[arr.length - 1]);
    }

    /**
     * Validates input parameters for sorting methods.
     * 
     * @param arr the array to validate
     * @param comp the comparator to validate
     * @throws IllegalArgumentException if array or comparator is null
     */
    private static void validateInput(Shape[] arr, Comparator<Shape> comp) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (comp == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
    }

    /**
     * Swaps two elements in an array.
     * 
     * @param arr the array containing elements to swap
     * @param i the index of the first element
     * @param j the index of the second element
     */
    private static void swap(Shape[] arr, int i, int j) {
        Shape temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
