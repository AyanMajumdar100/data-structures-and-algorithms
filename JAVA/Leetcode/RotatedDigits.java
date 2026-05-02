/*
Problem Statement:
An integer x is good if after rotating each digit individually by 180 degrees, 
we get a valid number that is different from x. Each digit must be rotated.

A number is valid if each digit remains a digit after rotation:
- 0, 1, and 8 rotate to themselves.
- 2 and 5 rotate to each other.
- 6 and 9 rotate to each other.
- 3, 4, and 7 do not rotate to a valid digit.

Given an integer n, return the number of good integers in the range [1, n].

Constraints:
1 <= n <= 10^4
*/

import java.util.Scanner;

public class RotatedDigits {

    public static int rotatedDigits(int n) {
        // dp array stores the "state" of each number:
        // 0 = invalid
        // 1 = valid but rotates to itself
        // 2 = valid and rotates to a different number (Good Number)
        int[] dp = new int[n + 1];
        int count = 0;
        
        for (int i = 0; i <= n; i++) {
            if (i < 10) {
                // Base cases for single-digit numbers
                if (i == 0 || i == 1 || i == 8) {
                    dp[i] = 1;
                } else if (i == 2 || i == 5 || i == 6 || i == 9) {
                    dp[i] = 2;
                    count++;
                }
            } else {
                // Transition for multi-digit numbers
                int a = dp[i / 10]; // State of all digits except the last one
                int b = dp[i % 10]; // State of the last digit
                
                // If both parts are valid and only contain 0, 1, 8
                if (a == 1 && b == 1) {
                    dp[i] = 1;
                } 
                // If both parts are valid AND at least one part contains a 2, 5, 6, or 9
                else if (a >= 1 && b >= 1) {
                    dp[i] = 2;
                    count++;
                }
                // Otherwise, it remains 0 (invalid)
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the integer n:");
            int n = Integer.parseInt(scanner.nextLine().trim());
            
            if (n < 1) {
                System.out.println("Please enter a positive integer.");
                return;
            }
            
            int result = rotatedDigits(n);
            System.out.println("Number of good integers: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter a valid integer.");
        } finally {
            scanner.close();
        }
    }
}