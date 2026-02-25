"""
Problem: Sort Integers by The Number of 1 Bits

You are given an integer array arr.
Sort the integers in ascending order based on:
1) Number of 1s in their binary representation.
2) If two numbers have same number of 1s → sort by value.

Example:
Input: arr = [0,1,2,3,4,5,6,7,8]
Output: [0,1,2,4,8,3,5,6,7]

Input: arr = [1024,512,256,128,64,32,16,8,4,2,1]
Output: [1,2,4,8,16,32,64,128,256,512,1024]
"""

import sys


class BitCountSorter:
    """
    Core idea (logic preserved exactly):

    We temporarily pack two values into one integer:
    - Higher bits → number of 1s
    - Lower bits → original number

    Sorting integers naturally sorts:
    bit count first → then actual number.

    After sorting → we extract original numbers back.
    """

    @staticmethod
    def sort_by_bits(arr):

        # Attach bit count into higher bits
        for i in range(len(arr)):
            arr[i] |= (bin(arr[i]).count("1") << 16)

        # Normal numeric sorting handles priority automatically
        arr.sort()

        # Remove bit count information
        for i in range(len(arr)):
            arr[i] &= 0xFFFF

        return arr


# User input: space separated integers
line = sys.stdin.readline().strip()
arr = list(map(int, line.split()))

result = BitCountSorter.sort_by_bits(arr)

print(*result)