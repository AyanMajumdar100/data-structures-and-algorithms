/*
 * Problem Statement: LeetCode 3876 - Construct Uniform Parity Array II
 * You are given an array nums1 of n distinct integers.
 * Construct an array nums2 of length n such that all elements in nums2 have uniform parity (all odd or all even).
 * For each index i, you may choose:
 *   1. nums2[i] = nums1[i]
 *   2. nums2[i] = nums1[i] - nums1[j] (for some j != i where nums1[i] - nums1[j] >= 1)
 * Return true if it is possible to construct such an array, otherwise return false.
 */

/*
 * Approach: Parity Subtraction Rules & Extremum Analysis (O(N) Time, O(1) Space)
 * 1. Goal A: Make all elements Even
 *    - Even numbers can stay as nums1[i].
 *    - To turn an odd number even, we must subtract another odd number: odd - odd = even.
 *    - But the smallest odd number in nums1 cannot subtract any smaller odd number from itself,
 *      so it can NEVER be turned even. Thus, making all elements even is possible IF AND ONLY IF
 *      there are NO odd numbers in nums1.
 * 
 * 2. Goal B: Make all elements Odd
 *    - Odd numbers can simply stay as nums1[i].
 *    - To turn an even number even into odd, we must subtract an odd number: even - odd = odd.
 *    - For an even number E to subtract an odd number O such that E - O >= 1, we must have E > O.
 *    - Specifically, this must hold for ALL even numbers in nums1, which is possible if and only if
 *      every even number is strictly greater than the minimum odd number:
 *      minEven > minOdd (or minOdd < minEven).
 * 
 * 3. Synthesis:
 *    - If all numbers are already odd or all even, return true.
 *    - If mixed, we can only make everything odd, which is valid if and only if minOdd < minEven.
 */

import java.util.Scanner;

public class ConstructUniformParityArrayII3876 {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        // Step 1: Find the minimum odd and minimum even values
        for (int num : nums1) {
            if (num % 2 != 0) {
                if (num < minOdd) {
                    minOdd = num;
                }
            } else {
                if (num < minEven) {
                    minEven = num;
                }
            }
        }

        // If nums1 contains only even numbers or only odd numbers, parity is already uniform
        if (minOdd == Integer.MAX_VALUE || minEven == Integer.MAX_VALUE) {
            return true;
        }

        // If mixed, all elements can be made odd iff minOdd < minEven
        return minOdd < minEven;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: true");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userNumsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userNumsArray[i] = Integer.parseInt(tokens[i]);
        }

        ConstructUniformParityArrayII3876 solver = new ConstructUniformParityArrayII3876();
        boolean canConstruct = solver.uniformArray(userNumsArray);

        System.out.println("Can construct uniform parity array: " + canConstruct);
        scanner.close();
    }
}