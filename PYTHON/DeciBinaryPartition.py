"""
Problem: Partitioning Into Minimum Number Of Deci-Binary Numbers

A deci-binary number contains only digits 0 or 1 (no leading zeros).

Given a decimal string n, return the minimum number of
positive deci-binary numbers needed so that their sum equals n.

Example:
Input: n = "32"
Output: 3

Input: n = "82734"
Output: 8

Input: n = "27346209830709182346"
Output: 9
"""

import sys


class DeciBinaryPartition:
    """
    Beginner-friendly idea:

    Each digit tells how many 1s are required at that position.
    Since each deci-binary number contributes at most 1,
    the answer is the maximum digit in the string.
    """

    @staticmethod
    def min_partitions(n: str) -> int:
        max_digit = 0

        for ch in n:
            digit = ord(ch) - ord('0')

            if digit > max_digit:
                max_digit = digit

                # Early exit because 9 is highest possible digit
                if max_digit == 9:
                    return 9

        return max_digit


# User input: decimal string
n = sys.stdin.readline().strip()

result = DeciBinaryPartition.min_partitions(n)
print(result)