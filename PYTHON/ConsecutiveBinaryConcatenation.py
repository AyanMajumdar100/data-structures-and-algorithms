"""
Problem: Concatenation of Consecutive Binary Numbers

Given an integer n, form a binary string by concatenating
binary representations of numbers from 1 to n.
Return its decimal value modulo 10^9 + 7.

Example:
Input: n = 1
Output: 1

Input: n = 3
Output: 27

Input: n = 12
Output: 505379714
"""

import sys


class ConsecutiveBinaryConcatenation:
    """
    Beginner-friendly explanation:

    We keep building the final number.
    To append a binary number:
    shift current result left by its bit length,
    then add the number.

    Whenever i is a power of 2,
    binary length increases by 1.
    """

    @staticmethod
    def concatenated_binary(n: int) -> int:
        ans = 0
        mod = 1_000_000_007
        length = 0

        for i in range(1, n + 1):

            # Check if i is power of 2 → bit length increases
            if (i & (i - 1)) == 0:
                length += 1

            # Shift left and append number
            ans = ((ans << length) | i) % mod

        return ans


# User input
n = int(sys.stdin.readline().strip())

result = ConsecutiveBinaryConcatenation.concatenated_binary(n)
print(result)