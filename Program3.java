/**************************************************************/
/* Joseline Ly                                                */
/* Login ID: 017241510                                        */
/* CS 3310, Spring 2025                                       */
/* Programming Assignment 3                                   */
/* Program3 class: reads a file and calculates the optimal    */
/*                 canoe rental cost and path                 */
/**************************************************************/

import java.io.*;
import java.util.*;

public class Program3 {
    
    /***************************************************************************/
    /* Method: main                                                            */
    /* Purpose: Take in filename and determine optimal cost matrix, optimal    */
    /*          cost, and optimal path; call printOptimalPath to display       */
    /* Parameters:                                                             */
    /*      String[] args: filename                                            */
    /* No return value                                                         */
    /***************************************************************************/
    public static void main(String[] args) {
        if (args.length != 1) { // Ensure exactly one argument is provided
            System.out.println("Usage: java Program3 <input_file>");
            System.exit(1);
        }

        String inputFile = args[0]; // Input file name
        try {
            // Open the file for reading
            BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));

            int totalPosts = Integer.parseInt(fileReader.readLine().trim()); // Number of posts along the river
            int[][] rentalCosts = new int[totalPosts][totalPosts]; // Matrix to store rental costs

            // Initialize all entries to a large value (infinity)
            for (int i = 0; i < totalPosts; i++) {
                Arrays.fill(rentalCosts[i], Integer.MAX_VALUE);
            }

            // Populate the rental cost matrix from the file
            for (int i = 0; i < totalPosts - 1; i++) {
                String[] costValues = fileReader.readLine().trim().split("\\s+");
                for (int j = 0; j < costValues.length; j++) {
                    rentalCosts[i][i + j + 1] = Integer.parseInt(costValues[j].trim());
                }
            }
            fileReader.close();

            int[][] minCost = new int[totalPosts][totalPosts]; // Matrix for minimum costs
            int[][] pathTracker = new int[totalPosts][totalPosts]; // Matrix to track the rental path

            // Initialize the minimum cost matrix
            for (int i = 0; i < totalPosts; i++) {
                for (int j = 0; j < totalPosts; j++) {
                    minCost[i][j] = (i < j) ? Integer.MAX_VALUE : 0;
                }
            }

            // Compute the minimum cost for each route using dynamic programming
            for (int length = 2; length <= totalPosts; length++) { // Length of the route
                for (int start = 0; start <= totalPosts - length; start++) { // Starting post
                    int end = start + length - 1; // Ending post
                    minCost[start][end] = Integer.MAX_VALUE; // Initialize to a large value

                    // Check all intermediate posts
                    for (int mid = start; mid < end; mid++) {
                        int currentCost = minCost[start][mid] + rentalCosts[mid][end];
                        if (currentCost < minCost[start][end]) {
                            minCost[start][end] = currentCost; // Update minimum cost
                            pathTracker[start][end] = mid; // Record the intermediate post
                        }
                    }
                }
            }

            // Output the results
            System.out.println("Minimum Cost Matrix:");
            for (int[] row : minCost) {
                for (int value : row) {
                    // Format each value to be right-aligned in a 5-character wide column
                    System.out.printf("%5s", (value == Integer.MAX_VALUE ? "INF" : value));
                }
                System.out.println();
            }

            System.out.println("Optimal cost from post 0 to post " + (totalPosts - 1) + ": " + minCost[0][totalPosts - 1]);
            System.out.print("Optimal rental sequence:\n");
            printOptimalPath(pathTracker, 0, totalPosts - 1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(1);
        }
    }

    /***************************************************************************/
    /* Method: printOptimalPath                                                */
    /* Purpose: Recursively print the sequence of rentals for the optimal path */
    /* Parameters:                                                             */
    /*      int[][] pathTracker: matrix tracking intermediate posts            */
    /*      int start: starting post                                           */
    /*      int end: ending post                                               */
    /* No return value                                                         */
    /***************************************************************************/
    private static void printOptimalPath(int[][] pathTracker, int start, int end) {
        if (start == end) return;
        int mid = pathTracker[start][end];
        if (mid != start) printOptimalPath(pathTracker, start, mid);
        System.out.println("\tRent from post " + (mid == start ? start : mid) + " to post " + end);
    }
}
