/*
 * Problem Statement:
 * Given a 0-indexed integer array nums, compute the sum of the longest sequential prefix 
 * (where nums[j] = nums[j-1] + 1). Return the smallest integer x >= prefixSum that is missing from nums.
 */

/*
 * Approach: Sequential Prefix Sum + Hash Set Lookup (O(N) Time, O(N) Space)
 * 1. Sequential Prefix Identification:
 *    - Start accumulating the sum from `nums[0]`.
 *    - Iterate from `index = 1` onwards as long as `nums[i] == nums[i - 1] + 1` and add `nums[i]` to `prefixSum`.
 *    - Stop at the first break in the sequence.
 * 2. Hash Set Construction:
 *    - Store all elements of `nums` in a HashSet for O(1) lookup time.
 * 3. Search for Smallest Missing Integer:
 *    - Starting at `targetValue = prefixSum`, check if `targetValue` exists in the set.
 *    - Increment `targetValue` by 1 until finding a value not present in the set, then return it.
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SmallestMissingSequentialPrefix2996 {
    public int missingInteger(int[] nums) {
        int prefixSum = nums[0];
        
        // Step 1: Compute sum of the longest sequential prefix starting from index 0
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                prefixSum += nums[i];
            } else {
                break;
            }
        }
        
        // Step 2: Store elements in a hash set for O(1) existence checks
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        // Step 3: Find smallest missing integer >= prefixSum
        int targetValue = prefixSum;
        while (numSet.contains(targetValue)) {
            targetValue++;
        }
        
        return targetValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Smallest missing integer: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        SmallestMissingSequentialPrefix2996 solverInstance = new SmallestMissingSequentialPrefix2996();
        int result = solverInstance.missingInteger(userNumsArray);

        System.out.println("Smallest missing integer: " + result);
        scanner.close();
    }
}