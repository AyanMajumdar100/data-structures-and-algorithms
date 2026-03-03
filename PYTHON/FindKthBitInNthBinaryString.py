"""
1545. Find Kth Bit in Nth Binary String

Given n and k, construct Sn using:

S1 = "0"
Si = S(i-1) + "1" + reverse(invert(S(i-1)))

Return kth bit in Sn (1-based indexing).
"""

def findKthBit(n, k):
    invert_count = 0

    # Length of Sn
    length = (1 << n) - 1

    while k > 1:

        mid = length // 2 + 1

        # If k is middle element
        if k == mid:
            return '1' if invert_count % 2 == 0 else '0'

        # If k is in right half
        if k > mid:
            # Mirror index
            k = length - k + 1

            # Increase inversion count
            invert_count += 1

        # Move to previous level
        length //= 2

    # Base case (S1 = "0")
    return '0' if invert_count % 2 == 0 else '1'


# User Input
n = int(input("Enter n: "))
k = int(input("Enter k: "))

result = findKthBit(n, k)

print("Kth Bit:", result)