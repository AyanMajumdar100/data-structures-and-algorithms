"""
1980. Find Unique Binary String

Problem:
Given n unique binary strings of length n,
return a binary string of length n that does NOT exist in the array.

Approach:
Use Cantor's Diagonalization trick.

For each string nums[i]:
flip the i-th bit.

This guarantees the new string differs from nums[i]
at position i, so it cannot equal any existing string.
"""

def findDifferentBinaryString(nums):

    result = []

    for i in range(len(nums)):

        # Flip the i-th bit of the i-th string
        if nums[i][i] == '0':
            result.append('1')
        else:
            result.append('0')

    return "".join(result)


# User Input
n = int(input("Enter number of binary strings: "))

nums = []
print("Enter the binary strings:")

for _ in range(n):
    nums.append(input())

result = findDifferentBinaryString(nums)

print("Unique binary string:", result)