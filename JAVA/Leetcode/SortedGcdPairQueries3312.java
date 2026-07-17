/*
 * Problem Statement:
 * You are given an integer array nums of length n and an integer array queries.
 * Let gcdPairs denote an array obtained by calculating the GCD of all possible pairs (nums[i], nums[j]),
 * where 0 <= i < j < n, and then sorting these values in ascending order.
 * For each query queries[i], find the element at index queries[i] in the sorted gcdPairs array.
 */

/*
 * Approach: Frequency Counting + Sieve-like DP Counting + Prefix Sums Binary Search
 * 1. Calculate the maximum value in nums (`maxVal`) to define the range of possible GCDs.
 * 2. Count the frequencies of each element in the array `freq`.
 * 3. Count how many total numbers in `nums` are multiples of each number `i` from 1 to `maxVal` (`countMultiples`).
 * 4. Compute the exact number of pairs having GCD equal to `i` (`exactPairs`) using a backward inclusion-exclusion 
 *    sieve mechanism. For any `i`, the total combinations of its multiples is C(count, 2). We subtract out pairs 
 *    whose true GCD is a multiple of `i` (i.e., 2i, 3i, 4i...).
 * 5. Compute prefix sums of the `exactPairs` array. This transforms the count array into monotonically increasing
 *    index ranges.
 * 6. For each query, since the elements are conceptually sorted, use binary search to locate the first GCD bucket 
 *    whose prefix sum range covers the target index (`prefixSums[mid] > target`).
 */

import java.util.Arrays;
import java.util.Scanner;

public class SortedGcdPairQueries3312 {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // Step 1: Count frequency distributions of each number inside the array
        int[] freq = new int[maxVal + 1];
        for (int num : nums) {
            freq[num]++;
        }

        // Step 2: Sum up how many elements are multiples of each value i using a harmonic scale sieve
        long[] countMultiples = new long[maxVal + 1];
        for (int i = 1; i <= maxVal; i++) {
            for (int j = i; j <= maxVal; j += i) {
                countMultiples[i] += freq[j];
            }
        }

        // Step 3: Compute the exact count of pairs that possess GCD equal to i via backward deduction
        long[] exactPairs = new long[maxVal + 1];
        for (int i = maxVal; i >= 1; i--) {
            long totalPairsWithMultiple = countMultiples[i] * (countMultiples[i] - 1) / 2;
            exactPairs[i] = totalPairsWithMultiple;
            // Remove pairs whose actual GCD is a larger multiple of i
            for (int j = 2 * i; j <= maxVal; j += i) {
                exactPairs[i] -= exactPairs[j];
            }
        }

        // Step 4: Construct prefix sums array to define linear index slots for the virtual sorted array
        long[] prefixSums = new long[maxVal + 1];
        for (int i = 1; i <= maxVal; i++) {
            prefixSums[i] = prefixSums[i - 1] + exactPairs[i];
        }

        // Step 5: Process each query index position using binary search
        int[] queryAnswers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long targetIndex = queries[i];
            
            int lowBound = 1, highBound = maxVal;
            int optimalGcdResult = maxVal;
            
            while (lowBound <= highBound) {
                int midValue = lowBound + (highBound - lowBound) / 2;
                if (prefixSums[midValue] > targetIndex) {
                    optimalGcdResult = midValue;
                    highBound = midValue - 1; // Try to discover a smaller candidate GCD
                } else {
                    lowBound = midValue + 1; // Target resides in a higher segment range
                }
            }
            queryAnswers[i] = optimalGcdResult;
        }

        return queryAnswers;
    }

    public static void main(String[] args) {
        // Step 6: Process user console interactions using contextual parameter tags
        Scanner multiGcdConsoleScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawNumsLine = multiGcdConsoleScanner.nextLine().trim();
        
        if (rawNumsLine.isEmpty()) {
            System.out.println("Result: []");
            return;
        }
        
        String[] numTokens = rawNumsLine.split("\\s+");
        int[] userNumsParam = new int[numTokens.length];
        for (int i = 0; i < numTokens.length; i++) {
            userNumsParam[i] = Integer.parseInt(numTokens[i]);
        }
        
        System.out.println("Enter query index values separated by space:");
        String rawQueriesLine = multiGcdConsoleScanner.nextLine().trim();
        String[] queryTokens = rawQueriesLine.split("\\s+");
        long[] userQueriesParam = new long[queryTokens.length];
        for (int i = 0; i < queryTokens.length; i++) {
            userQueriesParam[i] = Long.parseLong(queryTokens[i]);
        }
        
        // Step 7: Instantiate the custom transformer logic class to calculate answers
        SortedGcdPairQueries3312 uniqueSolver = new SortedGcdPairQueries3312();
        int[] queryOutputs = uniqueSolver.gcdValues(userNumsParam, userQueriesParam);
        
        System.out.println("Query Answers: " + Arrays.toString(queryOutputs));
        multiGcdConsoleScanner.close();
    }
}
