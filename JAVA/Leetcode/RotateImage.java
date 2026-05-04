/*
Problem Statement:
You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. 
DO NOT allocate another 2D matrix and do the rotation.

Constraints:
n == matrix.length == matrix[i].length
1 <= n <= 20
-1000 <= matrix[i][j] <= 1000
*/

import java.util.Scanner;

public class RotateImage {

    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // Step 1: Transpose the matrix (swap matrix[i][j] with matrix[j][i])
        for (int i = 0; i < n; i++) {
            // Start j from i to avoid swapping elements back to their original positions
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Step 2: Reverse each row horizontally
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the size of the matrix (n):");
            int n = Integer.parseInt(scanner.nextLine().trim());
            
            int[][] matrix = new int[n][n];
            System.out.println("Enter the matrix rows (numbers separated by spaces):");
            for (int i = 0; i < n; i++) {
                String[] rowStrs = scanner.nextLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(rowStrs[j]);
                }
            }
            
            rotate(matrix);
            
            System.out.println("Rotated Matrix:");
            for (int i = 0; i < n; i++) {
                System.out.print("[");
                for (int j = 0; j < n; j++) {
                    System.out.print(matrix[i][j] + (j == n - 1 ? "" : ", "));
                }
                System.out.println("]");
            }
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}
