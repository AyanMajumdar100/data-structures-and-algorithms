/*
Problem: 3643. Flip Square Submatrix Vertically

You are given a matrix and a square submatrix defined by:
(x, y) → top-left corner
k → size of square

Task:
Flip this k x k submatrix vertically (i.e., reverse rows inside the square)

--------------------------------------------------

DETAILED LOGIC:

1. Understand "Vertical Flip":
   - We are NOT reversing columns
   - We are reversing rows within the selected square

   Example:
   Row1
   Row2
   Row3

   becomes:

   Row3
   Row2
   Row1

--------------------------------------------------

2. Two Pointer Technique:
   - Use two pointers:
        top → starts from x
        bottom → starts from x + k - 1

   - Swap rows at top and bottom
   - Move inward:
        top++
        bottom--

--------------------------------------------------

3. Swapping Logic:
   - We only swap within the square columns:
        from y → y + k - 1

   - For each column:
        swap(grid[top][col], grid[bottom][col])

--------------------------------------------------

4. Stop Condition:
   - When top >= bottom → all rows are swapped

--------------------------------------------------

Time Complexity:
O(k * k) → we visit each element in submatrix once

Space Complexity:
O(1) → in-place swapping
*/

import java.util.*;

class FlipSubMat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input matrix size
        int m = sc.nextInt();
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        // Input grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        // Input x, y, k
        int x = sc.nextInt();
        int y = sc.nextInt();
        int k = sc.nextInt();

        int[][] result = reverseSubmatrix(grid, x, y, k);

        // Print updated grid
        for (int[] row : result) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        sc.close(); // good practice
    }

    public static int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {

        // Pointer to top row of submatrix
        int top = x;

        // Pointer to bottom row of submatrix
        int bottom = x + k - 1;

        // Swap rows until pointers meet
        while (top < bottom) {

            // Swap elements column-wise within square boundaries
            for (int col = y; col < y + k; col++) {

                int temp = grid[top][col];
                grid[top][col] = grid[bottom][col];
                grid[bottom][col] = temp;
            }

            // Move pointers inward
            top++;
            bottom--;
        }

        return grid;
    }
}