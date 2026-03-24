/*
Problem: 2906. Construct Product Matrix

Goal:
For each cell (i, j), compute:
→ product of ALL elements in grid EXCEPT grid[i][j]
→ take modulo 12345

--------------------------------------------------

⚠️ Constraint Challenge:
- n * m can be up to 1e5
- Values up to 1e9

So:
❌ Cannot use brute force (O(n^2))
❌ Cannot use division (as per hint)

--------------------------------------------------

💡 CORE IDEA (Same as "Product of Array Except Self"):

We use:
1. Prefix Product (before current element)
2. Suffix Product (after current element)

Instead of division, we multiply:
prefix * suffix

--------------------------------------------------

🧠 Key Trick:
Treat the 2D matrix as a "flattened array"

Traversal order:
→ Row-wise (left to right, top to bottom)

--------------------------------------------------

STEP 1: PREFIX PASS

- pref = product of all elements BEFORE current index

For each cell:
    p[i][j] = pref
    pref *= grid[i][j]

So:
p[i][j] stores product of everything BEFORE it

--------------------------------------------------

STEP 2: SUFFIX PASS

- suff = product of all elements AFTER current index

Traverse from end:
    p[i][j] *= suff
    suff *= grid[i][j]

Now:
p[i][j] = prefix * suffix
        = product of all elements except itself

--------------------------------------------------

MODULO HANDLING:
- Apply % 12345 at every multiplication to avoid overflow

--------------------------------------------------

Time Complexity: O(n * m)
Space Complexity: O(1) extra (excluding output)
*/

import java.util.*;

class ProdMat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] grid = new int[n][m];

        // Input grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int[][] result = constructProductMatrix(grid);

        // Print result
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        sc.close();
    }

    public static int[][] constructProductMatrix(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        int[][] p = new int[n][m];

        long pref = 1;

        // STEP 1: Prefix pass (left to right, top to bottom)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Store product of all previous elements
                p[i][j] = (int) pref;

                // Update prefix product
                pref = (pref * (grid[i][j] % 12345)) % 12345;
            }
        }

        long suff = 1;

        // STEP 2: Suffix pass (reverse order)
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {

                // Multiply with suffix (elements after current)
                p[i][j] = (int) ((p[i][j] * suff) % 12345);

                // Update suffix product
                suff = (suff * (grid[i][j] % 12345)) % 12345;
            }
        }

        return p;
    }
}