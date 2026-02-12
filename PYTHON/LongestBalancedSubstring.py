"""
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
"""


class LongestBalancedSubstring:

    # Function to find longest balanced substring
    def longest_balanced(self, s: str) -> int:
        n = len(s)
        max_len = 0  # Stores best answer found

        # Fix starting index of substring
        for i in range(n):

            # Frequency array for 26 lowercase letters
            count = [0] * 26

            distinct = 0  # Number of distinct characters
            max_freq = 0  # Highest frequency among characters

            # Expand substring from i → j
            for j in range(i, n):

                # Convert character to index (a=0, b=1, ..., z=25)
                char_index = ord(s[j]) - ord('a')

                # If character appears first time → increase distinct count
                if count[char_index] == 0:
                    distinct += 1

                # Increase frequency of this character
                count[char_index] += 1

                # Track maximum frequency among characters
                if count[char_index] > max_freq:
                    max_freq = count[char_index]

                """
                Optimization logic:

                If all distinct characters appear SAME number of times,
                then:

                substring length = max_freq × distinct

                Why this works:
                aabb → distinct=2, max_freq=2 → length=4 → balanced
                abc  → distinct=3, max_freq=1 → length=3 → balanced
                abbc → distinct=3, max_freq=2 → length=4 → NOT balanced

                So instead of comparing all frequencies,
                we check this math condition.
                """

                if max_freq * distinct == (j - i + 1):
                    current_len = j - i + 1

                    # Update best answer
                    if current_len > max_len:
                        max_len = current_len

        return max_len


# -------- USER INPUT + DRIVER CODE --------

if __name__ == "__main__":
    s = input("Enter a lowercase string: ")

    solver = LongestBalancedSubstring()
    result = solver.longest_balanced(s)

    print("Longest balanced substring length:", result)
