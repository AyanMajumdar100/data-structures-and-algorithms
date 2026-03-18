/*
Problem: 3070. Count Submatrices with Top-Left Element and Sum Less Than k

You are given a 0-indexed integer matrix grid and an integer k.

Return the number of submatrices that:
1. Contain the top-left element (0,0)
2. Have a sum less than or equal to k

Approach:
- Since every valid submatrix must start from (0,0), each submatrix is defined by its bottom-right corner (i, j)
- We use Prefix Sum (2D) to compute sum from (0,0) to (i,j)
- Formula:
    sum(i,j) = grid[i][j]
               + top
               + left
               - top-left overlap

- If sum <= k → valid submatrix
- If sum exceeds k in a row, we break (because values only increase further)

Time Complexity: O(m * n)
Space Complexity: O(1) (in-place prefix sum)
*/

import java.util.*;

class SubMatK {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Taking matrix dimensions
        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Input matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Input k
        int k = sc.nextInt();

        int result = countSubmatrices(grid, k);
        System.out.println(result);

        sc.close(); // good practice
    }

    public static int countSubmatrices(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int count = 0;

        // Traverse each cell as bottom-right of submatrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Start with current cell value
                int sum = grid[i][j];

                // Add top part
                if (i > 0) sum += grid[i - 1][j];

                // Add left part
                if (j > 0) sum += grid[i][j - 1];

                // Remove double counted top-left
                if (i > 0 && j > 0) sum -= grid[i - 1][j - 1];

                // Store prefix sum back into grid (in-place)
                grid[i][j] = sum;

                // Check condition
                if (sum <= k) {
                    count++;
                } else {
                    // Important optimization:
                    // If sum exceeds k, further elements in this row will only increase
                    break;
                }
            }
        }

        return count;
    }
}