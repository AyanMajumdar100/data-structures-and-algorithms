"""
1009. Complement of Base 10 Integer

Problem:
The complement of an integer is formed by flipping all bits in its binary representation.

0 -> 1
1 -> 0

Example:
5  -> 101
flip -> 010
result -> 2

Goal:
Return the decimal value after flipping the bits.

Constraints:
0 <= n < 10^9
"""

def bitwiseComplement(n):

    # Edge case: complement of 0 is 1
    if n == 0:
        return 1

    mask = 1

    # Build mask with all 1s matching length of n's binary form
    # Example: if n = 10 (1010), mask becomes 1111
    while mask < n:
        mask = (mask << 1) | 1

    # XOR flips the bits
    return n ^ mask


# User Input
n = int(input("Enter integer n: "))

result = bitwiseComplement(n)

print("Complement of the number:", result)