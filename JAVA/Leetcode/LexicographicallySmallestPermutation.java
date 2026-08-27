/*
 * Problem Statement:
 * Given two strings s and target of equal length n consisting of lowercase English letters.
 * Return the lexicographically smallest permutation of s that is strictly greater than target.
 * If no such permutation exists, return an empty string "".
 */

/*
 * Approach: Greedy Longest Matching Prefix with Backtracking (O(N * 26) Time, O(26) Space)
 * 1. Count character frequencies in s.
 * 2. Find the maximum matching prefix between s and target:
 *    - Greedily match as many characters of target as possible using available characters in s.
 *    - Let `matchLen` be the length of this matched prefix.
 * 3. Search for the bifurcation point `i` from `min(n - 1, matchLen)` down to 0:
 *    - If `i < matchLen`, backtrack target[i] by restoring its count to `avail`.
 *    - Look for the smallest available character `nextChar > target[i]`.
 *    - If found:
 *      * Form the prefix `target[0...i-1]`.
 *      * Append `nextChar` at index `i`.
 *      * Greedily append all remaining available characters in ascending alphabetical order ('a' through 'z').
 *      * Return this constructed string immediately (guaranteed to be the smallest valid permutation).
 * 4. If no such index `i` yields a valid larger character, return "".
 */

import java.util.Scanner;

public class LexicographicallySmallestPermutation {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] sCounts = new int[26];
        for (char c : s.toCharArray()) {
            sCounts[c - 'a']++;
        }

        // Step 1: Find the maximum common prefix match with target
        int[] avail = sCounts.clone();
        int matchLen = 0;
        while (matchLen < n && avail[target.charAt(matchLen) - 'a'] > 0) {
            avail[target.charAt(matchLen) - 'a']--;
            matchLen++;
        }

        // Step 2: Try splitting at index i from right to left
        for (int i = Math.min(n - 1, matchLen); i >= 0; i--) {
            if (i < matchLen) {
                avail[target.charAt(i) - 'a']++;
            }

            int targetChar = target.charAt(i) - 'a';
            int nextChar = -1;

            // Find the smallest character strictly greater than target[i]
            for (int c = targetChar + 1; c < 26; c++) {
                if (avail[c] > 0) {
                    nextChar = c;
                    break;
                }
            }

            // Step 3: If found, construct the minimal suffix
            if (nextChar != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(target, 0, i);
                sb.append((char) (nextChar + 'a'));
                avail[nextChar]--;

                for (int c = 0; c < 26; c++) {
                    while (avail[c] > 0) {
                        sb.append((char) (c + 'a'));
                        avail[c]--;
                    }
                }
                return sb.toString();
            }
        }

        return "";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        System.out.println("Enter string target:");
        String targetParam = scanner.nextLine().trim();

        LexicographicallySmallestPermutationGreaterThanTarget3720 solver = 
                new LexicographicallySmallestPermutationGreaterThanTarget3720();
        String result = solver.lexGreaterPermutation(sParam, targetParam);

        System.out.println("Lexicographically smallest permutation > target: \"" + result + "\"");
        scanner.close();
    }
}
