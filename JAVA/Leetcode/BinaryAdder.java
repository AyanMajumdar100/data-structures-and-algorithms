/*
67. Add Binary
Given two binary strings a and b, return their sum as a binary string.
A binary string contains only '0' and '1'.

Examples:
Input:  a = "11", b = "1"
Output: "100"

Input:  a = "1010", b = "1011"
Output: "10101"

Constraints:
1 <= a.length, b.length <= 10^4
a and b contain only '0' or '1'
No leading zeros except the number zero itself
*/

import java.util.*;

class BinaryAdder {

    public String addBinary(String a, String b) {

        // I will build the answer from right to left
        // just like normal addition.
        StringBuilder result = new StringBuilder();

        // Pointers start from the last index of both strings
        int i = a.length() - 1;
        int j = b.length() - 1;

        // Carry stores overflow when sum >= 2
        int carry = 0;

        // I continue while digits remain OR carry exists
        while (i >= 0 || j >= 0 || carry != 0) {

            // If digit exists in a, I add it to carry
            if (i >= 0) {
                carry += a.charAt(i) - '0'; // convert char to int
                i--;
            }

            // If digit exists in b, I add it to carry
            if (j >= 0) {
                carry += b.charAt(j) - '0';
                j--;
            }

            // Current binary digit is carry % 2
            // I convert it back to char and append
            result.append(carry % 2);

            // Update carry (either 0 or 1)
            carry /= 2;
        }

        // I reverse because digits were added backwards
        return result.reverse().toString();
    }

    // ---------------- USER INPUT DRIVER ----------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first binary string: ");
        String a = sc.next();

        System.out.print("Enter second binary string: ");
        String b = sc.next();

        BinaryAdder solver = new BinaryAdder();
        String sum = solver.addBinary(a, b);

        System.out.println("Binary Sum: " + sum);

        sc.close();
    }
}
