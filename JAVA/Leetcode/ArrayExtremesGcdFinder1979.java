/*
 * Problem Statement:
 * Given an integer array nums, return the greatest common divisor of the smallest number and largest number in nums.
 * The greatest common divisor of two numbers is the largest positive integer that evenly divides both numbers.
 */

/*
 * Approach: Linear Extremes Scanning + Euclidean GCD Algorithm
 * 1. Initialize two variables `minVal` and `maxVal` with the first element of the array.
 * 2. Scan linearly through the array from left to right to track the global absolute minimum and maximum values.
 * 3. Use the Euclidean algorithm iteratively (`while (b != 0)`) to compute the greatest common divisor between 
 *    the identified minimum and maximum elements.
 * 4. This operates efficiently in O(N + log(min(minVal, maxVal))) time complexity and O(1) auxiliary space.
 */

import java.util.Scanner;

public class ArrayExtremesGcdFinder1979 {
    public int findGCD(int[] nums) {
        int minVal = nums[0];
        int maxVal = nums[0];
        
        // Step 1: Discover the absolute maximum and minimum bounds via linear scan
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < minVal) {
                minVal = nums[i];
            }
            if (nums[i] > maxVal) {
                maxVal = nums[i];
            }
        }
        
        // Step 2: Calculate the Greatest Common Divisor between both boundaries
        return computeEuclideanGcd(minVal, maxVal);
    }

    private int computeEuclideanGcd(int numberA, int numberB) {
        while (numberB != 0) {
            int remainderHolder = numberA % numberB;
            numberA = numberB;
            numberB = remainderHolder;
        }
        return numberA;
    }

    public static void main(String[] args) {
        // Step 3: Parse standard keyboard console inputs using descriptive variable names
        Scanner scannerInstance = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawConsoleInput = scannerInstance.nextLine().trim();
        
        if (rawConsoleInput.isEmpty()) {
            System.out.println("Resulting extreme GCD: 0");
            return;
        }
        
        String[] stringTokens = rawConsoleInput.split("\\s+");
        int[] userArrayParam = new int[stringTokens.length];
        for (int i = 0; i < stringTokens.length; i++) {
            userArrayParam[i] = Integer.parseInt(stringTokens[i]);
        }
        
        // Step 4: Execute calculations and render output results
        ArrayExtremesGcdFinder1979 arraySolver = new ArrayExtremesGcdFinder1979();
        int finalGcdResult = arraySolver.findGCD(userArrayParam);
        System.out.println("Resulting extreme GCD: " + finalGcdResult);
        
        scannerInstance.close();
    }
}
