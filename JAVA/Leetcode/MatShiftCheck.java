/*
Problem: 2946. Matrix Similarity After Cyclic Shifts

--------------------------------------------------

GOAL:

After performing k cyclic shifts:
- Even rows → LEFT shift
- Odd rows → RIGHT shift

Check:
👉 Does the matrix become SAME as original?

--------------------------------------------------

💡 KEY OBSERVATION:

After n shifts (where n = number of columns),
the row returns to original position.

So:
👉 k = k % n

This avoids unnecessary rotations.

--------------------------------------------------

🧠 CORE IDEA:

Instead of actually shifting the matrix,
we directly CHECK where each element would go.

--------------------------------------------------

FOR EACH CELL (i, j):

Case 1: EVEN ROW (i % 2 == 0)
→ Left shift by k

So:
Original element at j moves to (j - k)

To reverse check:
Compare:
mat[i][j] == mat[i][(j + k) % n]

--------------------------------------------------

Case 2: ODD ROW (i % 2 == 1)
→ Right shift by k

So:
Original element at j moves to (j + k)

To reverse check:
Compare:
mat[i][j] == mat[i][(j - k + n) % n]

(+n to avoid negative index)

--------------------------------------------------

If ANY mismatch → return false

Else → return true

--------------------------------------------------

Time Complexity: O(m * n)
Space Complexity: O(1)
*/

import java.util.*;

class MatShiftCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] mat = new int[m][n];

        // Input matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        int k = sc.nextInt();

        System.out.println(areSimilar(mat, k));

        sc.close();
    }

    public static boolean areSimilar(int[][] mat, int k) {

        int m = mat.length;
        int n = mat[0].length;

        // Reduce unnecessary rotations
        k = k % n;

        // Traverse each cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Even row → left shift
                if (i % 2 == 0) {
                    if (mat[i][j] != mat[i][(j + k) % n]) {
                        return false;
                    }
                }
                // Odd row → right shift
                else {
                    if (mat[i][j] != mat[i][(j - k + n) % n]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}