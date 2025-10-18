package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import shapes.*;

/**
 * Complexity and Sorting - Assignemnt 1
 * Handles command line parsing, file reading, sorting operations, and result output.
 * 
 * @Team Link
 * @version 1.0
 */
public class Main {
    
    /**
     * Main entry point for the shape sorting application.
     * Parses command line arguments, loads shapes, performs sorting, and displays results.
     *
     * @param args command line arguments: -f<filename> -t<compare_type> -s<sort_algorithm>
     */
    public static void main(String[] args) {
        try {
            CommandLineArgs cmdArgs = parseArguments(args);
            
            // Validate that all required arguments are provided
            if (!validateArguments(cmdArgs)) {
                printUsage();
                return;
            }
            
            Shape[] shapes = loadShapesFromFile(cmdArgs.filename);
            
            if (shapes == null || shapes.length == 0) {
                System.out.println("No shapes loaded from file.");
                return;
            }
            
            System.out.println("Loaded " + shapes.length + " shapes from " + cmdArgs.filename);
            
            // Get the appropriate comparator based on user selection
            Comparator<Shape> comparator = getComparator(cmdArgs.compareType);
            
            // Perform sorting and measure execution time
            long timeNanos = Sorts.benchmarkSort(shapes, comparator, cmdArgs.sortType);
            double timeMillis = timeNanos / 1_000_000.0;
            
            // Display sorting results and performance metrics
            displayResults(cmdArgs, timeMillis);
            
            // Print checkpoint shapes for verification
            printCheckpoints(shapes);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Validates that all required command line arguments are present and valid.
     *
     * @param cmdArgs the parsed command line arguments
     * @return true if all arguments are valid, false otherwise
     */
    private static boolean validateArguments(CommandLineArgs cmdArgs) {
        if (cmdArgs.filename == null || cmdArgs.filename.trim().isEmpty()) {
            System.err.println("Error: Filename is required");
            return false;
        }
        if (cmdArgs.sortType == null || cmdArgs.sortType.trim().isEmpty()) {
            System.err.println("Error: Sort type is required");
            return false;
        }
        if (cmdArgs.compareType == null || cmdArgs.compareType.trim().isEmpty()) {
            System.err.println("Error: Compare type is required");
            return false;
        }
        return true;
    }
    
    /**
     * Parses command line arguments according to assignment specifications.
     * Handles formats like: -ffilename, -tv, -sb (no spaces between flag and value).
     * Also supports quoted filenames: -f"filename with spaces.txt"
     *
     * @param args command line arguments array
     * @return parsed arguments container
     */
    private static CommandLineArgs parseArguments(String[] args) {
        CommandLineArgs cmdArgs = new CommandLineArgs();
        
        for (String arg : args) {
            if (arg.length() < 2) {
                System.err.println("Warning: Invalid argument format: " + arg);
                continue;
            }
            
            String flag = arg.substring(0, 2).toLowerCase();
            String value = arg.substring(2);
            
            switch (flag) {
                case "-f":
                    cmdArgs.filename = parseFilename(value);
                    break;
                case "-t":
                    cmdArgs.compareType = parseCompareType(value);
                    break;
                case "-s":
                    cmdArgs.sortType = mapSortType(value.toLowerCase());
                    break;
                default:
                    System.err.println("Warning: Unknown flag: " + flag);
                    break;
            }
        }
        
        return cmdArgs;
    }
    
    /**
     * Parses filename value, handling quoted filenames.
     *
     * @param value the filename value from command line
     * @return cleaned filename string
     */
    private static String parseFilename(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
    
    /**
     * Parses and validates compare type value.
     *
     * @param value the compare type value from command line
     * @return validated compare type string
     */
    private static String parseCompareType(String value) {
        String lowerValue = value.toLowerCase();
        if (lowerValue.matches("^(h|height|a|area|v|volume)$")) {
            return lowerValue;
        }
        System.err.println("Warning: Invalid compare type: " + value);
        return null;
    }
    
    /**
     * Maps single character sort flags to full sort algorithm names.
     * Supports both single-letter and full algorithm names.
     *
     * @param sortFlag the sort algorithm flag
     * @return full algorithm name, or null if invalid
     */
    private static String mapSortType(String sortFlag) {
        switch (sortFlag) {
            case "b":
            case "bubble":
                return "bubble";
            case "s":
            case "selection":
                return "selection";
            case "i":
            case "insertion":
                return "insertion";
            case "m":
            case "merge":
                return "merge";
            case "q":
            case "quick":
                return "quick";
            case "h":
            case "heap":
                return "heap";
            case "z": // Alternative for heap sort as specified
                return "heap";
            default:
                System.err.println("Error: Unknown sort type '" + sortFlag + "'");
                return null;
        }
    }
    
    /**
     * Loads shapes from the specified file.
     * File format: first line contains count, subsequent lines contain shape data.
     *
     * @param filename the path to the shapes data file
     * @return array of Shape objects, or null if error occurs
     */
    private static Shape[] loadShapesFromFile(String filename) {
        List<Shape> shapeList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null) {
                System.err.println("Error: File is empty");
                return new Shape[0];
            }
            
            // Parse the count of shapes from first line
            int count = parseShapeCount(line.trim());
            if (count <= 0) {
                return new Shape[0];
            }
            
            // Read and parse each shape line
            for (int i = 0; i < count; i++) {
                line = reader.readLine();
                if (line == null) {
                    System.err.println("Warning: Expected " + count + " shapes but reached end of file at line " + (i + 1));
                    break;
                }
                
                Shape shape = parseShape(line.trim());
                if (shape != null) {
                    shapeList.add(shape);
                }
            }
            
            System.out.println("Successfully loaded " + shapeList.size() + " out of " + count + " expected shapes");
            
        } catch (IOException e) {
            System.err.println("Error reading file '" + filename + "': " + e.getMessage());
            return null;
        } catch (SecurityException e) {
            System.err.println("Security exception accessing file '" + filename + "': " + e.getMessage());
            return null;
        }
        
        return shapeList.toArray(new Shape[0]);
    }
    
    /**
     * Parses the shape count from the first line of the file.
     *
     * @param countLine the first line containing the shape count
     * @return parsed count, or 0 if invalid
     */
    private static int parseShapeCount(String countLine) {
        try {
            int count = Integer.parseInt(countLine);
            if (count < 0) {
                System.err.println("Error: Shape count cannot be negative: " + count);
                return 0;
            }
            return count;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid shape count format: " + countLine);
            return 0;
        }
    }
    
    /**
     * Parses a single line to create a Shape object.
     * Expected format: "ShapeType height parameter"
     *
     * @param line the input line containing shape data
     * @return Shape object, or null if parsing fails
     */
    private static Shape parseShape(String line) {
        if (line == null || line.trim().isEmpty()) {
            System.err.println("Warning: Empty shape line skipped");
            return null;
        }
        
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
            System.err.println("Warning: Invalid shape data (expected 3 parts): " + line);
            return null;
        }
        
        try {
            String shapeType = parts[0];
            double height = Double.parseDouble(parts[1]);
            double parameter = Double.parseDouble(parts[2]);
            
            // Validate numerical values
            if (height <= 0 || parameter <= 0) {
                System.err.println("Warning: Shape dimensions must be positive: " + line);
                return null;
            }
            
            return createShape(shapeType, height, parameter);
            
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numerical values in line: " + line);
            return null;
        }
    }
    
    /**
     * Creates a specific Shape subclass instance based on type string.
     *
     * @param shapeType the type of shape to create
     * @param height the height dimension
     * @param parameter the shape-specific parameter (radius, side length, etc.)
     * @return concrete Shape instance, or null if type is unknown
     */
    private static Shape createShape(String shapeType, double height, double parameter) {
        switch (shapeType.toLowerCase()) {
            case "cone":
                return new Cone(height, parameter);
            case "cylinder":
                return new Cylinder(height, parameter);
            case "octagonalprism":
                return new OctagonalPrism(height, parameter);
            case "pentagonalprism":
                return new PentagonalPrism(height, parameter);
            case "squareprism":
                return new SquarePrism(height, parameter);
            case "triangularprism":
                return new TriangularPrism(height, parameter);
            case "pyramid":
                return new Pyramid(height, parameter);
            default:
                System.err.println("Unknown shape type: " + shapeType);
                return null;
        }
    }
    
    /**
     * Gets the appropriate comparator based on the compare type.
     * All comparisons are in descending order as specified.
     *
     * @param compareType the type of comparison (h/height, a/area, v/volume)
     * @return Comparator for Shape objects
     * @throws IllegalArgumentException for invalid compare types
     */
    private static Comparator<Shape> getComparator(String compareType) {
        switch (compareType.toLowerCase()) {
            case "h":
            case "height":
                return Comparator.<Shape>naturalOrder().reversed(); // Height descending
            case "a":
            case "area":
                return Comparator.comparingDouble(Shape::getBaseArea).reversed(); // Area descending
            case "v":
            case "volume":
                return Comparator.comparingDouble(Shape::getVolume).reversed(); // Volume descending
            default:
                throw new IllegalArgumentException("Invalid compare type: " + compareType + 
                    ". Valid options are: h/height, a/area, v/volume");
        }
    }
    
    /**
     * Gets human-readable description for the compare type.
     *
     * @param compareType the compare type string
     * @return descriptive string for display
     */
    private static String getCompareTypeDescription(String compareType) {
        switch (compareType.toLowerCase()) {
            case "h":
            case "height":
                return "Height (descending)";
            case "a":
            case "area":
                return "Base Area (descending)";
            case "v":
            case "volume":
                return "Volume (descending)";
            default:
                return "Unknown comparison type";
        }
    }
    
    /**
     * Displays sorting results and performance metrics.
     *
     * @param cmdArgs the command line arguments
     * @param timeMillis the sorting time in milliseconds
     */
    private static void displayResults(CommandLineArgs cmdArgs, double timeMillis) {
        System.out.println("\nSorting completed using " + cmdArgs.sortType + " sort");
        System.out.println("Sorted by: " + getCompareTypeDescription(cmdArgs.compareType));
        System.out.printf("Time taken: %.2f milliseconds%n", timeMillis);
        System.out.println();
    }
    
    /**
     * Prints checkpoint shapes (first, every 1000th, and last) for verification.
     *
     * @param shapes the sorted array of shapes
     */
    private static void printCheckpoints(Shape[] shapes) {
        if (shapes.length == 0) {
            System.out.println("No shapes to display");
            return;
        }
        
        System.out.println("First shape: " + shapes[0]);
        
        // Print every 1000th shape
        for (int i = 1000; i < shapes.length; i += 1000) {
            System.out.println("Shape at index " + i + ": " + shapes[i]);
        }
        
        // Print last shape if different from last checkpoint
        if (shapes.length > 1) {
            int lastIndex = shapes.length - 1;
            if (lastIndex % 1000 != 0) { // Avoid duplicate if last index is a multiple of 1000
                System.out.println("Last shape: " + shapes[lastIndex]);
            }
        }
    }
    
    /**
     * Prints comprehensive usage information.
     */
    private static void printUsage() {
        System.out.println("Shape Sorter - Geometric Shape Sorting Application");
        System.out.println("==================================================");
        System.out.println();
        System.out.println("Usage: java -jar Sort.jar -f<filename> -t<compare_type> -s<sort_algorithm>");
        System.out.println();
        System.out.println("Arguments (case-insensitive, order-insensitive, no spaces between flag and value):");
        System.out.println();
        System.out.println("  -f<filename>       : Path to the shapes data file");
        System.out.println("                       Supports quoted filenames: -f\"filename with spaces.txt\"");
        System.out.println();
        System.out.println("  -t<compare_type>   : Comparison criteria:");
        System.out.println("                       h or height  - Sort by height (descending)");
        System.out.println("                       a or area    - Sort by base area (descending)");
        System.out.println("                       v or volume  - Sort by volume (descending)");
        System.out.println();
        System.out.println("  -s<sort_algorithm> : Sorting algorithm:");
        System.out.println("                       b or bubble     - Bubble sort");
        System.out.println("                       s or selection  - Selection sort");
        System.out.println("                       i or insertion  - Insertion sort");
        System.out.println("                       m or merge      - Merge sort");
        System.out.println("                       q or quick      - Quick sort");
        System.out.println("                       h or heap       - Heap sort");
        System.out.println("                       z               - Heap sort (alternative)");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar Sort.jar -fshapes1.txt -tv -sb");
        System.out.println("  java -jar Sort.jar -ta -sq -f\"res\\\\shapes1.txt\"");
        System.out.println("  java -jar Sort.jar -th -f\"C:\\\\temp\\\\shapes1.txt\" -sb");
        System.out.println("  java -jar Sort.jar -fdata.txt -tarea -smerge");
    }
    
    /**
     * Inner class to hold parsed command line arguments.
     * Uses immutable fields with validation.
     */
    private static class CommandLineArgs {
        private final String filename;
        private final String compareType;
        private final String sortType;
        
        /**
         * Default constructor for cases where parsing fails.
         */
        public CommandLineArgs() {
            this.filename = null;
            this.compareType = null;
            this.sortType = null;
        }
        
        /**
         * Constructor with validation for creating valid argument sets.
         */
        public CommandLineArgs(String filename, String compareType, String sortType) {
            this.filename = filename;
            this.compareType = compareType;
            this.sortType = sortType;
        }
    }
}
