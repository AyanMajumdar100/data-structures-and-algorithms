/*
Problem Statement:
You are given an m x n integer matrix grid, where m and n are both even integers, and an integer k.
A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. 
To cyclically rotate a layer once, each element in the layer will take the place of the 
adjacent element in the counter-clockwise direction.
Return the matrix after applying k cyclic rotations to it.

Constraints:
m == grid.length
n == grid[i].length
2 <= m, n <= 50
Both m and n are even integers.
1 <= grid[i][j] <= 5000
1 <= k <= 10^9
*/

import java.util.Scanner;

public class CyclicallyRotateGrid {

    public static int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        
        // Since both m and n are even, the number of layers is half of the smaller dimension
        int numLayers = Math.min(m, n) / 2;
        
        for (int layer = 0; layer < numLayers; layer++) {
            // Define the boundaries for the current concentric layer
            int r1 = layer;
            int c1 = layer;
            int r2 = m - 1 - layer;
            int c2 = n - 1 - layer;
            
            // The perimeter size of the current layer
            int size = 2 * (r2 - r1) + 2 * (c2 - c1);
            int[] arr = new int[size];
            int idx = 0;
            
            // Extract the layer into a 1D array in Counter-Clockwise order:
            // 1. Left column (moving down)
            for (int i = r1; i < r2; i++) arr[idx++] = grid[i][c1];
            // 2. Bottom row (moving right)
            for (int j = c1; j < c2; j++) arr[idx++] = grid[r2][j];
            // 3. Right column (moving up)
            for (int i = r2; i > r1; i--) arr[idx++] = grid[i][c2];
            // 4. Top row (moving left)
            for (int j = c2; j > c1; j--) arr[idx++] = grid[r1][j];
            
            // Calculate effective rotations to avoid redundant cycles
            int rot = k % size;
            idx = 0;
            
            // Write the shifted elements back into the grid using the same CCW path
            // The new value at `idx` comes from `rot` steps behind it in the flattened array
            for (int i = r1; i < r2; i++) {
                grid[i][c1] = arr[(idx - rot + size) % size];
                idx++;
            }
            for (int j = c1; j < c2; j++) {
                grid[r2][j] = arr[(idx - rot + size) % size];
                idx++;
            }
            for (int i = r2; i > r1; i--) {
                grid[i][c2] = arr[(idx - rot + size) % size];
                idx++;
            }
            for (int j = c2; j > c1; j--) {
                grid[r1][j] = arr[(idx - rot + size) % size];
                idx++;
            }
        }
        
        return grid;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of rows (m) and columns (n) - both must be EVEN:");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            if (m % 2 != 0 || n % 2 != 0) {
                System.out.println("Both dimensions must be even integers.");
                return;
            }
            
            int[][] grid = new int[m][n];
            System.out.println("Enter the grid rows (numbers separated by spaces):");
            for (int i = 0; i < m; i++) {
                String[] rowStrs = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(rowStrs[j]);
                }
            }
            
            System.out.println("Enter the number of rotations (k):");
            int k = Integer.parseInt(scanner.nextLine().trim());
            
            int[][] result = rotateGrid(grid, k);
            
            System.out.println("\nRotated Grid:");
            for (int i = 0; i < m; i++) {
                System.out.print("[");
                for (int j = 0; j < n; j++) {
                    System.out.print(result[i][j] + (j == n - 1 ? "" : ", "));
                }
                System.out.println("]");
            }
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}