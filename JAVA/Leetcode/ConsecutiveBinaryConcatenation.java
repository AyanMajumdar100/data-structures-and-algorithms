/*
Problem: Concatenation of Consecutive Binary Numbers

Given an integer n, form a binary string by concatenating
binary representations of numbers from 1 to n.
Return its decimal value modulo 10^9 + 7.

Example:
Input: n = 1
Output: 1

Input: n = 3
Output: 27
Explanation:
1 -> "1"
2 -> "10"
3 -> "11"
Concatenation = "11011" = 27

Input: n = 12
Output: 505379714
*/

import java.util.*;

class ConsecutiveBinaryConcatenation {

    /*
    Beginner-friendly explanation:

    We build the final number step by step.
    When we append a number in binary,
    we shift the current result left by the number of bits
    in that number, then add the number itself.

    If i is a power of 2 → its binary length increases by 1.
    */
    public static int concatenatedBinary(int n) {
        long ans = 0;
        long mod = 1_000_000_007;
        int len = 0;

        for (int i = 1; i <= n; i++) {

            // Detect when binary length increases
            if ((i & (i - 1)) == 0) {
                len++;
            }

            // Shift left and append current number
            ans = ((ans << len) | i) % mod;
        }

        return (int) ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input: integer n
        int n = scanner.nextInt();

        int result = concatenatedBinary(n);
        System.out.println(result);
        scanner.close();
    }
}