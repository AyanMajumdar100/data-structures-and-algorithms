"""
1758. Minimum Changes To Make Alternating Binary String

Problem Statement:
You are given a binary string s consisting only of '0' and '1'.

In one operation, you can flip any character:
'0' -> '1'
'1' -> '0'

A string is called alternating if no two adjacent characters are equal.

Examples:
Alternating: "0101", "1010"
Not Alternating: "0100", "1111"

Goal:
Find the minimum number of operations needed to convert the given
string into an alternating binary string.

Observation:
There are only two valid alternating patterns:

Pattern A:
Starts with '0'
Example: 01010101...

Pattern B:
Starts with '1'
Example: 10101010...

We compute how many flips are required for both patterns
and return the smaller value.

Constraints:
1 <= len(s) <= 10^4
s contains only '0' and '1'
"""

def minOperations(s):

    countMismatchPattern0 = 0
    n = len(s)

    # Assume pattern starts with '0'
    # Expected pattern becomes:
    # index: 0 1 2 3 4 5
    # value: 0 1 0 1 0 1
    for i in range(n):

        currentDigit = int(s[i])

        expectedDigit = i % 2

        # If current digit does not match expected pattern
        # we need one flip operation
        if currentDigit != expectedDigit:
            countMismatchPattern0 += 1

    # For pattern starting with '1',
    # mismatches will be the opposite positions
    countMismatchPattern1 = n - countMismatchPattern0

    return min(countMismatchPattern0, countMismatchPattern1)


# User Input
s = input("Enter binary string: ")

result = minOperations(s)

print("Minimum operations needed:", result)