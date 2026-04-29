/*
Problem Statement:
You are given a 2D matrix grid of size n x n. Initially, all cells are colored white. 
In one operation, you can select any cell (i, j), and color black all cells of the j-th column 
from the top row down to the i-th row.
The grid score is the sum of all white cells that have a horizontally adjacent black cell.
Return the maximum score that can be achieved after some number of operations.

Constraints:
1 <= n == grid.length <= 100
0 <= grid[i][j] <= 10^9
*/

import java.util.Scanner;

public class GridMaxScore {

    public static long maximumScore(int[][] grid) {
        int n = grid.length;
        
        // Prefix sum of columns to quickly calculate the sum of segments in O(1) time
        long[][] prefixSum = new long[n + 1][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                prefixSum[i + 1][j] = prefixSum[i][j] + grid[i][j];
            }
        }
        
        // dp[L][C] represents the max score up to the current column, where:
        // L = height of the black cells in column j-2
        // C = height of the black cells in column j-1
        long[][] dp = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        
        // Base case: before processing column 0, the imaginary previous columns have height 0
        for (int C = 0; C <= n; C++) {
            dp[0][C] = 0;
        }
        
        // Process each column one by one
        for (int j = 0; j < n; j++) {
            long[][] nextDp = new long[n + 1][n + 1];
            for (int i = 0; i <= n; i++) {
                for (int k = 0; k <= n; k++) {
                    nextDp[i][k] = -1;
                }
            }
            
            // For a fixed height 'C' in column j-1
            for (int C = 0; C <= n; C++) {
                
                // pref[L] maintains the maximum score if the height L (col j-2) <= current height R (col j)
                long[] pref = new long[n + 1];
                long maxSoFar = -1;
                for (int L = 0; L <= n; L++) {
                    if (dp[L][C] != -1) {
                        maxSoFar = Math.max(maxSoFar, dp[L][C]);
                    }
                    pref[L] = maxSoFar;
                }
                
                // suff[L] maintains the maximum score if the height L (col j-2) > current height R (col j).
                // If L > C, the white cells in col j-1 from C to L get score from the black cells in col j-2.
                long[] suff = new long[n + 2];
                suff[n + 1] = -1;
                maxSoFar = -1;
                for (int L = n; L >= 0; L--) {
                    if (dp[L][C] != -1) {
                        long val = dp[L][C] + getSegmentSum(prefixSum, j, C, L);
                        maxSoFar = Math.max(maxSoFar, val);
                    }
                    suff[L] = maxSoFar;
                }
                
                // Determine the best transition for height 'R' in the current column j
                for (int R = 0; R <= n; R++) {
                    long best = -1;
                    
                    // Case 1: L <= R. The score comes from col j providing black neighbors to col j-1
                    if (pref[R] != -1) {
                        best = Math.max(best, pref[R] + getSegmentSum(prefixSum, j, C, R));
                    }
                    
                    // Case 2: L > R. The score was already provided by col j-2
                    if (R + 1 <= n && suff[R + 1] != -1) {
                        best = Math.max(best, suff[R + 1]);
                    }
                    
                    nextDp[C][R] = best;
                }
            }
            // Move to the next column
            dp = nextDp;
        }
        
        // Find the absolute maximum score in the final state where column n has height 0
        long ans = 0;
        for (int C = 0; C <= n; C++) {
            ans = Math.max(ans, dp[C][0]);
        }
        
        return ans;
    }
    
    // Helper to get sum of grid cells in column j from row 'a' to row 'b' (exclusive of b)
    private static long getSegmentSum(long[][] prefixSum, int j, int a, int b) {
        if (a >= b) return 0;
        return prefixSum[b][j] - prefixSum[a][j];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter grid size n:");
            int n = Integer.parseInt(scanner.nextLine().trim());
            
            int[][] grid = new int[n][n];
            System.out.println("Enter the grid rows (numbers separated by spaces):");
            for (int i = 0; i < n; i++) {
                String[] row = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    grid[i][j] = Integer.parseInt(row[j]);
                }
            }
            
            long result = maximumScore(grid);
            System.out.println("Maximum Score: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please ensure you enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}
