/*
762. Prime Number of Set Bits in Binary Representation

Given two integers left and right, count how many numbers
in the inclusive range [left, right] have a PRIME number of set bits.

Set bits = number of 1's in binary representation.

Example 1:
Input: left = 6, right = 10
Output: 4

Example 2:
Input: left = 10, right = 15
Output: 5

Constraints:
1 <= left <= right <= 10^6
0 <= right - left <= 10^4
*/

import java.util.*;

class PrimeSetBitsCounter {

    public int countPrimeSetBits(int left, int right) {

        // This mask has bits set at prime indices:
        // primes up to 20 → 2,3,5,7,11,13,17,19
        int mask = 665772;

        int count = 0;

        // I check each number in the range
        for (int i = left; i <= right; i++) {

            // I count how many 1s exist in binary form
            int setBits = Integer.bitCount(i);

            // I check if setBits is prime using bitmask trick
            if (((1 << setBits) & mask) != 0) {
                count++;
            }
        }

        return count;
    }

    // ---------- USER INPUT DRIVER ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter left: ");
        int left = sc.nextInt();

        System.out.print("Enter right: ");
        int right = sc.nextInt();

        PrimeSetBitsCounter solver = new PrimeSetBitsCounter();
        int result = solver.countPrimeSetBits(left, right);

        System.out.println("Count of numbers with prime set bits: " + result);

        sc.close();
    }
}