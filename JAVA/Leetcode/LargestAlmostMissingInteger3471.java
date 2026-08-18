/*
 * Problem Statement:
 * You are given an integer array nums and an integer k.
 * An integer x is almost missing from nums if x appears in exactly one subarray of size k.
 * Return the largest almost missing integer from nums. If no such integer exists, return -1.
 */

/*
 * Approach: Subarray Presence Frequency Counting (O(N * K + maxVal) Time, O(1) Space)
 * 1. An element's frequency in the problem context is the number of distinct size-k subarrays in which it appears.
 * 2. Iterate through each possible value x in range [0, 50] (or collect frequencies over all size-k windows).
 * 3. Count how many subarrays of size k contain value x.
 * 4. If x appears in exactly 1 subarray of size k, candidate x is valid; maximize over all valid candidates.
 * 5. Return the maximum valid candidate, or -1 if none exists.
 */

import java.util.Scanner;

public class LargestAlmostMissingInteger3471 {
    public int largestInteger(int[] nums, int k) {
        int maxAlmostMissing = -1;
        int n = nums.length;

        // Iterate over all possible values within constraints [0, 50]
        for (int x = 0; x <= 50; x++) {
            int subarrayCount = 0;

            // Check every subarray of size k
            for (int i = 0; i <= n - k; i++) {
                boolean foundInWindow = false;
                for (int j = i; j < i + k; j++) {
                    if (nums[j] == x) {
                        foundInWindow = true;
                        break;
                    }
                }
                if (foundInWindow) {
                    subarrayCount++;
                }
            }

            // Must appear in exactly one subarray of size k
            if (subarrayCount == 1) {
                maxAlmostMissing = Math.max(maxAlmostMissing, x);
            }
        }

        return maxAlmostMissing;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Largest almost missing integer: -1");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter window size k:");
        int userKParam = scanner.nextInt();

        LargestAlmostMissingInteger3471 solverInstance = new LargestAlmostMissingInteger3471();
        int result = solverInstance.largestInteger(userNumsArray, userKParam);

        System.out.println("Largest almost missing integer: " + result);
        scanner.close();
    }
}
