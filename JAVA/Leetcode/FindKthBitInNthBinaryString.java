/*
1545. Find Kth Bit in Nth Binary String

Given two positive integers n and k, the binary string Sn is formed as follows:

S1 = "0"
Si = S(i - 1) + "1" + reverse(invert(S(i - 1))) for i > 1

Where:
+ denotes concatenation
reverse(x) returns reversed string
invert(x) flips bits (0 -> 1, 1 -> 0)

Return the kth bit in Sn (1-based indexing).

Constraints:
1 <= n <= 20
1 <= k <= 2^n - 1
*/

import java.util.Scanner;

public class FindKthBitInNthBinaryString {

    public static char findKthBit(int n, int k) {

        int invertCount = 0;

        // Length of Sn = 2^n - 1
        int length = (1 << n) - 1;

        while (k > 1) {

            // Middle index of current string
            int mid = length / 2 + 1;

            // If k is exactly middle, answer is always '1'
            if (k == mid) {
                // If inverted odd times, flip the result
                return (invertCount % 2 == 0) ? '1' : '0';
            }

            // If k lies in right half
            if (k > mid) {
                // Mirror index to left half
                k = length - k + 1;

                // Since right half is inverted, increase inversion count
                invertCount++;
            }

            // Reduce length to previous string S(n-1)
            length = length / 2;
        }

        // Base case: first bit of S1 is always '0'
        return (invertCount % 2 == 0) ? '0' : '1';
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking user input
        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.print("Enter k: ");
        int k = sc.nextInt();

        char result = findKthBit(n, k);

        System.out.println("Kth Bit: " + result);

        sc.close();
    }
}