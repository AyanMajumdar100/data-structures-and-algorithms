/*
Problem: 1886. Determine Whether Matrix Can Be Obtained By Rotation

Given:
- Two n x n binary matrices: mat and target

Task:
- Check if mat can become target by rotating it:
    0°, 90°, 180°, or 270°

--------------------------------------------------

DETAILED LOGIC:

Instead of physically rotating the matrix multiple times,
we directly COMPARE positions using index transformation.

--------------------------------------------------

1. Rotation Mapping (VERY IMPORTANT):

For a cell (i, j) in mat:

- 0° rotation:
    stays same → (i, j)

- 90° rotation:
    becomes → (j, n-1-i)

- 180° rotation:
    becomes → (n-1-i, n-1-j)

- 270° rotation:
    becomes → (n-1-j, i)

--------------------------------------------------

2. Strategy:

- Assume all rotations are possible initially:
    rot0, rot90, rot180, rot270 = true

- Traverse entire matrix:
    For each (i, j):
        Compare mat[i][j] with corresponding rotated positions in target

- If mismatch found → mark that rotation as false

--------------------------------------------------

3. Final Decision:

- If ANY rotation is valid → return true
- Else → false

--------------------------------------------------

Why this is efficient?
- No actual rotation needed
- Single traversal → O(n^2)

--------------------------------------------------

Time Complexity:
O(n^2)

Space Complexity:
O(1)
*/

import java.util.*;

class MatRotateCheck {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[][] mat = new int[n][n];
        int[][] target = new int[n][n];

        // Input mat
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        // Input target
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                target[i][j] = sc.nextInt();
            }
        }

        System.out.println(findRotation(mat, target));

        sc.close();
    }

    public static boolean findRotation(int[][] mat, int[][] target) {

        int n = mat.length;

        // Assume all rotations are possible initially
        boolean rot0 = true;
        boolean rot90 = true;
        boolean rot180 = true;
        boolean rot270 = true;

        // Traverse every cell once
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // 0° rotation (no change)
                if (mat[i][j] != target[i][j]) {
                    rot0 = false;
                }

                // 90° rotation
                if (mat[i][j] != target[j][n - 1 - i]) {
                    rot90 = false;
                }

                // 180° rotation
                if (mat[i][j] != target[n - 1 - i][n - 1 - j]) {
                    rot180 = false;
                }

                // 270° rotation
                if (mat[i][j] != target[n - 1 - j][i]) {
                    rot270 = false;
                }
            }
        }

        // If any rotation matches → true
        return rot0 || rot90 || rot180 || rot270;
    }
}