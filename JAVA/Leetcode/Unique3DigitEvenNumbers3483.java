/*
 * Problem Statement: LeetCode 3483 - Unique 3-Digit Even Numbers
 * Given an array of digits called digits, determine the total number of distinct
 * three-digit even numbers that can be formed using these digits.
 * Each copy of a digit can only be used once per number, and numbers cannot have leading zeros.
 */

/*
 * Approach: Frequency Map Verification over Range [100, 998] (O(N) Time, O(1) Space)
 * 1. Build a digit frequency table of size 10 from the input array `digits`.
 * 2. Every valid number must be a 3-digit even number, meaning it falls in the range [100, 998] step 2 (exactly 450 numbers).
 * 3. Iterate through all even numbers from 100 to 998:
 *    - Decompose the candidate number into `hundreds`, `tens`, and `ones`.
 *    - Temporarily decrement the frequency counts of these three digits.
 *    - If all three counts remain >= 0, the candidate number can be formed using the provided digits; increment `result`.
 *    - Restore (increment) the counts of the three digits before moving to the next candidate.
 * 4. Return `result`.
 */

import java.util.Scanner;

public class Unique3DigitEvenNumbers3483 {
    public int totalNumbers(int[] digits) {
        int[] counts = new int[10];
        for (int d : digits) {
            counts[d]++;
        }

        int result = 0;
        // Check every 3-digit even integer from 100 to 998
        for (int i = 100; i < 1000; i += 2) {
            int ones = i % 10;
            int tens = (i / 10) % 10;
            int hundreds = i / 100;

            counts[ones]--;
            counts[tens]--;
            counts[hundreds]--;

            if (counts[ones] >= 0 && counts[tens] >= 0 && counts[hundreds] >= 0) {
                result++;
            }

            counts[ones]++;
            counts[tens]++;
            counts[hundreds]++;
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter digits separated by space:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Total distinct 3-digit even numbers: 0");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userDigitsArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userDigitsArray[i] = Integer.parseInt(tokens[i]);
        }

        Unique3DigitEvenNumbers3483 solver = new Unique3DigitEvenNumbers3483();
        int result = solver.totalNumbers(userDigitsArray);

        System.out.println("Total distinct 3-digit even numbers: " + result);
        scanner.close();
    }
}
