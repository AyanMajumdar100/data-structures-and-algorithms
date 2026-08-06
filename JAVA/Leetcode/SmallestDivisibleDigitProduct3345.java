/*
 * Problem Statement:
 * You are given two integers n and t. 
 * Return the smallest number greater than or equal to n such that the product of its digits is divisible by t.
 */

/*
 * Approach: Linear Search Simulation (O(1) Time, O(1) Space)
 * 1. Constraint Analysis:
 *    Since n <= 100 and t <= 10, any number ending in '0' has a digit product of 0, which is divisible by all t.
 *    Therefore, we need to inspect at most 10 consecutive integers starting from n before finding a valid candidate.
 * 2. Algorithm:
 *    - Start a loop from `currentNumber = n`.
 *    - For each candidate, compute its digit product by extracting digits using `% 10` and `/ 10`.
 *    - If `digitProduct % t == 0`, return `currentNumber`.
 *    - Otherwise, increment `currentNumber` and repeat.
 */

import java.util.Scanner;

public class SmallestDivisibleDigitProduct3345 {
    public int smallestNumber(int n, int t) {
        while (true) {
            int tempNumber = n;
            int digitProduct = 1;

            // Extract digits and compute their product
            while (tempNumber > 0) {
                digitProduct *= (tempNumber % 10);
                tempNumber /= 10;
            }

            // Check if the digit product is divisible by t
            if (digitProduct % t == 0) {
                return n;
            }
            n++;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter integer n:");
        int userNParam = scanner.nextInt();

        System.out.println("Enter integer t:");
        int userTParam = scanner.nextInt();

        SmallestDivisibleDigitProduct3345 solverInstance = new SmallestDivisibleDigitProduct3345();
        int smallestValidNumber = solverInstance.smallestNumber(userNParam, userTParam);

        System.out.println("Smallest number >= " + userNParam + " with digit product divisible by " + userTParam + ": " + smallestValidNumber);
        scanner.close();
    }
}
