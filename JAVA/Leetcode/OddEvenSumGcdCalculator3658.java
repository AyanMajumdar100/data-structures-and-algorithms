/*
 * Problem Statement:
 * You are given an integer n. Compute the GCD of two values:
 * - sumOdd: the sum of the smallest n positive odd numbers.
 * - sumEven: the sum of the smallest n positive even numbers.
 */

/*
 * Approach: Mathematical Derivation via Arithmetic Progressions
 * 1. The sum of the first n positive odd numbers is a well-known mathematical identity:
 *    sumOdd = 1 + 3 + 5 + ... + (2n - 1) = n^2
 * 2. The sum of the first n positive even numbers is given by:
 *    sumEven = 2 + 4 + 6 + ... + 2n = n * (n + 1) = n^2 + n
 * 3. We need to find GCD(sumOdd, sumEven) = GCD(n^2, n * (n + 1)).
 *    Using the property of GCD, GCD(a, b) = GCD(a, b - a):
 *    GCD(n^2, n^2 + n) = GCD(n^2, (n^2 + n) - n^2) = GCD(n^2, n) = n
 * 4. Therefore, the result for any given integer n is always exactly equal to n itself.
 *    This reduces the problem to an elegant O(1) time and space complexity solution.
 */

import java.util.Scanner;

public class OddEvenSumGcdCalculator3658 {
    public int gcdOfOddEvenSums(int n) {
        // Step 1: Return n directly based on the identity GCD(n^2, n*(n+1)) = n
        return n;
    }

    public static void main(String[] args) {
        // Step 2: Handle user console inputs using descriptive parameter names
        Scanner parityGcdConsoleScanner = new Scanner(System.in);
        System.out.println("Enter integer n:");
        int userCountParamN = parityGcdConsoleScanner.nextInt();
        
        // Step 3: Initialize the solver object and output computed value
        OddEvenSumGcdCalculator3658 dynamicSolver = new OddEvenSumGcdCalculator3658();
        int calculatedGcdResult = dynamicSolver.gcdOfOddEvenSums(userCountParamN);
        
        System.out.println("GCD of sumOdd and sumEven is: " + calculatedGcdResult);
        parityGcdConsoleScanner.close();
    }
}
