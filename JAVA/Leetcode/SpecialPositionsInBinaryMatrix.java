/*
1582. Special Positions in a Binary Matrix

Problem Statement:
Given an m x n binary matrix mat, return the number of special positions in mat.

A position (i, j) is called special if:
1. mat[i][j] == 1
2. All other elements in row i are 0
3. All other elements in column j are 0

Note:
- Rows and columns are 0-indexed.
- m == mat.length
- n == mat[i].length
- 1 <= m, n <= 100
- mat[i][j] is either 0 or 1

Example 1:
Input:
3 3
1 0 0
0 0 1
1 0 0
Output: 1

Example 2:
Input:
3 3
1 0 0
0 1 0
0 0 1
Output: 3
*/

import java.util.Scanner;

public class SpecialPositionsInBinaryMatrix {

    public static int numSpecial(int[][] mat) {

        int m = mat.length;
        int n = mat[0].length;

        // Array to count number of 1s in each row
        int[] rowCount = new int[m];

        // Array to count number of 1s in each column
        int[] colCount = new int[n];

        // First pass:
        // Count how many 1s are present in each row and each column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (mat[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }

        int specialPositions = 0;

        // Second pass:
        // A position is special if:
        // 1. It contains 1
        // 2. That row contains exactly one 1
        // 3. That column contains exactly one 1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (mat[i][j] == 1 &&
                    rowCount[i] == 1 &&
                    colCount[j] == 1) {

                    specialPositions++;
                }
            }
        }

        return specialPositions;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking matrix dimensions as input
        System.out.print("Enter number of rows (m): ");
        int m = sc.nextInt();

        System.out.print("Enter number of columns (n): ");
        int n = sc.nextInt();

        int[][] mat = new int[m][n];

        System.out.println("Enter matrix values (0 or 1):");

        // Reading matrix input from user
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = sc.nextInt();
            }
        }

        int result = numSpecial(mat);

        System.out.println("Number of Special Positions: " + result);

        sc.close(); 
    }
}