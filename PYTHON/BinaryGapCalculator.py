"""
868. Binary Gap

Return the longest distance between two adjacent 1's
in the binary representation of a number.

Distance = number of bit positions between 1s.
If fewer than two 1s exist → return 0.
"""

class BinaryGapCalculator:

    def binaryGap(self, n: int) -> int:

        max_gap = 0

        # I skip trailing zeros to reach first 1
        while (n & 1) == 0:
            n >>= 1

        # I measure distance between consecutive 1s
        while n > 0:

            n >>= 1
            if n == 0:
                break

            distance = 1

            # Count zeros between two 1s
            while (n & 1) == 0:
                distance += 1
                n >>= 1

            max_gap = max(max_gap, distance)

        return max_gap


# ---------- USER INPUT DRIVER ----------
if __name__ == "__main__":
    n = int(input("Enter a number: "))

    solver = BinaryGapCalculator()
    result = solver.binaryGap(n)

    print("Longest Binary Gap:", result)