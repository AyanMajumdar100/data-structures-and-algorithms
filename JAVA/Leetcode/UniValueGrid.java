/*
Problem Statement:
You are given a 2D integer grid of size m x n and an integer x. In one operation, you can 
add x to or subtract x from any element in the grid.
A uni-value grid is a grid where all the elements of it are equal.

Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 10^5
1 <= m * n <= 10^5
1 <= x, grid[i][j] <= 10^4
*/

import java.util.Arrays;
import java.util.Scanner;

public class UniValueGrid {

    public static int minOperations(int[][] grid, int x) {
        int m = grid.length;
        int n = grid[0].length;
        int[] arr = new int[m * n];
        int idx = 0;
        
        // The remainder when divided by x must be identical for ALL elements.
        // If not, it is mathematically impossible to make them equal.
        int rem = grid[0][0] % x;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Optimization: Check the modulo condition during the initial scan
                if (grid[i][j] % x != rem) {
                    return -1; 
                }
                arr[idx++] = grid[i][j];
            }
        }
        
        // Sort the array to find the median
        Arrays.sort(arr);
        
        // Why the median? Moving elements to the median guarantees the minimum 
        // total distance traveled (or operations used) across all points.
        int median = arr[arr.length / 2];
        int ops = 0;
        
        // Calculate the required operations for each element
        for (int i = 0; i < arr.length; i++) {
            ops += Math.abs(arr[i] - median) / x;
        }
        
        return ops;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of rows and columns (e.g., '2 2'):");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            int[][] grid = new int[m][n];
            System.out.println("Enter the grid rows (numbers separated by spaces):");
            for (int i = 0; i < m; i++) {
                String[] rowStrs = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(rowStrs[j]);
                }
            }
            
            System.out.println("Enter the value of x:");
            int x = scanner.nextInt();
            
            int result = minOperations(grid, x);
            System.out.println("Minimum Operations: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}
