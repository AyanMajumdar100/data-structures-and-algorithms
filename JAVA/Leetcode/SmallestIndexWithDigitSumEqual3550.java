/*
 * Problem Statement: LeetCode 3550 - Smallest Index With Digit Sum Equal to Index
 * You are given an integer array nums.
 * Return the smallest index i such that the sum of the digits of nums[i] is equal to i.
 * If no such index exists, return -1.
 */

/*
 * Approach: Linear Scan & Digit Sum Extraction (O(N * log10(M)) Time, O(1) Space)
 * where N is the length of nums and M is the maximum value in nums.
 * 1. Iterate through each index `i` of the array from 0 to n - 1.
 * 2. Compute the sum of the digits of `nums[i]` using modulo and division.
 * 3. Check if the digit sum equals the current index `i`.
 * 4. Return the first (smallest) index that satisfies the condition.
 * 5. Return -1 if no matching index is found after checking all elements.
 */

import java.util.Arrays;
import java.util.Scanner;

public class SmallestIndexWithDigitSumEqual3550 {
    public int smallestIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (getDigitSum(nums[i]) == i) {
                return i;
            }
        }
        return -1;
    }

    private int getDigitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: -1");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNums = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNums[i] = Integer.parseInt(tokens[i]);
        }

        SmallestIndexWithDigitSumEqual3550 solver = new SmallestIndexWithDigitSumEqual3550();
        int result = solver.smallestIndex(userNums);

        System.out.println("Smallest index with digit sum equal to index: " + result);
        scanner.close();
    }
}
