/*
 * Problem Statement:
 * You are given an integer n.
 * Form a new integer x by concatenating all the non-zero digits of n in their original order.
 * If there are no non-zero digits, x = 0.
 * Let sum be the sum of digits in x.
 * Return an integer representing the value of x * sum.
 */

/*
 * Approach: Right-to-Left Digit Extraction
 * To retain the original relative order while processing digits from right to left using modulo 10:
 * 1. Maintain a `multiplier` starting at 1. Every time a non-zero digit is found, append it to 
 * the left of `x` via: `x = digit * multiplier + x`, then scale `multiplier *= 10`.
 * 2. Simultaneously accumulate the extracted non-zero values into a running `sum`.
 * 3. Return `x * sum`. This completes in O(log10(N)) time and O(1) space.
 */
import java.util.Scanner;

public class NonZeroDigitConcatenate3754 {
    public long sumAndMultiply(int n) {
        long x = 0;
        long sum = 0;
        long multiplier = 1;
        
        // Loop to peel digits from the right side to the left side
        while (n > 0) {
            int digit = n % 10;
            if (digit != 0) {
                // Prepend the current digit to the left side of x
                x = digit * multiplier + x;
                multiplier *= 10;
                sum += digit;
            }
            n /= 10;
        }
        
        return x * sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter integer n:");
        int n = scanner.nextInt();
        
        NonZeroDigitConcatenate3754 solver = new NonZeroDigitConcatenate3754();
        long result = solver.sumAndMultiply(n);
        System.out.println("Result (x * sum): " + result);
        
        scanner.close();
    }
}
