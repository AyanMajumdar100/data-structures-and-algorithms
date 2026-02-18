/*
693. Binary Number with Alternating Bits

Given a positive integer, check whether it has alternating bits:
if two adjacent bits always have different values.

Examples:
Input: n = 5
Output: true
Explanation: Binary of 5 is 101 → alternating

Input: n = 7
Output: false
Explanation: Binary of 7 is 111 → not alternating

Input: n = 11
Output: false
Explanation: Binary of 11 is 1011 → not alternating

Constraints:
1 <= n <= 2^31 - 1
*/

import java.util.*;

class AlternatingBitsChecker {

    public boolean hasAlternatingBits(int n) {

        // I XOR the number with itself right-shifted by 1.
        // If bits were alternating → result becomes all 1s.
        n = n ^ (n >> 1);

        // If n is of form 111...111
        // then n & (n + 1) will be 0.
        return (n & (n + 1)) == 0;
    }

    // ---------- USER INPUT DRIVER ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a positive integer: ");
        int n = sc.nextInt();

        AlternatingBitsChecker solver = new AlternatingBitsChecker();
        boolean result = solver.hasAlternatingBits(n);

        System.out.println("Has alternating bits: " + result);

        sc.close();
    }
}
