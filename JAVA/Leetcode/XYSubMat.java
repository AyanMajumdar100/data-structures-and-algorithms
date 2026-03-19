/*
Problem: 3212. Count Submatrices With Equal Frequency of X and Y

You are given a 2D character matrix grid containing 'X', 'Y', or '.'.

Return the number of submatrices that:
1. MUST include the top-left element (0,0)
2. Have equal number of 'X' and 'Y'
3. Have at least one 'X'

--------------------------------------------------

Approach:
- Since submatrix must include (0,0), each valid submatrix is defined by bottom-right corner (i, j)
- So we only check rectangles from (0,0) → (i,j)

- Instead of full prefix matrix, we optimize using:
    prevX[j] → total X count from (0,0) to (i,j)
    prevY[j] → total Y count from (0,0) to (i,j)

- We accumulate row-wise counts:
    currRowX → number of X in current row till column j
    currRowY → number of Y in current row till column j

- Then build prefix vertically:
    prevX[j] += currRowX
    prevY[j] += currRowY

- Condition:
    if prevX[j] == prevY[j] AND prevX[j] > 0 → valid

Time Complexity: O(m * n)
Space Complexity: O(n)
*/

import java.util.*;

class XYSubMat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input dimensions
        int m = sc.nextInt();
        int n = sc.nextInt();

        char[][] grid = new char[m][n];

        // Input grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.next().charAt(0);
            }
        }

        int result = numberOfSubmatrices(grid);
        System.out.println(result);

        sc.close(); // always close scanner
    }

    public static int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int count = 0;

        // Stores prefix counts column-wise
        int[] prevX = new int[n];
        int[] prevY = new int[n];

        for (int i = 0; i < m; i++) {

            int currRowX = 0; // running X count in current row
            int currRowY = 0; // running Y count in current row

            for (int j = 0; j < n; j++) {

                // Update row counts
                if (grid[i][j] == 'X') {
                    currRowX++;
                } else if (grid[i][j] == 'Y') {
                    currRowY++;
                }

                // Add to vertical prefix
                prevX[j] += currRowX;
                prevY[j] += currRowY;

                /*
                 Check:
                 1. Equal X and Y
                 2. At least one X (avoid empty or only Y cases)
                */
                if (prevX[j] > 0 && prevX[j] == prevY[j]) {
                    count++;
                }
            }
        }

        return count;
    }
}