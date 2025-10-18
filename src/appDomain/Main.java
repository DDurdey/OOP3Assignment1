package appDomain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import shapes.*;

/**
 * handles commandline parsing, file reading, sorting, and outputs
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            CommandLineArgs cmdArgs = parseArguments(args);
            
            // validate arguments
            if (cmdArgs.filename == null || cmdArgs.sortType == null || cmdArgs.compareType == null) {
                printUsage();
                return;
            }
            
            Shape[] shapes = loadShapesFromFile(cmdArgs.filename);
            
            if (shapes == null || shapes.length == 0) {
                System.out.println("No shapes loaded from file.");
                return;
            }
            
            System.out.println("Loaded " + shapes.length + " shapes from " + cmdArgs.filename);
            
            // Get the appropriate comparator
            Comparator<Shape> comparator = getComparator(cmdArgs.compareType);
            
            // Perform sorting and measure time
            long timeNanos = Sorts.benchmarkSort(shapes, comparator, cmdArgs.sortType);
            double timeMillis = timeNanos / 1_000_000.0;
            
            // Display results
            System.out.println("\nSorting completed using " + cmdArgs.sortType + " sort");
            System.out.println("Sorted by: " + getCompareTypeDescription(cmdArgs.compareType));
            System.out.printf("Time taken: %.2f milliseconds%n", timeMillis);
            System.out.println();
            
            // Print checkpoint shapes
            printCheckpoints(shapes);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Parses command line arguments according to assignment specifications
     * Handles formats like: -ffilename, -tv, -sb (no spaces between flag and value)
     */
    private static CommandLineArgs parseArguments(String[] args) {
        CommandLineArgs cmdArgs = new CommandLineArgs();
        
        for (String arg : args) {
            String lowerArg = arg.toLowerCase();
            
            // Handle -f flag (filename)
            if (lowerArg.startsWith("-f")) {
                if (lowerArg.length() > 2) {
                    // Format: -ffilename
                    cmdArgs.filename = arg.substring(2);
                } else {
                    System.err.println("Error: -f flag requires a filename");
                }
            }
            // Handle -t flag (compare type)
            else if (lowerArg.startsWith("-t")) {
                if (lowerArg.length() > 2) {
                    // Format: -tv, -th, -ta
                    cmdArgs.compareType = lowerArg.substring(2);
                } else {
                    System.err.println("Error: -t flag requires a compare type (h/height, v/volume, a/area)");
                }
            }
            // Handle -s flag (sort type)
            else if (lowerArg.startsWith("-s")) {
                if (lowerArg.length() > 2) {
                    // Format: -sb, -sq, etc.
                    String sortFlag = lowerArg.substring(2);
                    cmdArgs.sortType = mapSortType(sortFlag);
                } else {
                    System.err.println("Error: -s flag requires a sort type");
                }
            }
            // Handle quoted filenames (like -f"filename")
            else if (arg.startsWith("-f\"") || arg.startsWith("-F\"")) {
                String filename = arg.substring(3);
                if (filename.endsWith("\"")) {
                    filename = filename.substring(0, filename.length() - 1);
                }
                cmdArgs.filename = filename;
            }
        }
        
        return cmdArgs;
    }
    
    /**
     * Maps single character sort flags to full sort algorithm names
     */
    private static String mapSortType(String sortFlag) {
        switch (sortFlag) {
            case "b": return "bubble";
            case "s": return "selection";
            case "i": return "insertion";
            case "m": return "merge";
            case "q": return "quick";
            case "h": return "heap";
            case "z": return "heap"; // Using heap as the 6th algorithm choice
            default:
                System.err.println("Error: Unknown sort type '" + sortFlag + "'");
                return null;
        }
    }
    
    /**
     * Loads shapes from the specified file
     */
    private static Shape[] loadShapesFromFile(String filename) {
        List<Shape> shapeList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null) {
                return new Shape[0];
            }
            
            int count = Integer.parseInt(line.trim());
            
            for (int i = 0; i < count; i++) {
                line = reader.readLine();
                if (line == null) break;
                
                Shape shape = parseShape(line.trim());
                if (shape != null) {
                    shapeList.add(shape);
                }
            }
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        
        return shapeList.toArray(new Shape[0]);
    }
    
    /**
     * Parses a single line to create a Shape object
     */
    private static Shape parseShape(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) return null;
        
        try {
            String shapeType = parts[0];
            double height = Double.parseDouble(parts[1]);
            double parameter = Double.parseDouble(parts[2]);
            
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
        } catch (NumberFormatException e) {
            System.err.println("Error parsing shape data: " + line);
            return null;
        }
    }
    
    /**
     * Gets the appropriate comparator based on the compare type
     */
    private static Comparator<Shape> getComparator(String compareType) {
        switch (compareType.toLowerCase()) {
            case "h":
            case "height":
                return Comparator.<Shape>naturalOrder().reversed(); // Height descending
            case "a":
            case "area":
                return (s1, s2) -> Double.compare(s2.getBaseArea(), s1.getBaseArea()); // Area descending
            case "v":
            case "volume":
                return (s1, s2) -> Double.compare(s2.getVolume(), s1.getVolume()); // Volume descending
            default:
                throw new IllegalArgumentException("Invalid compare type: " + compareType + 
                    ". Valid options are: h/height, a/area, v/volume");
        }
    }
    
    /**
     * Gets description for the compare type
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
                return "Unknown";
        }
    }
    
    /**
     * Prints checkpoint shapes (first, every 1000th, and last)
     */
    private static void printCheckpoints(Shape[] shapes) {
        if (shapes.length == 0) return;
        
        System.out.println("First shape: " + shapes[0]);
        
        for (int i = 1000; i < shapes.length; i += 1000) {
            System.out.println("Shape at index " + i + ": " + shapes[i]);
        }
        
        if (shapes.length > 1) {
            System.out.println("Last shape: " + shapes[shapes.length - 1]);
        }
    }
    
    /**
     * Prints usage information according to assignment specifications
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar Sort.jar -f<filename> -t<compare_type> -s<sort_algorithm>");
        System.out.println();
        System.out.println("Arguments (case-insensitive, order-insensitive):");
        System.out.println("  -f<filename>       : Path to the shapes data file (no space between -f and filename)");
        System.out.println("  -t<compare_type>   : h/height, a/area, v/volume (no space between -t and type)");
        System.out.println("  -s<sort_algorithm> : b/bubble, s/selection, i/insertion, m/merge, q/quick, h/heap");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar Sort.jar -fshapes1.txt -tv -sb");
        System.out.println("  java -jar Sort.jar -ta -sq -f\"res\\shapes1.txt\"");
        System.out.println("  java -jar Sort.jar -th -f\"C:\\temp\\shapes1.txt\" -sb");
    }
    
    /**
     * Inner class to hold command line arguments
     */
    private static class CommandLineArgs {
        String filename;
        String compareType;
        String sortType;
    }
}