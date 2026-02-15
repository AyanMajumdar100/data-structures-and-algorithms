"""
67. Add Binary

Given two binary strings a and b, return their sum as a binary string.

A binary string contains only '0' and '1'.

Examples:
Input:  a = "11", b = "1"
Output: "100"

Input:  a = "1010", b = "1011"
Output: "10101"

Constraints:
1 <= len(a), len(b) <= 10^4
a and b contain only '0' or '1'
No leading zeros except the number zero itself
"""


class BinaryAdder:

    def addBinary(self, a: str, b: str) -> str:
        # I will build the result from right to left,
        # exactly like manual binary addition.
        result = []

        # I start from the last character of both strings
        i = len(a) - 1
        j = len(b) - 1

        # Carry stores overflow when sum >= 2
        carry = 0

        # I continue while digits remain OR carry still exists
        while i >= 0 or j >= 0 or carry:

            # If there is a digit in a, I add it to carry
            if i >= 0:
                carry += int(a[i])
                i -= 1

            # If there is a digit in b, I add it to carry
            if j >= 0:
                carry += int(b[j])
                j -= 1

            # Current binary digit is carry % 2
            result.append(str(carry % 2))

            # Update carry (either 0 or 1)
            carry //= 2

        # I reverse because digits were added backwards
        return ''.join(reversed(result))


# ---------------- USER INPUT DRIVER ----------------

if __name__ == "__main__":
    a = input("Enter first binary string: ")
    b = input("Enter second binary string: ")

    solver = BinaryAdder()
    ans = solver.addBinary(a, b)

    print("Binary Sum:", ans)
