"""
696. Count Binary Substrings

Given a binary string s, return the number of non-empty substrings that:
1) contain the same number of 0's and 1's
2) all 0's and all 1's in the substring are grouped consecutively

Substrings that appear multiple times are counted multiple times.

Examples:
Input:  s = "00110011"
Output: 6

Input:  s = "10101"
Output: 4

Constraints:
1 <= s.length <= 10^5
s contains only '0' and '1'
"""

class BinarySubstringCounter:

    def countBinarySubstrings(self, s: str) -> int:

        # I track the previous group length
        prev = 0

        # Current group length starts at 1
        curr = 1

        ans = 0

        # I scan the string from index 1
        for i in range(1, len(s)):

            # If same char → extend current group
            if s[i] == s[i - 1]:
                curr += 1
            else:
                # When group changes, valid substrings depend
                # on the smaller of the two group sizes
                ans += min(prev, curr)

                # Move current group to previous
                prev = curr

                # Start new group
                curr = 1

        # I add result for the final pair of groups
        return ans + min(prev, curr)


# ---------- USER INPUT  ----------
if __name__ == "__main__":
    s = input("Enter binary string: ")

    solver = BinarySubstringCounter()
    result = solver.countBinarySubstrings(s)

    print("Number of valid substrings:", result)
