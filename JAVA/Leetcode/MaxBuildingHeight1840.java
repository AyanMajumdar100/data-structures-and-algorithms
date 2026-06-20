/*
 * Problem Statement:
 * You want to build n new buildings in a city labeled from 1 to n.
 * - The height of each building must be a non-negative integer.
 * - The height of the first building must be 0.
 * - The height difference between any two adjacent buildings cannot exceed 1.
 * - You are given a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti].
 * Return the maximum possible height of the tallest building.
 */

/*
 * Approach: Two-Pass Optimization (Similar to Candy / Trapping Rain Water)
 * 1. Boundary Extensions: Add a pseudo-restriction at building 1 with height 0, and at building n 
 * with a theoretical max height of n - 1 (since it grows at most 1 per step from building 1).
 * 2. Sorting: Sort the restrictions based on building IDs so we can propagate limits linearly.
 * 3. Forward Pass: Update each building's max height constraint by checking how high it can go 
 * starting from its left neighbor: r[i][1] = min(r[i][1], r[i-1][1] + distance).
 * 4. Backward Pass: Pass backwards to propagate constraints from right neighbors back to left: 
 * r[i][1] = min(r[i][1], r[i+1][1] + distance).
 * 5. Peak Maximization: Between any two restricted buildings, the height can grow to a peak 
 * and drop down. The theoretical peak height between two points (id1, h1) and (id2, h2) is 
 * given by the math formula: floor((h1 + h2 + (id2 - id1)) / 2). Track the maximum peak found.
 */
import java.util.Arrays;
import java.util.Scanner;

public class MaxBuildingHeight1840 {
    public int maxBuilding(int n, int[][] restrictions) {
        int m = restrictions.length;
        // Create an extended array to include building 1 and building n as base boundaries
        int[][] r = new int[m + 2][2];
        
        for (int i = 0; i < m; i++) {
            r[i] = restrictions[i];
        }
        // Base constraint: building 1 must start at height 0
        r[m] = new int[]{1, 0};
        // End constraint: building n can at most reach height n - 1
        r[m + 1] = new int[]{n, n - 1};
        
        // Sort restrictions chronologically by building ID
        Arrays.sort(r, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Pass 1: Propagate constraints from left to right
        for (int i = 1; i < r.length; i++) {
            r[i][1] = Math.min(r[i][1], r[i - 1][1] + (r[i][0] - r[i - 1][0]));
        }
        
        // Pass 2: Propagate constraints from right to left
        for (int i = r.length - 2; i >= 0; i--) {
            r[i][1] = Math.min(r[i][1], r[i + 1][1] + (r[i + 1][0] - r[i][0]));
        }
        
        int maxHeight = 0;
        // Calculate maximum peak heights between adjacent restricted zones
        for (int i = 0; i < r.length - 1; i++) {
            int id1 = r[i][0], h1 = r[i][1];
            int id2 = r[i + 1][0], h2 = r[i + 1][1];
            
            // Intersection peak math formula: peak = ((h1 + h2) + distance) / 2
            int peak = (int) (((long) h1 + h2 + (id2 - id1)) / 2);
            maxHeight = Math.max(maxHeight, peak);
        }
        
        return maxHeight;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter total number of buildings n:");
        int n = scanner.nextInt();
        
        System.out.println("Enter number of restrictions:");
        int m = scanner.nextInt();
        int[][] restrictions = new int[m][2];
        
        System.out.println("Enter restrictions (id height) line by line:");
        for (int i = 0; i < m; i++) {
            restrictions[i][0] = scanner.nextInt();
            restrictions[i][1] = scanner.nextInt();
        }
        
        MaxBuildingHeight1840 solver = new MaxBuildingHeight1840();
        int result = solver.maxBuilding(n, restrictions);
        System.out.println("Maximum possible building height: " + result);
        
        scanner.close();
    }
}