/*
 * Problem Statement:
 * Given an integer array nums, find three numbers whose product is maximum and return the maximum product.
 */

/*
 * Approach: Single-Pass Extremes Tracking (O(N) Time, O(1) Space)
 * 1. The maximum product of three numbers in an array with both positive and negative values can only come from:
 *    - Option A: Product of the 3 largest numbers (`max1 * max2 * max3`).
 *    - Option B: Product of the 2 smallest (most negative) numbers and the largest number (`min1 * min2 * max1`).
 * 2. Scan linearly through `nums` once to maintain `max1`, `max2`, `max3` (top 3 largest) and `min1`, `min2` (top 2 smallest).
 * 3. Return `Math.max(max1 * max2 * max3, min1 * min2 * max1)`.
 */

import java.util.Scanner;

public class MaximumProductOfThreeNumbers628 {
    public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;

        // Traverse the array once to track top 3 maximums and top 2 minimums
        for (int num : nums) {
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max3 = max2;
                max2 = num;
            } else if (num > max3) {
                max3 = num;
            }

            if (num < min1) {
                min2 = min1;
                min1 = num;
            } else if (num < min2) {
                min2 = num;
            }
        }

        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }

    public static void main(String[] args) {
        Scanner arrayScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = arrayScanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Maximum product: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        MaximumProductOfThreeNumbers628 solverInstance = new MaximumProductOfThreeNumbers628();
        int maximumProduct = solverInstance.maximumProduct(userNumsArray);

        System.out.println("Maximum product of three numbers: " + maximumProduct);
        arrayScanner.close();
    }
}
