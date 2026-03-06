"""
1784. Check if Binary String Has at Most One Segment of Ones

Problem Statement:
You are given a binary string s containing only '0' and '1'.
The string has no leading zeros, meaning it always starts with '1'.

Your task is to check whether the string contains at most
one contiguous segment of '1's.

A segment means a continuous block of '1's.

Example 1
Input: "1001"
Output: False
Explanation:
There are two separate segments of '1':
"1" at the start and another "1" at the end.

Example 2
Input: "110"
Output: True
Explanation:
All '1's appear in a single block.

Key Observation:
Because the string always starts with '1', the first segment is guaranteed.

If another segment of '1' appears later, we must have seen:
0 -> 1 transition

That pattern is represented by substring "01".

So if "01" exists in the string, there are multiple segments.
Otherwise, there is only one segment.

Constraints:
1 <= len(s) <= 100
s contains only '0' and '1'
s[0] is always '1'
"""

def checkOnesSegment(s):

    # If "01" exists, it means after some zero,
    # a new '1' started again → second segment.
    return "01" not in s


# User input
s = input("Enter binary string: ")

result = checkOnesSegment(s)

print("At most one segment of ones:", result)