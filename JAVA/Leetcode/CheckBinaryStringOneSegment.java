/*
1784. Check if Binary String Has at Most One Segment of Ones

Problem Statement:
You are given a binary string s consisting only of characters '0' and '1'.
The string does NOT contain leading zeros, meaning it always starts with '1'.

Your task is to determine whether the string contains at most ONE contiguous
segment of '1's.

A contiguous segment means the '1's appear together without interruption.

Examples:

Example 1
Input: s = "1001"
Output: false
Explanation:
There are two separate segments of '1':
"1" at the beginning and another "1" at the end.

Example 2
Input: s = "110"
Output: true
Explanation:
The ones appear only once in a single contiguous block.

Key Idea:
Since the string starts with '1', the first segment of ones is guaranteed.
A second segment would only occur if we see the pattern "01"
(which means we had 1 -> 0 -> 1 transition).

So if the substring "01" exists, it means a new segment of ones begins later.

Constraints:
1 <= s.length <= 100
s[i] is either '0' or '1'
s[0] is '1'
*/

import java.util.Scanner;

public class CheckBinaryStringOneSegment {

    public static boolean checkOnesSegment(String s) {

        /*
        If "01" appears in the string, it means:
        first we had a '0', then a '1' started again,
        meaning a new segment of ones began.

        That would violate the rule of having only one segment.
        */

        return !s.contains("01");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking binary string input from user
        System.out.print("Enter binary string: ");
        String s = sc.next();

        boolean result = checkOnesSegment(s);

        System.out.println("At most one segment of ones: " + result);

        // Close scanner to avoid resource leaks
        sc.close();
    }
}