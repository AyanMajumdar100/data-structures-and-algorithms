"""
190. Reverse Bits

Reverse bits of a given 32-bit unsigned integer.

Examples:
Input:  n = 43261596
Output: 964176192

Input:  n = 2147483644
Output: 1073741822

Constraints:
0 <= n <= 2^31 - 2
n is even.

Follow-up:
If this function is called many times, how would you optimize it?
"""


class BitReverser:

    def reverseBits(self, n: int) -> int:
        # I will store the reversed number here
        result = 0

        # I process exactly 32 bits
        for _ in range(32):

            # Step 1: shift result left to make space
            result <<= 1

            # Step 2: take last bit of n and add it to result
            result |= (n & 1)

            # Step 3: shift n right to process next bit
            n >>= 1

        return result


# ---------------- USER INPUT DRIVER ----------------

if __name__ == "__main__":
    n = int(input("Enter an integer: "))

    solver = BitReverser()
    result = solver.reverseBits(n)

    print("Reversed bits result:", result)
