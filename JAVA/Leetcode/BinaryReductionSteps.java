/*
Problem: Number of Steps to Reduce a Number in Binary Representation to One

You are given a binary string s representing a positive integer.

Rules to reduce the number to 1:
- If number is even → divide by 2
- If number is odd → add 1

Return the number of steps required.

Example:
Input: s = "1101"
Output: 6

Input: s = "10"
Output: 1

Input: s = "1"
Output: 0
*/

import java.util.*;

class BinaryReductionSteps {

    /*
    Beginner-friendly idea:

    Instead of converting binary to decimal (which may overflow),
    we simulate operations directly on the binary string.

    We move from right → left:
    - If current bit + carry = 1 → odd → add 1 (creates carry) then divide
    - Otherwise → even → just divide by 2

    Carry represents the effect of previous "+1" operations.
    */
    public static int numSteps(String s) {
        int steps = 0;
        int carry = 0;

        // Start from last bit and move toward MSB (skip index 0)
        for (int i = s.length() - 1; i > 0; i--) {

            // Current bit + carry determines odd/even
            if (s.charAt(i) - '0' + carry == 1) {
                // Odd case → add 1 then divide
                steps += 2;
                carry = 1;
            } else {
                // Even case → only divide
                steps += 1;
            }
        }

        // If carry remains, one extra step is needed
        return steps + carry;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User enters binary string
        String s = scanner.nextLine().trim();

        int result = numSteps(s);
        System.out.println(result);

        scanner.close();
    }
}