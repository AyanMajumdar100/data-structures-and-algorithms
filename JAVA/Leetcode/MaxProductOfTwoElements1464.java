/*
 * Problem Statement:
 * Given the array of integers nums, choose two different indices i and j.
 * Return the maximum value of (nums[i] - 1) * (nums[j] - 1).
 */

/*
 * Approach: Single-Pass Top Two Maxima Tracking (O(N) Time, O(1) Space)
 * 1. Since all elements nums[i] >= 1, maximizing (nums[i] - 1) * (nums[j] - 1) requires finding 
 *    the two largest numbers in the array.
 * 2. Traverse `nums` linearly while keeping track of `max1` (largest) and `max2` (second largest).
 *    - If `num > max1`: Shift `max1` to `max2`, then update `max1 = num`.
 *    - Else if `num > max2`: Update `max2 = num`.
 * 3. Return `(max1 - 1) * (max2 - 1)`.
 */

import java.util.Scanner;

public class MaxProductOfTwoElements1464 {
    public int maxProduct(int[] nums) {
        int max1 = 0;
        int max2 = 0;

        // Traverse array linearly to extract top two largest values
        for (int num : nums) {
            if (num > max1) {
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max2 = num;
            }
        }

        return (max1 - 1) * (max2 - 1);
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

        MaxProductOfTwoElements1464 solverInstance = new MaxProductOfTwoElements1464();
        int maximumProduct = solverInstance.maxProduct(userNumsArray);

        System.out.println("Maximum value of (nums[i]-1)*(nums[j]-1): " + maximumProduct);
        arrayScanner.close();
    }
}