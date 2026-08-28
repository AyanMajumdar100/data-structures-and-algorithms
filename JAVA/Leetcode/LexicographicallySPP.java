/*
 * Problem Statement:
 * Given two strings s and target of length n consisting of lowercase English letters.
 * Return the lexicographically smallest palindromic permutation of s that is strictly greater than target.
 * If no such permutation exists, return an empty string "".
 */

/*
 * Approach: Palindrome Halving + Greedy Prefix Matching with Backtracking (O(N * 26) Time, O(26) Space)
 * 1. Palindrome Feasibility Check:
 *    - Count character frequencies of s.
 *    - An odd-length string must have at most 1 odd-frequency character (the middle character).
 *    - An even-length string must have 0 odd-frequency characters.
 *    - If these conditions are not met, return "".
 * 2. Half-String Available Counts:
 *    - The first half of the palindrome `H` of length `m = n / 2` uses `freq[c] / 2` of each character.
 * 3. Matching Prefix with Target:
 *    - Greedily match `H` with `target[0...m-1]` as long as matching characters are available.
 *    - Let `maxMatch` be the length of the matched prefix.
 * 4. Backtracking from Right to Left:
 *    - Case A (i == m): All characters in the first half match `target[0...m-1]`.
 *      Build the full palindrome `P = H + midChar + reverse(H)`. If `P > target`, it is valid and minimal.
 *    - Case B (i < m): Try picking the smallest character `nextChar > target[i]` from available pool.
 *      If found:
 *      * Form the prefix `H = target[0...i-1] + nextChar`.
 *      * Greedily append all remaining available characters in ascending order to `H`.
 *      * Construct full palindrome `P = H + midChar + reverse(H)` and return.
 * 5. Return "" if no valid palindrome permutation greater than target can be formed.
 */

import java.util.Scanner;

public class LexicographicallySPP {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int oddCount = 0;
        char midChar = (char) -1;
        for (int c = 0; c < 26; c++) {
            if (freq[c] % 2 != 0) {
                oddCount++;
                midChar = (char) (c + 'a');
            }
        }

        // Validate palindrome condition
        if (oddCount > 1 || (n % 2 == 0 && oddCount > 0)) {
            return "";
        }

        // Half-frequencies available for the first half
        int[] avail = new int[26];
        for (int c = 0; c < 26; c++) {
            avail[c] = freq[c] / 2;
        }

        int m = n / 2;
        int maxMatch = 0;

        // Greedily match the first half with target
        while (maxMatch < m && avail[target.charAt(maxMatch) - 'a'] > 0) {
            avail[target.charAt(maxMatch) - 'a']--;
            maxMatch++;
        }

        // Backtrack to find the optimal bifurcation point
        for (int i = maxMatch; i >= 0; i--) {
            if (i < maxMatch) {
                avail[target.charAt(i) - 'a']++;
            }

            if (i == m) {
                // Exact prefix match for first half; check if resulting palindrome is strictly greater
                String H = target.substring(0, m);
                String P = buildPalindrome(H, midChar);
                if (P.compareTo(target) > 0) {
                    return P;
                }
            } else {
                int targetChar = target.charAt(i) - 'a';
                int nextChar = -1;

                // Find smallest character strictly greater than target[i]
                for (int c = targetChar + 1; c < 26; c++) {
                    if (avail[c] > 0) {
                        nextChar = c;
                        break;
                    }
                }

                if (nextChar != -1) {
                    StringBuilder H = new StringBuilder();
                    H.append(target, 0, i);
                    H.append((char) (nextChar + 'a'));
                    avail[nextChar]--;

                    // Fill remainder in ascending order
                    for (int c = 0; c < 26; c++) {
                        while (avail[c] > 0) {
                            H.append((char) (c + 'a'));
                            avail[c]--;
                        }
                    }
                    return buildPalindrome(H.toString(), midChar);
                }
            }
        }

        return "";
    }

    private String buildPalindrome(String half, char midChar) {
        StringBuilder sb = new StringBuilder(half);
        if (midChar != (char) -1) {
            sb.append(midChar);
        }
        for (int k = half.length() - 1; k >= 0; k--) {
            sb.append(half.charAt(k));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        System.out.println("Enter string target:");
        String targetParam = scanner.nextLine().trim();

        LexicographicallySmallestPalindromicPermutation3734 solver = 
                new LexicographicallySmallestPalindromicPermutation3734();
        String result = solver.lexPalindromicPermutation(sParam, targetParam);

        System.out.println("Lexicographically smallest palindromic permutation > target: \"" + result + "\"");
        scanner.close();
    }
}
