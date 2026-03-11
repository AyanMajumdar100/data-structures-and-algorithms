/*
1009. Complement of Base 10 Integer

Problem Statement:
The complement of an integer is obtained by flipping all the bits in its binary representation:
0 -> 1
1 -> 0

For example:
5 in binary  = 101
Complement   = 010
Result       = 2 (in decimal)

Given an integer n, return its complement.

Important:
The complement should only flip the bits that exist in the binary representation of n.
Leading zeros are NOT considered part of the number.

Examples:

Example 1
Input: n = 5
Binary: 101
Complement: 010
Output: 2

Example 2
Input: n = 7
Binary: 111
Complement: 000
Output: 0

Example 3
Input: n = 10
Binary: 1010
Complement: 0101
Output: 5

Edge Case:
Input: 0
Binary: 0
Complement: 1

Constraints:
0 <= n < 10^9

Approach:
1. Create a mask consisting of all 1s that is the same length as the binary representation of n.
2. XOR the number with the mask to flip all its bits.
3. Special case: if n = 0, return 1.

Time Complexity: O(log n)
Space Complexity: O(1)
*/

import java.util.Scanner;

public class ComplementOfBase10Integer {

    public static int bitwiseComplement(int n) {

        // Edge case: binary representation of 0 is "0"
        // flipping it gives "1"
        if (n == 0) {
            return 1;
        }

        int mask = 1;

        /*
        Build a mask of all 1s that covers the binary length of n.
        Example:
        n = 10 (1010)
        mask should become 1111
        */
        while (mask < n) {
            mask = (mask << 1) | 1;
        }

        /*
        XOR with mask flips all bits of n
        Example:
        n    = 1010
        mask = 1111
        XOR  = 0101
        */
        return n ^ mask;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter integer n: ");
        int n = sc.nextInt();

        int result = bitwiseComplement(n);

        System.out.println("Complement of the number: " + result);

        sc.close();
    }
}