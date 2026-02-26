"""
Problem: Number of Steps to Reduce a Number in Binary Representation to One

You are given a binary string s representing a positive integer.

Rules to reduce the number to 1:
- If number is even → divide by 2
- If number is odd → add 1

Return the number of steps required.

Example:
Input: s = "1101"
Output: 6

Input: s = "10"
Output: 1

Input: s = "1"
Output: 0
"""

import sys


class BinaryReductionSteps:
    """
    Beginner-friendly idea:

    We simulate operations directly on the binary string
    instead of converting to decimal.

    Traverse from right → left:
    - If bit + carry = 1 → odd → add 1 then divide
    - Otherwise → even → only divide

    Carry represents overflow caused by "+1".
    """

    @staticmethod
    def num_steps(s: str) -> int:
        steps = 0
        carry = 0

        # Traverse from least significant bit to most significant
        for i in range(len(s) - 1, 0, -1):

            if int(s[i]) + carry == 1:
                # Odd → add 1 then divide
                steps += 2
                carry = 1
            else:
                # Even → divide only
                steps += 1

        return steps + carry


# User input: binary string
s = sys.stdin.readline().strip()

result = BinaryReductionSteps.num_steps(s)
print(result)