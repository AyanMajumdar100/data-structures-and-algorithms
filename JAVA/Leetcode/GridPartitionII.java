/*
Problem: 3548. Equal Sum Grid Partition II

--------------------------------------------------

GOAL:

Make ONE cut (horizontal OR vertical) such that:
1. Both parts are non-empty
2. Their sums are equal
   OR can be made equal by removing AT MOST ONE cell
3. After removing that cell → section must remain CONNECTED

--------------------------------------------------

⚠️ CORE CHALLENGE:

Unlike previous problem:
Now we can REMOVE one cell

So:
sum1 == sum2
OR
sum1 - x == sum2
OR
sum1 == sum2 - x

=> difference between sums must be removable by deleting ONE value

--------------------------------------------------

💡 MASTER IDEA:

For each possible cut:
1. Maintain:
   - sum1 (first section)
   - sum2 (second section)
   - frequency maps of both sections

2. Compute:
   diff = sum1 - sum2

--------------------------------------------------

CASE ANALYSIS:

If diff == 0:
→ Perfect split → return true

If diff > 0:
→ First section is larger
→ Need to remove diff from FIRST section

If diff < 0:
→ Second section is larger
→ Need to remove abs(diff) from SECOND section

--------------------------------------------------

⚠️ IMPORTANT: CONNECTIVITY RULE

Removing a cell must NOT break connectivity

Key observation:
- If section is >= 2x2 → removing ANY single cell is safe
- If section is 1D (row or column) → only edge removal is safe
- If single cell → only that cell can be removed

--------------------------------------------------

STEP 1: Precompute total sum + frequency

--------------------------------------------------

STEP 2: Try horizontal cuts

We move row by row:
- Move elements from bottom → top section
- Update freq1, freq2
- Update sums

--------------------------------------------------

STEP 3: Try vertical cuts (same logic)

--------------------------------------------------

STEP 4: checkRemovable()

Handles:
- Whether required value exists
- Whether removing it keeps section connected

--------------------------------------------------

Time Complexity: O(n * m)
Space Complexity: O(max value frequency)
*/

import java.util.*;

class GridPartitionII {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Input
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

        long totalSum = 0;

        // freqTotal stores count of all values in grid
        int[] freqTotal = new int[100005];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                totalSum += grid[i][j];
                freqTotal[grid[i][j]]++;
            }
        }

        // freq1 → first partition
        // freq2 → second partition
        int[] freq1 = new int[100005];
        int[] freq2 = new int[100005];

        System.arraycopy(freqTotal, 0, freq2, 0, 100005);

        long sum1 = 0;
        long sum2 = totalSum;

        // ---------------- HORIZONTAL CUT ----------------
        for (int r = 0; r < m - 1; r++) {

            // Move row r into top section
            for (int j = 0; j < n; j++) {
                int val = grid[r][j];

                freq1[val]++;
                freq2[val]--;

                sum1 += val;
                sum2 -= val;
            }

            long diff = sum1 - sum2;

            // Perfect split
            if (diff == 0) return true;

            int h1 = r + 1, w1 = n;
            int h2 = m - 1 - r, w2 = n;

            // Try removing from top section
            if (diff > 0 && diff <= 100000) {
                if (checkRemovable(diff, h1, w1, true, r, n, m, grid, freq1)) return true;
            }
            // Try removing from bottom section
            else if (diff < 0 && -diff <= 100000) {
                if (checkRemovable(-diff, h2, w2, false, r, n, m, grid, freq2)) return true;
            }
        }

        // Reset for vertical
        Arrays.fill(freq1, 0);
        System.arraycopy(freqTotal, 0, freq2, 0, 100005);

        sum1 = 0;
        sum2 = totalSum;

        // ---------------- VERTICAL CUT ----------------
        for (int c = 0; c < n - 1; c++) {

            for (int i = 0; i < m; i++) {
                int val = grid[i][c];

                freq1[val]++;
                freq2[val]--;

                sum1 += val;
                sum2 -= val;
            }

            long diff = sum1 - sum2;

            if (diff == 0) return true;

            int h1 = m, w1 = c + 1;
            int h2 = m, w2 = n - 1 - c;

            if (diff > 0 && diff <= 100000) {
                if (checkRemovableVert(diff, h1, w1, true, c, n, m, grid, freq1)) return true;
            }
            else if (diff < 0 && -diff <= 100000) {
                if (checkRemovableVert(-diff, h2, w2, false, c, n, m, grid, freq2)) return true;
            }
        }

        return false;
    }

    // ----------- HORIZONTAL REMOVAL CHECK -----------

    private static boolean checkRemovable(long val, int h, int w, boolean isTop,
                                          int r, int n, int m, int[][] grid, int[] freq) {

        // If section is 2D → any removal is safe
        if (h > 1 && w > 1) {
            return freq[(int) val] > 0;
        }

        // Single cell
        if (h == 1 && w == 1) {
            return isTop ? grid[0][0] == val : grid[m - 1][0] == val;
        }

        // Single row
        if (h == 1) {
            return isTop
                    ? (grid[0][0] == val || grid[0][n - 1] == val)
                    : (grid[m - 1][0] == val || grid[m - 1][n - 1] == val);
        }

        // Single column
        if (w == 1) {
            return isTop
                    ? (grid[0][0] == val || grid[r][0] == val)
                    : (grid[r + 1][0] == val || grid[m - 1][0] == val);
        }

        return false;
    }

    // ----------- VERTICAL REMOVAL CHECK -----------

    private static boolean checkRemovableVert(long val, int h, int w, boolean isLeft,
                                              int c, int n, int m, int[][] grid, int[] freq) {

        if (h > 1 && w > 1) {
            return freq[(int) val] > 0;
        }

        if (h == 1 && w == 1) {
            return isLeft ? grid[0][0] == val : grid[0][n - 1] == val;
        }

        if (h == 1) {
            return isLeft
                    ? (grid[0][0] == val || grid[0][c] == val)
                    : (grid[0][c + 1] == val || grid[0][n - 1] == val);
        }

        if (w == 1) {
            return isLeft
                    ? (grid[0][0] == val || grid[m - 1][0] == val)
                    : (grid[0][n - 1] == val || grid[m - 1][n - 1] == val);
        }

        return false;
    }
}
