/*
 * Problem Statement:
 * You are given a string word containing distinct lowercase English letters.
 * You can remap telephone keypad keys 2 through 9 (8 keys total) to distinct collections of letters.
 * Return the minimum number of key pushes needed to type word.
 */

/*
 * Approach: Greedily Grouping Distinct Letters Across 8 Keypad Keys (O(N) Time, O(1) Space)
 * 1. Keypad Keys Available: Keys 2 to 9 provide exactly 8 available keys.
 * 2. Key Press Cost Breakdown:
 *    - The first 8 letters mapped will each cost 1 push (assigned as the 1st letter on keys 2..9).
 *    - The next 8 letters mapped will each cost 2 pushes (assigned as the 2nd letter on keys 2..9).
 *    - The next 8 letters mapped will each cost 3 pushes (assigned as the 3rd letter on keys 2..9).
 *    - The remaining letters will each cost 4 pushes.
 * 3. Calculation:
 *    - For the i-th character (0-indexed), its push cost is given by `(i / 8) + 1`.
 *    - Summing this up over all characters gives the minimal total pushes required.
 */

import java.util.Scanner;

public class MinimumKeypadPushesI3014 {
    public int minimumPushes(String word) {
        int wordLength = word.length();
        int totalKeyPushes = 0;

        // Map characters across 8 available keypad keys greedily
        for (int i = 0; i < wordLength; i++) {
            totalKeyPushes += (i / 8) + 1;
        }

        return totalKeyPushes;
    }

    public static void main(String[] args) {
        Scanner keypadScanner = new Scanner(System.in);
        System.out.println("Enter string word containing distinct lowercase English letters:");
        String userWordParam = keypadScanner.nextLine().trim();

        MinimumKeypadPushesI3014 solverInstance = new MinimumKeypadPushesI3014();
        int totalPushesResult = solverInstance.minimumPushes(userWordParam);

        System.out.println("Minimum number of pushes needed: " + totalPushesResult);
        keypadScanner.close();
    }
}
