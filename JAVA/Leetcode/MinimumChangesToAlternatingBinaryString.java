/*
1758. Minimum Changes To Make Alternating Binary String

Problem Statement:
You are given a binary string s consisting only of characters '0' and '1'.

In one operation, you can flip any character:
'0' -> '1'
'1' -> '0'

A string is considered alternating if no two adjacent characters are equal.

Examples of alternating strings:
"0101", "1010", "0", "1"

Examples of non-alternating strings:
"0100", "111", "0011"

Your task:
Return the minimum number of operations required to convert the given string
into an alternating binary string.

Important Insight:
A valid alternating string can only be of two possible forms:

Pattern 1: starts with '0'
Example: 010101010...

Pattern 2: starts with '1'
Example: 101010101...

We calculate how many flips are needed to convert the string into both patterns
and return the minimum.

Example 1:
Input:
0100
Output:
1
Explanation:
Change last '0' -> '1' to get "0101"

Example 2:
Input:
10
Output:
0
Explanation:
Already alternating

Example 3:
Input:
1111
Output:
2
Explanation:
Possible results: "0101" or "1010"

Constraints:
1 <= s.length <= 10^4
s[i] is either '0' or '1'
*/

import java.util.Scanner;

public class MinimumChangesToAlternatingBinaryString {

    public static int minOperations(String s) {

        int countMismatchPattern0 = 0;
        int n = s.length();

        // We assume the alternating pattern starts with '0'
        // Pattern becomes: 0 1 0 1 0 1 ...
        // Index:            0 1 2 3 4 5 ...
        for (int i = 0; i < n; i++) {

            int currentDigit = s.charAt(i) - '0';

            // For pattern starting with '0':
            // even index -> 0
            // odd index  -> 1
            int expectedDigit = i % 2;

            // If the digit does not match expected pattern
            // then we would need one flip operation
            if (currentDigit != expectedDigit) {
                countMismatchPattern0++;
            }
        }

        /*
        If mismatches for pattern "010101..." = countMismatchPattern0

        Then mismatches for pattern "101010..." must be:
        total_length - countMismatchPattern0

        Because every matching position in first pattern
        becomes mismatching in the second pattern.
        */
        int countMismatchPattern1 = n - countMismatchPattern0;

        // Minimum operations among the two possible alternating patterns
        return Math.min(countMismatchPattern0, countMismatchPattern1);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking binary string input from the user
        System.out.print("Enter binary string: ");
        String s = sc.next();

        int result = minOperations(s);

        System.out.println("Minimum operations needed: " + result);

        // Closing scanner to prevent resource leaks
        sc.close();
    }
}