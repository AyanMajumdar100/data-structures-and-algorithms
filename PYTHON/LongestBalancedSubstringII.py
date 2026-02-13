"""
3714. Longest Balanced Substring II

You are given a string s consisting only of the characters 'a', 'b', and 'c'.

A substring of s is called balanced if all distinct characters in the substring
appear the same number of times.

Return the length of the longest balanced substring of s.

Examples:
Input:  s = "abbac"
Output: 4
Explanation: "abba" → 'a' and 'b' both appear 2 times.

Input:  s = "aabcc"
Output: 3
Explanation: "abc" → 'a', 'b', 'c' appear 1 time each.

Input:  s = "aba"
Output: 2
Explanation: "ab" or "ba".

Constraints:
1 <= s.length <= 100000
s contains only 'a', 'b', 'c'.
"""

class LongestBalancedSubstringII:

    def longestBalanced(self, s: str) -> int:
        n = len(s)

        # If string is not empty, minimum balanced substring length is 1
        ans = 1 if n > 0 else 0

        # ---------------------------------------------------
        # CASE 1: Only ONE distinct character
        # Example: "aaaa"
        # Longest balanced substring is longest continuous run
        # ---------------------------------------------------
        run = 1
        for i in range(1, n):
            if s[i] == s[i - 1]:
                run += 1
            else:
                run = 1
            ans = max(ans, run)

        # ---------------------------------------------------
        # CASE 2: Exactly TWO distinct characters
        # For each pair among (a,b), (a,c), (b,c)
        #
        # Track prefix difference:
        # count(x) - count(y)
        #
        # If same difference appears again,
        # substring between them has equal counts.
        # ---------------------------------------------------
        for x in range(3):
            for y in range(x + 1, 3):

                diff = 0
                first = {0: -1}

                for i, ch in enumerate(s):
                    if ch == chr(ord('a') + x):
                        diff += 1
                    elif ch == chr(ord('a') + y):
                        diff -= 1
                    else:
                        # Reset when third character appears
                        diff = 0
                        first = {0: i}
                        continue

                    if diff in first:
                        ans = max(ans, i - first[diff])
                    else:
                        first[diff] = i

        # ---------------------------------------------------
        # CASE 3: All THREE characters present
        # Balanced means:
        # count(a) = count(b) = count(c)
        #
        # Store prefix differences:
        # (b - a, c - a)
        #
        # If same pair repeats → substring balanced
        # ---------------------------------------------------
        ca = cb = cc = 0
        seen = {(0, 0): -1}

        for i, ch in enumerate(s):
            if ch == 'a':
                ca += 1
            elif ch == 'b':
                cb += 1
            else:
                cc += 1

            key = (cb - ca, cc - ca)

            if key in seen:
                ans = max(ans, i - seen[key])
            else:
                seen[key] = i

        return ans


# ---------------- USER INPUT DRIVER ----------------

if __name__ == "__main__":
    s = input("Enter a string containing only a, b, c: ")

    solver = LongestBalancedSubstringII()
    result = solver.longestBalanced(s)

    print("Length of longest balanced substring:", result)
