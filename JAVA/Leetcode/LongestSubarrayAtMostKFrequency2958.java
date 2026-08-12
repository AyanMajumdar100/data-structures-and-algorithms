/*
 * Problem Statement:
 * Given an integer array nums and an integer k, return the length of the longest 
 * good subarray where the frequency of each element in the subarray is at most k.
 */

/*
 * Approach: Two-Pointer Sliding Window + Frequency Hash Map (O(N) Time, O(N) Space)
 * 1. Maintain a sliding window [left, right] and a hash map `freqMap` tracking element frequencies in the window.
 * 2. Expand the `right` pointer to include `nums[right]`, updating its frequency count.
 * 3. Shrink the window from the `left` pointer as long as the frequency of `nums[right]` exceeds `k`.
 * 4. Update `maxLength` with the size of the valid window `right - left + 1`.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LongestSubarrayAtMostKFrequency2958 {
    public int maxSubarrayLength(int[] nums, int k) {
        int left = 0;
        int maxLength = 0;
        Map<Integer, Integer> freqMap = new HashMap<>();

        // Expand right pointer through the array
        for (int right = 0; right < nums.length; right++) {
            int currentNum = nums[right];
            freqMap.put(currentNum, freqMap.getOrDefault(currentNum, 0) + 1);

            // Shrink window from left if frequency exceeds k
            while (freqMap.get(currentNum) > k) {
                int leftNum = nums[left];
                freqMap.put(leftNum, freqMap.get(leftNum) - 1);
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Max subarray length: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        System.out.println("Enter integer k:");
        int userKParam = scanner.nextInt();

        LongestSubarrayAtMostKFrequency2958 solverInstance = new LongestSubarrayAtMostKFrequency2958();
        int maxLenResult = solverInstance.maxSubarrayLength(userNumsArray, userKParam);

        System.out.println("Length of longest good subarray: " + maxLenResult);
        scanner.close();
    }
}
