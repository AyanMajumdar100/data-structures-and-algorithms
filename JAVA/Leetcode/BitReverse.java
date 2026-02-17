/*
190. Reverse Bits

Reverse bits of a given 32-bit unsigned integer.

Examples:
Input:  n = 43261596
Output: 964176192

Input:  n = 2147483644
Output: 1073741822

Constraints:
0 <= n <= 2^31 - 2
n is even.

Follow-up:
If this function is called many times, how would you optimize it?
*/

import java.util.*;

class BitReverse {

    public int reverseBits(int n) {

        // I will store the reversed number here
        int result = 0;

        // I process exactly 32 bits (since integer is 32-bit)
        for (int i = 0; i < 32; i++) {

            // Step 1: shift result left to make space
            result = result << 1;

            // Step 2: take last bit of n and add it to result
            result = result | (n & 1);

            // Step 3: shift n right (unsigned shift)
            // so next bit moves to last position
            n >>>= 1;
        }

        return result;
    }

    // ---------------- USER INPUT DRIVER ----------------

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter an integer:");
        int n = sc.nextInt();

        final BitReverse solver = new BitReverse();
        int result = solver.reverseBits(n);

        System.out.println("Reversed bits result: " + result);

        sc.close();
    }
}
