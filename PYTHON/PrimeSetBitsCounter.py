"""
762. Prime Number of Set Bits in Binary Representation

Count numbers in [left, right] whose binary representation
contains a PRIME number of set bits.

Set bits = number of 1's in binary form.

Example:
Input: left = 6, right = 10
Output: 4

Constraints:
1 <= left <= right <= 10^6
0 <= right - left <= 10^4
"""

class PrimeSetBitsCounter:

    def countPrimeSetBits(self, left: int, right: int) -> int:

        # Same prime bitmask trick as Java
        mask = 665772

        count = 0

        # I iterate through the range
        for i in range(left, right + 1):

            # Count number of set bits using built-in
            set_bits = bin(i).count('1')

            # Check if set_bits is prime using mask
            if (1 << set_bits) & mask:
                count += 1

        return count


# ---------- USER INPUT DRIVER ----------
if __name__ == "__main__":
    left = int(input("Enter left: "))
    right = int(input("Enter right: "))

    solver = PrimeSetBitsCounter()
    result = solver.countPrimeSetBits(left, right)

    print("Count of numbers with prime set bits:", result)