/*
Problem: Minimum Swaps to Arrange a Binary Grid

You are given an n x n binary grid.
In one step, you may swap two adjacent rows.

A grid is valid if every cell above the main diagonal is 0.
Return the minimum number of swaps required, or -1 if impossible.

Example:
Input: [[0,0,1],[1,1,0],[1,0,0]]
Output: 3

Input: [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
Output: -1

Input: [[1,0,0],[1,1,0],[1,1,1]]
Output: 0
*/

import java.util.*;

class BinaryGridMinimumSwaps {

    /*
    Beginner-friendly idea:

    For row i to be valid,
    it must have at least (n - 1 - i) trailing zeros.

    Steps:
    1. Count trailing zeros in each row.
    2. For each position i, find the nearest row below it
       that has enough trailing zeros.
    3. Bring that row upward using adjacent swaps
       (like bubble sort).
    */
    public static int minSwaps(int[][] grid) {
        int n = grid.length;
        int[] zeros = new int[n];

        // Count trailing zeros for each row
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 0) {
                    count++;
                } else {
                    break;
                }
            }
            zeros[i] = count;
        }

        int swaps = 0;

        // Place correct row at each position
        for (int i = 0; i < n; i++) {
            int requiredZeros = n - 1 - i;
            int j = i;

            // Find first row with enough trailing zeros
            while (j < n && zeros[j] < requiredZeros) {
                j++;
            }

            // No valid row found
            if (j == n) {
                return -1;
            }

            // Bubble row upward using adjacent swaps
            while (j > i) {
                int temp = zeros[j];
                zeros[j] = zeros[j - 1];
                zeros[j - 1] = temp;
                swaps++;
                j--;
            }
        }

        return swaps;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input: n
        int n = scanner.nextInt();

        int[][] grid = new int[n][n];

        // Input grid row by row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        int result = minSwaps(grid);
        System.out.println(result);
        scanner.close();
    }
}