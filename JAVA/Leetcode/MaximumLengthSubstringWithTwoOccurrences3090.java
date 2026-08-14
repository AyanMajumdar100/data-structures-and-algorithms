/*
 * Problem Statement:
 * Given a string s, return the maximum length of a substring such that 
 * it contains at most two occurrences of each character.
 */

/*
 * Approach: Two-Pointer Sliding Window + Frequency Array (O(N) Time, O(1) Space)
 * 1. Maintain a dynamic sliding window [left, right] and a 26-element array `freq` 
 *    tracking character counts in the current window.
 * 2. Expand the `right` pointer to include `s[right]` and increment its frequency count.
 * 3. Whenever `freq[s[right] - 'a'] > 2`, shrink the window from `left` by decrementing 
 *    `freq[s[left] - 'a']` and incrementing `left` until the frequency condition is restored.
 * 4. Update `maxLength = max(maxLength, right - left + 1)`.
 */

import java.util.Scanner;

public class MaximumLengthSubstringWithTwoOccurrences3090 {
    public int maximumLengthSubstring(String s) {
        int[] freq = new int[26];
        int left = 0;
        int maxLength = 0;

        // Expand the right boundary across the string
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            freq[currentChar - 'a']++;

            // Shrink window from the left if any character occurs more than 2 times
            while (freq[currentChar - 'a'] > 2) {
                char leftChar = s.charAt(left);
                freq[leftChar - 'a']--;
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String userStringParam = scanner.nextLine().trim();

        MaximumLengthSubstringWithTwoOccurrences3090 solverInstance = new MaximumLengthSubstringWithTwoOccurrences3090();
        int maxSubstringLen = solverInstance.maximumLengthSubstring(userStringParam);

        System.out.println("Maximum length of substring with at most two occurrences: " + maxSubstringLen);
        scanner.close();
    }
}
