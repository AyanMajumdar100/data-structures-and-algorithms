/*
1980. Find Unique Binary String

Problem Statement:
You are given an array of n unique binary strings called nums.
Each string has length n and consists only of '0' and '1'.

Your task is to return a binary string of length n that does NOT
appear in nums.

It is guaranteed that at least one such string exists.

Key Idea (Diagonal Trick / Cantor’s Diagonalization):

If we construct a new string by flipping the i-th character
of the i-th string in nums, the resulting string will differ
from every string in nums in at least one position.

Example:
nums = ["01","10"]

index : 0 1

nums[0] = 0 1
nums[1] = 1 0

Construct new string:
flip nums[0][0] -> 1
flip nums[1][1] -> 1

Result = "11"

"11" is guaranteed to be different from every string in nums.

Why this works:
The new string differs from nums[0] at index 0,
differs from nums[1] at index 1,
and therefore cannot equal any string in the array.

Constraints:
1 <= nums.length <= 16
nums[i].length == nums.length
nums[i] contains only '0' or '1'
All strings in nums are unique
*/

import java.util.Scanner;

public class FindUniqueBinaryString {

    public static String findDifferentBinaryString(String[] nums) {

        // StringBuilder is used because strings are immutable in Java
        StringBuilder sb = new StringBuilder();

        /*
        Loop through each index i.
        For the i-th string, we check the i-th character.
        Then we flip it:
        '0' -> '1'
        '1' -> '0'
        */
        for (int i = 0; i < nums.length; i++) {

            char current = nums[i].charAt(i);

            // Flip the bit
            if (current == '0') {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }

        // Convert StringBuilder to String
        return sb.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Number of binary strings
        System.out.print("Enter number of binary strings (n): ");
        int n = sc.nextInt();

        String[] nums = new String[n];

        System.out.println("Enter " + n + " binary strings of length " + n + ":");

        for (int i = 0; i < n; i++) {
            nums[i] = sc.next();
        }

        String result = findDifferentBinaryString(nums);

        System.out.println("Unique binary string: " + result);

        // Close scanner
        sc.close();
    }
}