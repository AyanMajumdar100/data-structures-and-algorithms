/*
 * Problem Statement:
 * You are given a 1-indexed array of distinct integers nums of length n.
 * Distribute all elements into two arrays arr1 and arr2:
 * - 1st element goes to arr1.
 * - 2nd element goes to arr2.
 * - For the i-th element (i >= 3): if the last element of arr1 > last element of arr2, append to arr1; otherwise append to arr2.
 * Return the concatenation of arr1 and arr2.
 */

/*
 * Approach: Direct Simulation with Preallocated Buffers (O(N) Time, O(N) Space)
 * 1. Initialize two pointers/buffers `arr1` and `arr2` of size n.
 * 2. Place `nums[0]` into `arr1` and `nums[1]` into `arr2`.
 * 3. Iterate through `nums` from index 2 to n - 1:
 *    - Compare the latest added elements `arr1[ptr1 - 1]` and `arr2[ptr2 - 1]`.
 *    - If `arr1[ptr1 - 1] > arr2[ptr2 - 1]`, append `nums[i]` to `arr1`.
 *    - Otherwise, append `nums[i]` to `arr2`.
 * 4. Concatenate `arr1` and `arr2` into a single result array.
 */

import java.util.Arrays;
import java.util.Scanner;

public class DistributeElementsIntoTwoArraysI3069 {
    public int[] resultArray(int[] nums) {
        int n = nums.length;
        int[] firstArray = new int[n];
        int[] secondArray = new int[n];
        int firstPointer = 0;
        int secondPointer = 0;

        // Base distributions for the first two elements
        firstArray[firstPointer++] = nums[0];
        secondArray[secondPointer++] = nums[1];

        // Process remaining elements sequentially
        for (int i = 2; i < n; i++) {
            if (firstArray[firstPointer - 1] > secondArray[secondPointer - 1]) {
                firstArray[firstPointer++] = nums[i];
            } else {
                secondArray[secondPointer++] = nums[i];
            }
        }

        // Concatenate firstArray followed by secondArray
        int[] concatenatedResult = new int[n];
        int writeIdx = 0;
        for (int i = 0; i < firstPointer; i++) {
            concatenatedResult[writeIdx++] = firstArray[i];
        }
        for (int i = 0; i < secondPointer; i++) {
            concatenatedResult[writeIdx++] = secondArray[i];
        }

        return concatenatedResult;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter distinct array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: []");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        DistributeElementsIntoTwoArraysI3069 solverInstance = new DistributeElementsIntoTwoArraysI3069();
        int[] distributedResult = solverInstance.resultArray(userNumsArray);

        System.out.println("Result array after distribution: " + Arrays.toString(distributedResult));
        scanner.close();
    }
}
