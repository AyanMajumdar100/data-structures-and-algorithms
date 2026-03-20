/*
Problem: 3567. Minimum Absolute Difference in Sliding Submatrix

Goal:
For every k x k submatrix → find minimum absolute difference between any two DISTINCT values.

--------------------------------------------------

DETAILED LOGIC (Step-by-step thinking):

1. Sliding Window over Matrix:
   - We treat every (i, j) as the top-left corner of a k x k submatrix
   - Total such submatrices = (m - k + 1) * (n - k + 1)

2. Extract Elements:
   - For each submatrix, we collect all k*k elements into a 1D array "vals"
   - Why 1D? → easier to sort and compare

3. Sorting Insight (IMPORTANT):
   - After sorting:
        Closest numbers will always be adjacent
   - So instead of checking all pairs (O(n^2)),
     we just check neighbors → O(n)

4. Finding Minimum Difference:
   - Traverse sorted array
   - For every adjacent pair:
        diff = vals[x] - vals[x-1]
   - Keep track of minimum difference

5. Handling Edge Case:
   - If ALL elements are same:
        → no distinct pair exists
        → answer must be 0
   - We track this using "allSame" flag

--------------------------------------------------

Time Complexity:
O((m-k+1)*(n-k+1) * k^2 log(k^2))

Why acceptable?
- Max grid size = 30 → small constraints → brute force works

Space Complexity:
O(k^2) for temporary array
*/

import java.util.*;

class MinDiffK {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input dimensions of grid
        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Taking grid input
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Size of submatrix
        int k = sc.nextInt();

        int[][] result = minAbsDiff(grid, k);

        // Printing result matrix
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        sc.close(); // always close scanner
    }

    public static int[][] minAbsDiff(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        int[][] ans = new int[m - k + 1][n - k + 1];

        // Temporary array to hold k*k elements of submatrix
        int[] vals = new int[k * k];

        // Traverse all possible starting points of k x k submatrix
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {

                int idx = 0;

                // Step 1: Extract elements into vals[]
                for (int r = i; r < i + k; r++) {
                    for (int c = j; c < j + k; c++) {
                        vals[idx++] = grid[r][c];
                    }
                }

                // Step 2: Sort the extracted values
                Arrays.sort(vals);

                int minDiff = Integer.MAX_VALUE;
                boolean allSame = true;

                // Step 3: Compare adjacent elements
                for (int x = 1; x < vals.length; x++) {

                    // If values differ → we found distinct elements
                    if (vals[x] != vals[x - 1]) {
                        allSame = false;

                        // Since sorted → this is smallest possible difference locally
                        minDiff = Math.min(minDiff, vals[x] - vals[x - 1]);
                    }
                }

                // Step 4: Final decision
                if (allSame) {
                    // All elements identical → no distinct pair
                    ans[i][j] = 0;
                } else {
                    ans[i][j] = minDiff;
                }
            }
        }

        return ans;
    }
}