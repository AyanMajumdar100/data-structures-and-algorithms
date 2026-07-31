/*
 * Problem Statement:
 * You are given a string word containing lowercase English letters.
 * You can remap telephone keypad keys 2 through 9 (8 keys total) to distinct collections of letters.
 * Return the minimum number of key pushes needed to type word.
 */

/*
 * Approach: Frequency-Based Greedy Assignment with Sorting (O(N) Time, O(1) Auxiliary Space)
 * 1. Count Frequencies:
 *    Unlike Part I where letters are distinct, here characters can repeat. Calculate the frequency of each 
 *    character in `word` using a 26-element array.
 * 2. Greedy Priority:
 *    To minimize total pushes, we must assign the most frequently occurring characters to the cheapest key slots 
 *    (costing 1 push), followed by the next most frequent to 2-push slots, and so on.
 * 3. Sorting & Slot Allocation:
 *    - Sort frequencies in ascending order and iterate backwards from the highest frequency.
 *    - The first 8 most frequent letters cost 1 push each.
 *    - The next 8 cost 2 pushes each.
 *    - The next 8 cost 3 pushes each.
 *    - The remaining 2 cost 4 pushes each.
 *    - Multiply each character's frequency by its assigned push cost `(count / 8) + 1` and sum them up.
 */

import java.util.Arrays;
import java.util.Scanner;

public class MinimumKeypadPushesII3016 {
    public int minimumPushes(String word) {
        // Step 1: Count character frequencies
        int[] characterFrequencies = new int[26];
        for (int i = 0; i < word.length(); i++) {
            characterFrequencies[word.charAt(i) - 'a']++;
        }

        // Step 2: Sort frequencies in ascending order
        Arrays.sort(characterFrequencies);

        int totalKeyPushes = 0;
        int mappedLetterCount = 0;

        // Step 3: Greedily map characters starting from the most frequent
        for (int i = 25; i >= 0; i--) {
            if (characterFrequencies[i] == 0) break;

            int pushesPerPress = (mappedLetterCount / 8) + 1;
            totalKeyPushes += characterFrequencies[i] * pushesPerPress;
            mappedLetterCount++;
        }

        return totalKeyPushes;
    }

    public static void main(String[] args) {
        Scanner keypadScanner = new Scanner(System.in);
        System.out.println("Enter string word:");
        String userWordParam = keypadScanner.nextLine().trim();

        MinimumKeypadPushesII3016 solverInstance = new MinimumKeypadPushesII3016();
        int totalPushesResult = solverInstance.minimumPushes(userWordParam);

        System.out.println("Minimum number of pushes needed: " + totalPushesResult);
        keypadScanner.close();
    }
}
