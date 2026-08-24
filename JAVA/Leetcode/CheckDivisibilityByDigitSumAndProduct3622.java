/*
 * Problem Statement:
 * Given a positive integer n, determine whether n is divisible by the sum of its digit sum and digit product:
 * total = (digit sum of n) + (digit product of n)
 * Return true if n % total == 0, otherwise return false.
 */

/*
 * Approach: Single-Pass Digit Extraction (O(log10(N)) Time, O(1) Space)
 * 1. Initialize `digitSum = 0` and `digitProduct = 1`.
 * 2. Extract digits of `n` one by one using `% 10` and `/ 10`:
 *    - Add each digit to `digitSum`.
 *    - Multiply each digit into `digitProduct`.
 * 3. Compute `divisor = digitSum + digitProduct`.
 * 4. Return `n % divisor == 0`.
 */

import java.util.Scanner;

public class CheckDivisibilityByDigitSumAndProduct3622 {
    public boolean checkDivisibility(int n) {
        int temp = n;
        int digitSum = 0;
        int digitProduct = 1;

        // Extract each digit and accumulate sum and product
        while (temp > 0) {
            int digit = temp % 10;
            digitSum += digit;
            digitProduct *= digit;
            temp /= 10;
        }

        int totalDivisor = digitSum + digitProduct;
        return n % totalDivisor == 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter positive integer n:");
        int nParam = scanner.nextInt();

        CheckDivisibilityByDigitSumAndProduct3622 solver = new CheckDivisibilityByDigitSumAndProduct3622();
        boolean isDivisible = solver.checkDivisibility(nParam);

        System.out.println("Is " + nParam + " divisible by (digit sum + digit product): " + isDivisible);
        scanner.close();
    }
}