/*
Problem Statement:
You are given an integer n.
Define its mirror distance as: abs(n - reverse(n)) where reverse(n) is the integer 
formed by reversing the digits of n.
Return an integer denoting the mirror distance of n.
abs(x) denotes the absolute value of x.

Constraints:
1 <= n <= 10^9
*/

import java.util.Scanner;

public class MirrorDist {

    public static int mirrorDistance(int n) {
        int original = n;
        int rev = 0;
        
        // Mathematically reverse the integer
        while (n > 0) {
            // Shift existing digits left (multiply by 10) and add the last digit of n (n % 10)
            rev = rev * 10 + n % 10;
            // Remove the last digit from n
            n /= 10;
        }
        
        // Return the absolute difference between the original and reversed numbers
        return Math.abs(original - rev);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter an integer n: ");
            int n = scanner.nextInt();
            
            if (n < 1) {
                System.out.println("Please enter a positive integer.");
                return;
            }
            
            int result = mirrorDistance(n);
            System.out.println("Mirror Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        } finally {
            scanner.close();
        }
    }
}
