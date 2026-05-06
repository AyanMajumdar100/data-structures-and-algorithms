/*
Problem Statement:
You are given an m x n matrix of characters boxGrid representing a side-view of a box.
A stone '#', A stationary obstacle '*', Empty '.'
The box is rotated 90 degrees clockwise, causing stones to fall due to gravity until they 
hit an obstacle, another stone, or the bottom. 
Return an n x m matrix representing the box after the rotation.

Constraints:
m == boxGrid.length
n == boxGrid[i].length
1 <= m, n <= 500
boxGrid[i][j] is either '#', '*', or '.'.
*/

import java.util.Scanner;

public class RotateBox {

    public static char[][] rotateTheBox(char[][] boxGrid) {
        int m = boxGrid.length;
        int n = boxGrid[0].length;
        
        // The rotated box will have dimensions n x m
        char[][] res = new char[n][m];
        
        // Initialize the result grid with empty spaces
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = '.';
            }
        }
        
        // Process each row of the original box
        for (int i = 0; i < m; i++) {
            // 'empty' tracks the lowest available position in the current column of the rotated box
            int empty = n - 1;
            
            // Scan from right to left (bottom to top in the rotated box)
            for (int j = n - 1; j >= 0; j--) {
                if (boxGrid[i][j] == '*') {
                    // Obstacles don't fall. Place it in the rotated position.
                    res[j][m - 1 - i] = '*';
                    // The next available empty spot for a falling stone is just above the obstacle
                    empty = j - 1;
                } else if (boxGrid[i][j] == '#') {
                    // A stone falls directly to the lowest known 'empty' spot
                    res[empty][m - 1 - i] = '#';
                    // The empty spot shifts up by one
                    empty--;
                }
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of rows (m) and columns (n) of the box:");
            String[] dims = scanner.nextLine().trim().split("\\s+");
            int m = Integer.parseInt(dims[0]);
            int n = Integer.parseInt(dims[1]);
            
            char[][] boxGrid = new char[m][n];
            System.out.println("Enter the grid rows (characters '.', '#', '*' continuously without spaces):");
            for (int i = 0; i < m; i++) {
                String rowStr = scanner.nextLine().trim();
                if (rowStr.length() != n) {
                    System.out.println("Row length does not match specified columns. Exiting.");
                    return;
                }
                boxGrid[i] = rowStr.toCharArray();
            }
            
            char[][] result = rotateTheBox(boxGrid);
            
            System.out.println("\nRotated Box:");
            for (int i = 0; i < result.length; i++) {
                System.out.print("[");
                for (int j = 0; j < result[0].length; j++) {
                    System.out.print("'" + result[i][j] + "'" + (j == result[0].length - 1 ? "" : ", "));
                }
                System.out.println("]");
            }
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}