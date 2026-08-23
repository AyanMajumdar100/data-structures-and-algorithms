/*
 * Problem Statement:
 * Given an even-length string num consisting of digits and '?' characters.
 * Alice and Bob take turns replacing '?' with any digit '0'-'9'. Alice goes first.
 * Bob wins if (sum of first half == sum of second half) at the end. Alice wins if sums differ.
 * Return true if Alice wins, false if Bob wins, assuming optimal play.
 */

/*
 * Approach: Game Theory / Symmetric Round Analysis (O(N) Time, O(1) Space)
 * 1. Count Known Digit Sums & Question Marks:
 *    Let `lSum` and `rSum` be the sums of known digits on the left and right halves.
 *    Let `lq` and `rq` be the number of '?' on the left and right halves.
 * 2. Total Question Marks Parity:
 *    If the total count of '?' (`lq + rq`) is odd, Alice makes the final move and can always guarantee
 *    the sums are unequal, so Alice wins.
 * 3. Question Mark Cancellation:
 *    If there are '?' on both sides, Bob can pair every Alice move on one side with an identical digit
 *    on the opposite side. This allows canceling out `min(lq, rq)` on both halves.
 * 4. Bob's Balancing Strategy:
 *    The side with more '?' has an excess of `|rq - lq|` question marks (which must be even for Bob to have a chance).
 *    In each pair of moves on the remaining side, if Alice plays digit `d`, Bob can always counter with `9 - d`
 *    so that every pair adds exactly 9.
 *    - To balance the existing difference `lSum - rSum`, the excess question marks must provide exactly:
 *      (lSum - rSum) == (rq - lq) / 2 * 9  =>  2 * (lSum - rSum) == 9 * (rq - lq).
 * 5. Conclusion:
 *    Alice wins if and only if `2 * (lSum - rSum) != 9 * (rq - lq)`.
 */

import java.util.Scanner;

public class SumGame1927 {
    public boolean sumGame(String num) {
        int n = num.length();
        int lSum = 0, rSum = 0;
        int lq = 0, rq = 0;

        // Count digits and '?' for the left half
        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') {
                lq++;
            } else {
                lSum += num.charAt(i) - '0';
            }
        }

        // Count digits and '?' for the right half
        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') {
                rq++;
            } else {
                rSum += num.charAt(i) - '0';
            }
        }

        // Alice wins if the balance condition for Bob cannot be satisfied
        return 2 * (lSum - rSum) != 9 * (rq - lq);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter even-length string num:");
        String numString = scanner.nextLine().trim();

        SumGame1927 solver = new SumGame1927();
        boolean aliceWins = solver.sumGame(numString);

        System.out.println("Alice wins: " + aliceWins);
        scanner.close();
    }
}
