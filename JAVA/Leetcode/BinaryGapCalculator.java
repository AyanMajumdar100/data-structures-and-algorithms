/*
868. Binary Gap

Given a positive integer n, return the longest distance between
two adjacent 1's in the binary representation of n.

Two 1's are adjacent if only 0's exist between them.
Distance = difference in bit positions.

Example 1:
Input: 22
Binary: 10110
Output: 2

Example 2:
Input: 8
Binary: 1000
Output: 0

Example 3:
Input: 5
Binary: 101
Output: 2

Constraints:
1 <= n <= 10^9
*/

import java.util.*;

class BinaryGapCalculator {

    public int binaryGap(int n) {

        int max = 0;

        // I first skip trailing zeros until I reach the first 1
        while ((n & 1) == 0) {
            n >>= 1;
        }

        // Now I scan distances between consecutive 1s
        while (n > 0) {

            n >>= 1;

            // If no more bits, I stop
            if (n == 0) break;

            int dist = 1;

            // I count zeros between two 1s
            while ((n & 1) == 0) {
                dist++;
                n >>= 1;
            }

            // I update maximum distance
            if (dist > max) max = dist;
        }

        return max;
    }

    // ---------- USER INPUT DRIVER ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        BinaryGapCalculator solver = new BinaryGapCalculator();
        int result = solver.binaryGap(n);

        System.out.println("Longest Binary Gap: " + result);

        sc.close();
    }
}