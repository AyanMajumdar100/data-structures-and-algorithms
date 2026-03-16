/*
1878. Get Biggest Three Rhombus Sums in a Grid

Problem Statement:
You are given an m x n integer matrix grid.

A rhombus sum is defined as the sum of elements on the border of a rhombus
(a square rotated 45°). The rhombus must have its four corners centered
on grid cells.

Important:
A rhombus of size 0 (single cell) is also valid.

Goal:
Return the biggest three DISTINCT rhombus sums in descending order.
If fewer than 3 distinct sums exist, return all of them.

Example 1
Input:
grid = [
[3,4,5,1,3],
[3,3,4,2,3],
[20,30,200,40,10],
[1,5,5,4,1],
[4,3,2,2,5]
]

Output:
[228,216,211]

Example 2
Input:
grid = [[1,2,3],[4,5,6],[7,8,9]]

Output:
[20,9,8]

Example 3
Input:
grid = [[7,7,7]]

Output:
[7]

Constraints:
1 <= m, n <= 50
1 <= grid[i][j] <= 10^5

Approach:
1. Use a TreeSet to store the top 3 distinct sums.
2. For each cell treat it as the top corner of a rhombus.
3. Expand rhombus layer by layer.
4. Calculate border sums for all 4 edges.
5. Maintain only the top 3 sums.

Time Complexity:
O(m * n * min(m,n)^2)

Space Complexity:
O(1)
*/

import java.util.*;

public class GetBiggestThreeRhombusSums {

    public static int[] getBiggestThree(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Single cell rhombus (size 0)
                set.add(grid[i][j]);

                if (set.size() > 3) {
                    set.pollFirst();
                }

                // Try rhombus sizes
                for (int L = 1; i + 2 * L < m && j - L >= 0 && j + L < n; L++) {

                    int sum = 0;

                    // Top -> Right
                    for (int k = 0; k < L; k++) {
                        sum += grid[i + k][j + k];
                    }

                    // Right -> Bottom
                    for (int k = 0; k < L; k++) {
                        sum += grid[i + L + k][j + L - k];
                    }

                    // Bottom -> Left
                    for (int k = 0; k < L; k++) {
                        sum += grid[i + 2 * L - k][j - k];
                    }

                    // Left -> Top
                    for (int k = 0; k < L; k++) {
                        sum += grid[i + L - k][j - L + k];
                    }

                    set.add(sum);

                    if (set.size() > 3) {
                        set.pollFirst();
                    }
                }
            }
        }

        int[] res = new int[set.size()];

        int idx = set.size() - 1;

        for (int val : set) {
            res[idx--] = val;
        }

        return res;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter rows: ");
        int m = sc.nextInt();

        System.out.print("Enter columns: ");
        int n = sc.nextInt();

        int[][] grid = new int[m][n];

        System.out.println("Enter grid values:");

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        int[] result = getBiggestThree(grid);

        System.out.println("Top Rhombus Sums:");

        for (int val : result) {
            System.out.print(val + " ");
        }

        sc.close();
    }
}
