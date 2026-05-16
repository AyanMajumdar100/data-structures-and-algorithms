/*
 * Problem Statement:
 * You are given an integer array nums. We consider an array good if it is a permutation of an array base[n].
 * base[n] = [1, 2, ..., n - 1, n, n] (contains 1 to n - 1 exactly once, plus two occurrences of n).
 * Return true if the given array is good, otherwise return false.
 */

/*
 * Approach:
 * An array is "good" if its length is n + 1, and it contains numbers from 1 to n - 1 exactly once, 
 * and the number n exactly twice.
 * We can determine n as nums.length - 1.
 * Using a frequency array (count), we count the occurrences of each number in nums.
 * If any number is greater than n, it is immediately invalid.
 * Finally, we verify that counts for 1 to n - 1 are exactly 1, and the count for n is exactly 2.
 */
import java.util.Scanner;

public class ChkGoodArr {
    public boolean isGood(int[] nums) {
        int len = nums.length;
        int n = len - 1;
        // Frequency array to track occurrences of each number up to n
        int[] count = new int[len];

        // Iterate through the array to populate the frequency array
        for (int num : nums) {
            // If any number is strictly greater than n, it violates the base[n] structure
            if (num > n) {
                return false;
            }
            count[num]++;
        }

        // Verify that numbers from 1 to n - 1 appear exactly one time
        for (int i = 1; i < n; i++) {
            if (count[i] != 1) {
                return false;
            }
        }

        // Verify that the maximum element n appears exactly two times
        return count[n] == 2;
    }

    public static void main(String[] args) {
        // Handle user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String[] input = scanner.nextLine().split(" ");
        int[] nums = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            nums[i] = Integer.parseInt(input[i]);
        }

        ChkGoodArr2784 solution = new ChkGoodArr2784();
        boolean result = solution.isGood(nums);
        System.out.println("Is the array good? " + result);
        scanner.close();
    }
}