/*
 * Problem Statement: LeetCode 2091 - Removing Minimum and Maximum From Array
 * You are given a 0-indexed array of distinct integers nums.
 * There is an element in nums that has the minimum value and an element that has the maximum value.
 * We want to remove both the minimum and maximum elements from the array.
 * A deletion can be performed from either the front or the back of the array.
 * Return the minimum number of deletions it would take to remove both the minimum and maximum elements.
 */

/*
 * Approach: Greedy Index Bounds Comparison (O(N) Time, O(1) Space)
 * 1. Find the indices of the minimum element (`minIndex`) and maximum element (`maxIndex`).
 * 2. Order the two indices as `left = min(minIndex, maxIndex)` and `right = max(minIndex, maxIndex)`.
 * 3. Consider the three possible strategies to remove both targets:
 *    - Strategy 1 (Delete all from front): Remove elements up to `right`. Cost = right + 1.
 *    - Strategy 2 (Delete all from back): Remove elements down to `left`. Cost = n - left.
 *    - Strategy 3 (Delete from both sides): Remove from front up to `left` and from back down to `right`.
 *      Cost = (left + 1) + (n - right).
 * 4. The minimum of these three values represents the optimal number of deletions.
 */

import java.util.Scanner;

public class RemovingMinAndMaxFromArray {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        int minIndex = 0;
        int maxIndex = 0;

        // Step 1: Find indices of minimum and maximum elements
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Step 2: Establish relative ordering between the two indices
        int left = Math.min(minIndex, maxIndex);
        int right = Math.max(minIndex, maxIndex);

        // Step 3: Compute costs for all 3 removal strategies
        int deleteFront = right + 1;
        int deleteBack = n - left;
        int deleteBothSides = (left + 1) + (n - right);

        // Step 4: Return the optimal minimum
        return Math.min(deleteFront, Math.min(deleteBack, deleteBothSides));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Minimum deletions: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        RemovingMinimumAndMaximumFromArray2091 solver = new RemovingMinimumAndMaximumFromArray2091();
        int result = solver.minimumDeletions(userNumsArray);

        System.out.println("Minimum deletions to remove min and max: " + result);
        scanner.close();
    }
}
