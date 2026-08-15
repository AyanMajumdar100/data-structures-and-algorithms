/*
 * Problem Statement:
 * You are given an integer array nums.
 * Return the length of the longest subsequence in nums whose bitwise XOR is non-zero.
 * If no such subsequence exists, return 0.
 */

/*
 * Approach: Bitwise XOR Global Parity Analysis (O(N) Time, O(1) Space)
 * 1. Compute the total XOR sum of all elements in the entire array `nums`.
 * 2. Case 1: Total XOR sum != 0
 *    - The entire array is already a valid subsequence with non-zero XOR.
 *    - The maximum length is `nums.length`.
 * 3. Case 2: Total XOR sum == 0
 *    - If all elements in `nums` are 0, every possible subsequence has XOR = 0. Return `0`.
 *    - If there is at least one non-zero element `x`, removing `x` from the full array leaves 
 *      a subsequence of length `nums.length - 1` whose XOR is `0 ^ x = x != 0`.
 *    - Thus, the maximum length is `nums.length - 1`.
 */

import java.util.Scanner;

public class LongestSubsequenceNonZeroXor3702 {
    public int longestSubsequence(int[] nums) {
        int totalXorSum = 0;
        boolean containsNonZeroElement = false;

        // Compute total XOR sum and check for any non-zero element
        for (int num : nums) {
            totalXorSum ^= num;
            if (num != 0) {
                containsNonZeroElement = true;
            }
        }

        // Evaluate max length based on XOR parity
        if (totalXorSum != 0) {
            return nums.length;
        } else if (containsNonZeroElement) {
            return nums.length - 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Longest subsequence length: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        LongestSubsequenceNonZeroXor3702 solverInstance = new LongestSubsequenceNonZeroXor3702();
        int maxSubsequenceLength = solverInstance.longestSubsequence(userNumsArray);

        System.out.println("Length of longest subsequence with non-zero bitwise XOR: " + maxSubsequenceLength);
        scanner.close();
    }
}