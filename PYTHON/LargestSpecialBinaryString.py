"""
761. Special Binary String

A special binary string satisfies:
1) number of '1' == number of '0'
2) every prefix has count('1') >= count('0')

You can swap two consecutive special substrings.

Return the lexicographically largest possible string.

Example 1:
Input:  s = "11011000"
Output: "11100100"

Example 2:
Input:  s = "10"
Output: "10"

Constraints:
1 <= len(s) <= 50
s is guaranteed to be special
"""

class LargestSpecialBinaryString:

    def makeLargestSpecial(self, s: str) -> str:

        # I track balance of 1's and 0's
        count = 0

        start = 0
        parts = []

        # I split string into primitive special substrings
        for i, ch in enumerate(s):

            if ch == '1':
                count += 1
            else:
                count -= 1

            # When balance returns to zero → valid special substring
            if count == 0:

                # I recursively optimize the inner part
                inner = self.makeLargestSpecial(s[start + 1:i])

                # Wrap with outer 1 and 0
                parts.append("1" + inner + "0")

                start = i + 1

        # I sort in descending order to maximize lexicographic value
        parts.sort(reverse=True)

        return "".join(parts)


# ---------- USER INPUT DRIVER ----------
if __name__ == "__main__":
    s = input("Enter special binary string: ")

    solver = LargestSpecialBinaryString()
    result = solver.makeLargestSpecial(s)

    print("Lexicographically largest string:", result)