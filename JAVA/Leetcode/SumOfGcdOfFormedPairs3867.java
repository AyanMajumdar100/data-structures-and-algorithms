/*
 * Problem Statement:
 * You are given an integer array nums of length n. 
 * 1. Construct an array prefixGcd where prefixGcd[i] = gcd(nums[i], max(nums[0]...nums[i])).
 * 2. Sort prefixGcd in non-decreasing order.
 * 3. Match the smallest unpaired and largest unpaired elements (Two Pointers: left and right) and compute their GCD.
 * 4. Return the sum of the GCD values of all formed pairs. (If n is odd, ignore the middle element).
 */

/*
 * Approach: Prefix Maximum + Two Pointers Simulation
 * 1. Maintain a running maximum value `runningMax` as we iterate through the `nums` array.
 * 2. Calculate the GCD of each element with the current `runningMax` and store it in `prefixGcd`.
 * 3. Sort `prefixGcd` to organize elements from smallest to largest.
 * 4. Use two pointers (`leftBound` starting at 0, `rightBound` starting at n - 1) to form pairs from 
 *    the extremes towards the center, summing their pairwise GCDs.
 */

import java.util.Arrays;
import java.util.Scanner;

public class SumOfGcdOfFormedPairs3867 {
    public long gcdSum(int[] nums) {
        int arrayLength = nums.length;
        int[] prefixGcd = new int[arrayLength];
        int runningMax = 0;
        
        // Step 1: Populate the prefixGcd array using a running maximum
        for (int i = 0; i < arrayLength; i++) {
            runningMax = Math.max(runningMax, nums[i]);
            prefixGcd[i] = calculateGcd(nums[i], runningMax);
        }
        
        // Step 2: Sort the prefixGcd array to prepare for pair matching
        Arrays.sort(prefixGcd);
        
        long totalGcdSum = 0;
        int leftBound = 0;
        int rightBound = arrayLength - 1;
        
        // Step 3: Match extreme elements using two pointers and accumulate pair GCDs
        while (leftBound < rightBound) {
            totalGcdSum += calculateGcd(prefixGcd[leftBound], prefixGcd[rightBound]);
            leftBound++;
            rightBound--;
        }
        
        return totalGcdSum;
    }
    
    // Helper method to compute Greatest Common Divisor (GCD) using Euclidean Algorithm
    private int calculateGcd(int numberA, int numberB) {
        while (numberB != 0) {
            int remainder = numberA % numberB;
            numberA = numberB;
            numberB = remainder;
        }
        return numberA;
    }

    public static void main(String[] args) {
        // Step 4: Handle standard scanner inputs using unique descriptive names
        Scanner pairingConsoleScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = pairingConsoleScanner.nextLine().trim();
        
        if (rawInputString.isEmpty()) {
            System.out.println("Sum of GCD of formed pairs: 0");
            return;
        }
        
        String[] stringSplits = rawInputString.split("\\s+");
        int[] userArrayParam = new int[stringSplits.length];
        for (int i = 0; i < stringSplits.length; i++) {
            userArrayParam[i] = Integer.parseInt(stringSplits[i]);
        }
        
        // Step 5: Execute calculations and print the sum
        SumOfGcdOfFormedPairs3867 uniqueSolver = new SumOfGcdOfFormedPairs3867();
        long resultingSum = uniqueSolver.gcdSum(userArrayParam);
        System.out.println("Sum of GCD of formed pairs: " + resultingSum);
        
        pairingConsoleScanner.close();
    }
}
