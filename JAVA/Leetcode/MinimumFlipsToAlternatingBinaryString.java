/*
1888. Minimum Number of Flips to Make the Binary String Alternating

Problem Statement:
You are given a binary string s consisting only of '0' and '1'.

You can perform two types of operations:

Type-1 (Rotation):
Remove the first character of the string and append it to the end.
Example: "1010" -> "0101"

Type-2 (Flip):
Choose any character and flip it.
'0' -> '1'
'1' -> '0'

Goal:
Return the minimum number of Type-2 (flip) operations required to make
the string alternating.

A string is alternating if no two adjacent characters are the same.

Examples of alternating strings:
"0101", "1010", "0", "1"

Example 1:
Input: s = "111000"
Output: 2

Example 2:
Input: s = "010"
Output: 0

Example 3:
Input: s = "1110"
Output: 1

Important Idea:

An alternating string can only be of two forms:

Pattern A: 010101...
Pattern B: 101010...

Since we are allowed to rotate the string (Type-1 operation),
we must consider every possible rotation.

Instead of actually rotating the string many times,
we simulate rotations using a sliding window over (s + s).

Why (s + s)?
Because every rotation of s appears as a substring of length n
inside s + s.

For each window of size n, we compute how many flips are needed
to match Pattern A and Pattern B, and take the minimum.

Constraints:
1 <= s.length <= 10^5
s[i] is either '0' or '1'
*/

import java.util.Scanner;

public class MinimumFlipsToAlternatingBinaryString {

    public static int minFlips(String s) {

        int n = s.length();

        // diff1 -> mismatches if pattern starts with '0'  (010101...)
        // diff2 -> mismatches if pattern starts with '1'  (101010...)
        int diff1 = 0;
        int diff2 = 0;

        int ans = Integer.MAX_VALUE;

        /*
        We iterate over 2*n to simulate the doubled string (s + s)
        without actually creating it.
        */
        for (int i = 0; i < 2 * n; i++) {

            // Current character in virtual doubled string
            char currentChar = s.charAt(i % n);

            // Expected characters for two alternating patterns
            char pattern1Expected = (i % 2 == 0) ? '0' : '1';
            char pattern2Expected = (i % 2 == 0) ? '1' : '0';

            // Count mismatches
            if (currentChar != pattern1Expected) diff1++;
            if (currentChar != pattern2Expected) diff2++;

            /*
            When the window size exceeds n,
            remove the contribution of the character
            that moved out of the sliding window.
            */
            if (i >= n) {

                char outgoingChar = s.charAt((i - n) % n);

                char outPattern1 = ((i - n) % 2 == 0) ? '0' : '1';
                char outPattern2 = ((i - n) % 2 == 0) ? '1' : '0';

                if (outgoingChar != outPattern1) diff1--;
                if (outgoingChar != outPattern2) diff2--;
            }

            /*
            Once we have a full window of size n,
            we can compute flips needed for this rotation.
            */
            if (i >= n - 1) {
                ans = Math.min(ans, Math.min(diff1, diff2));
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // User input
        System.out.print("Enter binary string: ");
        String s = sc.next();

        int result = minFlips(s);

        System.out.println("Minimum flips required: " + result);

        // Close scanner
        sc.close();
    }
}