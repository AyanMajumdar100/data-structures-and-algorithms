/*
 * Problem Statement: LeetCode 3870 - Count Commas in Range
 * You are given an integer n.
 * Return the total number of commas used when writing all integers from [1, n] (inclusive)
 * in standard number formatting (a comma inserted every three digits from the right).
 */

/*
 * Approach: Prefix Magnitude Threshold Counting (O(log1000(N)) Time, O(1) Space)
 * 1. Comma contribution per number:
 *    - Numbers in [1, 999] have 0 commas.
 *    - Numbers in [1,000, 999,999] have at least 1 comma.
 *    - Numbers in [1,000,000, 999,999,999] have at least 2 commas (one at 10^3, one at 10^6).
 * 2. Prefix summation trick:
 *    - Instead of counting commas number by number:
 *      * Every integer >= 1,000 contributes its 1st comma: (n - 1,000 + 1) numbers.
 *      * Every integer >= 1,000,000 contributes its 2nd comma: (n - 1,000,000 + 1) numbers.
 *      * Every integer >= 1,000,000,000 contributes its 3rd comma: (n - 1,000,000,000 + 1) numbers.
 * 3. Loop while `limit <= n`:
 *    - Add `n - limit + 1` to total comma count.
 *    - Multiply `limit` by 1000 for the next comma tier.
 */

import java.util.Scanner;

public class CountCommasInRange3870 {
    public int countCommas(int n) {
        int count = 0;
        long limit = 1000;

        // Add 1 comma for each threshold crossed by numbers up to n
        while (n >= limit) {
            count += (int) (n - limit + 1);
            limit *= 1000;
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter integer n:");
        int nParam = scanner.nextInt();

        CountCommasInRange3870 solver = new CountCommasInRange3870();
        int result = solver.countCommas(nParam);

        System.out.println("Total number of commas used: " + result);
        scanner.close();
    }
}
