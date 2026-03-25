/*
Problem: 3546. Equal Sum Grid Partition I

Goal:
Check if we can make:
→ ONE horizontal cut OR ONE vertical cut

Such that:
- Both parts are NON-EMPTY
- Both parts have EQUAL SUM

--------------------------------------------------

💡 CORE IDEA:

We are trying to split the grid into TWO parts with equal sum.

So first:
👉 Total sum must be EVEN
If odd → impossible → return false

--------------------------------------------------

STEP 1: Precompute sums

We calculate:
- rowSums[i] → sum of each row
- colSums[j] → sum of each column
- totalSum → sum of entire grid

--------------------------------------------------

STEP 2: Check possibility

We need:
each partition = totalSum / 2

--------------------------------------------------

STEP 3: Try HORIZONTAL cuts

Cut after row i:
Top part = rows [0 → i]
Bottom part = rows [i+1 → m-1]

We keep adding row sums:
currentSum += rowSums[i]

If currentSum == target:
→ valid partition found

NOTE:
i < m-1 ensures bottom part is non-empty

--------------------------------------------------

STEP 4: Try VERTICAL cuts

Cut after column j:
Left part = columns [0 → j]
Right part = columns [j+1 → n-1]

Same logic:
currentSum += colSums[j]

If currentSum == target:
→ valid partition found

NOTE:
j < n-1 ensures right part is non-empty

--------------------------------------------------

STEP 5: If no cut works → return false

--------------------------------------------------

Time Complexity: O(m * n)
Space Complexity: O(m + n)
*/

import java.util.*;

class EqualGridPartition {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Input grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.println(canPartitionGrid(grid));

        sc.close();
    }

    public static boolean canPartitionGrid(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        long[] rowSums = new long[m];
        long[] colSums = new long[n];
        long totalSum = 0;

        // STEP 1: Compute row sums, column sums, and total sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSums[i] += grid[i][j];
                colSums[j] += grid[i][j];
                totalSum += grid[i][j];
            }
        }

        // STEP 2: If total sum is odd → cannot split equally
        if (totalSum % 2 != 0) {
            return false;
        }

        long target = totalSum / 2;

        long currentSum = 0;

        // STEP 3: Try horizontal cuts
        for (int i = 0; i < m - 1; i++) {
            currentSum += rowSums[i];

            if (currentSum == target) {
                return true;
            }
        }

        currentSum = 0;

        // STEP 4: Try vertical cuts
        for (int j = 0; j < n - 1; j++) {
            currentSum += colSums[j];

            if (currentSum == target) {
                return true;
            }
        }

        // STEP 5: No valid partition found
        return false;
    }
}