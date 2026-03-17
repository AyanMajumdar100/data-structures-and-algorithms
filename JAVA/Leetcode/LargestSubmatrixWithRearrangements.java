/*
1727. Largest Submatrix With Rearrangements

Problem Statement:
You are given a binary matrix (0s and 1s).

You are allowed to rearrange the columns in ANY order.

Goal:
Find the largest possible submatrix consisting entirely of 1s
(after rearranging columns optimally).

Return the maximum area.

------------------------------------------------------------

Core Intuition:

Instead of actually rearranging columns globally,
we treat EACH ROW independently.

Key observation:
For any row, if we know the "height" of consecutive 1s above it,
we can rearrange columns in that row to maximize area.

------------------------------------------------------------

Step-by-step Logic:

1. Convert matrix into heights:
   - If matrix[i][j] == 1:
       height = 1 + height from previous row
   - Else:
       height = 0

   This effectively turns each row into a histogram.

2. For each row:
   - Copy the row into a temporary array
   - Sort it in ascending order

3. Why sorting works:
   - Sorting simulates rearranging columns
   - Larger heights grouped together give larger rectangles

4. Calculate area:
   For each position j:
   - height = currRow[j]
   - width = number of columns from j → end = (n - j)
   - area = height * width

5. Track maximum area.

------------------------------------------------------------

Example:
Row heights after processing:
[3,1,2]

Sorted:
[1,2,3]

Now:
1 * 3 = 3
2 * 2 = 4
3 * 1 = 3

Max = 4

------------------------------------------------------------

Time Complexity:
O(m * n log n)

Space Complexity:
O(n)
*/

import java.util.Arrays;
import java.util.Scanner;

public class LargestSubmatrixWithRearrangements {

    public static int largestSubmatrix(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        int maxArea = 0;

        for (int i = 0; i < m; i++) {

            // Step 1: Build heights (like histogram)
            for (int j = 0; j < n; j++) {

                /*
                If current cell is 1:
                Add height from previous row to build vertical streak
                */
                if (matrix[i][j] != 0 && i > 0) {
                    matrix[i][j] += matrix[i - 1][j];
                }
            }

            /*
            Step 2: Copy current row
            We don't want to destroy original height structure
            */
            int[] currRow = matrix[i].clone();

            /*
            Step 3: Sort heights
            This simulates rearranging columns optimally
            */
            Arrays.sort(currRow);

            /*
            Step 4: Calculate max possible area
            Try each height as the minimum height of submatrix
            */
            for (int j = 0; j < n; j++) {

                int height = currRow[j];

                /*
                Width = number of columns we can use
                Since sorted ascending:
                columns from j → n-1 all have height >= currRow[j]
                */
                int width = n - j;

                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter rows: ");
        int m = sc.nextInt();

        System.out.print("Enter columns: ");
        int n = sc.nextInt();

        int[][] matrix = new int[m][n];

        System.out.println("Enter matrix values (0 or 1):");

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        int result = largestSubmatrix(matrix);

        System.out.println("Largest Submatrix Area: " + result);

        sc.close();
    }
}