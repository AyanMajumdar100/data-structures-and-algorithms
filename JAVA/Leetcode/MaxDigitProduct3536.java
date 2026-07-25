/*
 * Problem Statement:
 * You are given a positive integer n.
 * Return the maximum product of any two digits in n.
 * Note: You may use the same digit twice if it appears more than once in n.
 */

/*
 * Approach: Single-Pass Top Two Maxima Tracking (O(log10(N)) Time, O(1) Space)
 * 1. Initialize `max1` and `max2` to track the largest and second largest digits encountered so far.
 * 2. Extract digits one by one using modulo 10 (`n % 10`) while shifting right via integer division (`n /= 10`).
 * 3. Update top digit candidates dynamically:
 *    - If `digit > max1`: Shift `max1` to `max2`, then store `digit` in `max1`.
 *    - Else if `digit > max2`: Update `max2` with `digit`.
 * 4. Return `max1 * max2`.
 */

import java.util.Scanner;

public class MaxDigitProduct3536 {
    public int maxProduct(int n) {
        int max1 = 0;
        int max2 = 0;

        // Extract digits right-to-left and maintain top two maximum digits
        while (n > 0) {
            int currentDigit = n % 10;
            if (currentDigit > max1) {
                max2 = max1;
                max1 = currentDigit;
            } else if (currentDigit > max2) {
                max2 = currentDigit;
            }
            n /= 10;
        }

        return max1 * max2;
    }

    public static void main(String[] args) {
        Scanner productScanner = new Scanner(System.in);
        System.out.println("Enter integer n:");
        int userNumberParam = productScanner.nextInt();

        MaxDigitProduct3536 solverInstance = new MaxDigitProduct3536();
        int maximumProduct = solverInstance.maxProduct(userNumberParam);

        System.out.println("Maximum product of two digits: " + maximumProduct);
        productScanner.close();
    }
}