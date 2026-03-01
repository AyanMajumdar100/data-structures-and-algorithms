/*
Problem: Partitioning Into Minimum Number Of Deci-Binary Numbers

A deci-binary number contains only digits 0 or 1 (no leading zeros).

Given a decimal string n, return the minimum number of
positive deci-binary numbers needed so that their sum equals n.

Example:
Input: n = "32"
Output: 3
Explanation: 10 + 11 + 11 = 32

Input: n = "82734"
Output: 8

Input: n = "27346209830709182346"
Output: 9
*/

import java.util.*;

class DeciBinaryPartition {

    /*
    Beginner-friendly idea:

    Each digit in the number tells us how many 1s we need at that position.
    Since deci-binary numbers can only contribute 0 or 1 per position,
    the minimum count required is simply the largest digit present.

    Example:
    If a digit is 8 → we need at least 8 deci-binary numbers.
    So the answer is the maximum digit in the string.
    */
    public static int minPartitions(String n) {
        int maxDigit = 0;

        for (int i = 0; i < n.length(); i++) {
            int currentDigit = n.charAt(i) - '0';

            if (currentDigit > maxDigit) {
                maxDigit = currentDigit;

                // Early exit since 9 is the maximum possible digit
                if (maxDigit == 9) {
                    return 9;
                }
            }
        }

        return maxDigit;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input: decimal string
        String n = scanner.nextLine().trim();

        int result = minPartitions(n);
        System.out.println(result);
        scanner.close();
    }
}