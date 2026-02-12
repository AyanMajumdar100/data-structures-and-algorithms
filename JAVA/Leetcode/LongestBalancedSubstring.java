/*
## Problem: Longest Balanced Substring I

Description
You are given a string s consisting of lowercase English letters.

A substring of s is called balanced if all distinct characters
in the substring appear the same number of times.

Return the length of the longest balanced substring of s.

Example 1:
Input:  s = "abbac"
Output: 4
Explanation: "abba" → 'a' and 'b' both appear 2 times

Example 2:
Input:  s = "zzabccy"
Output: 4
Explanation: "zabc" → each character appears once

Example 3:
Input:  s = "aba"
Output: 2
Explanation: "ab" or "ba" are balanced

Constraints:
1 <= s.length <= 1000
s consists of lowercase English letters
*/

import java.util.*;

class LongestBalancedSubstring {

    // Function to find longest balanced substring
    public int longestBalanced(String s) {

        int n = s.length();
        int maxLen = 0;  // Stores best answer found

        // Fix starting index of substring
        for (int i = 0; i < n; i++) {

            // Frequency array for 26 lowercase letters
            int[] count = new int[26];

            int distinct = 0; // Number of distinct characters in substring
            int maxFreq = 0;  // Highest frequency among characters

            // Expand substring from i → j
            for (int j = i; j < n; j++) {

                // Convert character to index (a=0, b=1, ..., z=25)
                int charIdx = s.charAt(j) - 'a';

                // If character appears first time → increase distinct count
                if (count[charIdx] == 0) distinct++;

                // Increase frequency of this character
                count[charIdx]++;

                // Track maximum frequency among all characters
                if (count[charIdx] > maxFreq) {
                    maxFreq = count[charIdx];
                }

                /*
                Optimization condition:

                If all distinct characters appear SAME number of times,
                then:

                substring length = maxFreq × distinct

                Why?
                Example:
                aabb → distinct=2, maxFreq=2 → length=4 → balanced
                abc  → distinct=3, maxFreq=1 → length=3 → balanced
                abbc → distinct=3, maxFreq=2 → length=4 → NOT balanced

                So we check this mathematical condition instead of
                comparing all frequencies — faster and efficient.
                */

                if (maxFreq * distinct == (j - i + 1)) {

                    int currentLen = j - i + 1;

                    // Update best answer
                    if (currentLen > maxLen) {
                        maxLen = currentLen;
                    }
                }
            }
        }

        return maxLen;
    }


    // -------- USER INPUT + DRIVER CODE --------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a lowercase string:");
        String s = sc.nextLine();

        LongestBalancedSubstring obj = new LongestBalancedSubstring();
        int result = obj.longestBalanced(s);

        System.out.println("Longest balanced substring length: " + result);

        sc.close();
    }
}
