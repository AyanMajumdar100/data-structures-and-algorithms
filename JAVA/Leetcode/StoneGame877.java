/*
 * Problem Statement:
 * Alice and Bob play a game with an EVEN number of stone piles arranged in a row.
 * The total number of stones is ODD, so there are no ties.
 * Alice goes first and takes a pile from either end. Bob follows under the same rules.
 * Return true if Alice wins, assuming optimal play.
 */

/*
 * Approach: Parity Strategy / Game Theory Mathematical Proof
 * 1. Mathematical Guarantee (O(1) Time & Space):
 *    - The total number of piles N is EVEN.
 *    - Piles can be partitioned into two distinct parity groups based on index:
 *      Group 1 (Odd-indexed piles):  piles[0], piles[2], piles[4], ...
 *      Group 2 (Even-indexed piles): piles[1], piles[3], piles[5], ...
 *    - Since the total sum of all stones across both groups is ODD, one group must strictly 
 *      contain more stones than the other (Sum(Odd) != Sum(Even)).
 * 2. Strategy Control:
 *    - Alice moves first and can choose whether to force taking ALL odd-indexed piles or ALL 
 *      even-indexed piles:
 *      * If she wants all even-indexed piles (0, 2, ...), she takes `piles[0]`. Bob is left 
 *        choosing between `piles[1]` and `piles[N-1]` (both odd indices).
 *      * If she wants all odd-indexed piles (1, 3, ...), she takes `piles[N-1]`. Bob is left 
 *        choosing between `piles[0]` and `piles[N-2]` (both even indices).
 *    - Thus, Alice calculates `Sum(Even)` and `Sum(Odd)` before her first move and chooses 
 *      the strictly larger group. Bob is forced to take whatever remains.
 * 3. Conclusion:
 *    Alice can ALWAYS force a win. Simply returning `true` is mathematically optimal.
 */

import java.util.Scanner;

public class StoneGame877 {
    public boolean stoneGame(int[] piles) {
        // Alice always wins due to the parity advantage on even-sized arrays
        return true;
    }

    public static void main(String[] args) {
        Scanner stoneScanner = new Scanner(System.in);
        System.out.println("Enter stone piles separated by space:");
        String rawInputString = stoneScanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Alice wins: true");
            return;
        }

        String[] tokens = rawInputString.split("\\s+");
        int[] userPilesArray = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            userPilesArray[i] = Integer.parseInt(tokens[i]);
        }

        StoneGame877 gameSolver = new StoneGame877();
        boolean result = gameSolver.stoneGame(userPilesArray);

        System.out.println("Alice wins: " + result);
        stoneScanner.close();
    }
}