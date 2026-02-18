"""
693. Binary Number with Alternating Bits

Given a positive integer, check whether it has alternating bits:
if two adjacent bits always have different values.

Examples:
Input: n = 5
Output: True
Explanation: Binary of 5 is 101 → alternating

Input: n = 7
Output: False
Explanation: Binary of 7 is 111 → not alternating

Input: n = 11
Output: False
Explanation: Binary of 11 is 1011 → not alternating

Constraints:
1 <= n <= 2^31 - 1
"""

class AlternatingBitsChecker:

    def hasAlternatingBits(self, n: int) -> bool:
        # I XOR the number with itself right-shifted by 1.
        # If bits were alternating → result becomes all 1s.
        n = n ^ (n >> 1)

        # If n is like 111...111,
        # then n & (n + 1) equals 0.
        return (n & (n + 1)) == 0


# ---------- USER INPUT DRIVER ----------
if __name__ == "__main__":
    n = int(input("Enter a positive integer: "))

    solver = AlternatingBitsChecker()
    result = solver.hasAlternatingBits(n)

    print("Has alternating bits:", result)
