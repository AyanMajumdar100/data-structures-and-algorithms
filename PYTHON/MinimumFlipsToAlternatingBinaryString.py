"""
1888. Minimum Number of Flips to Make the Binary String Alternating

Problem Summary:
You are given a binary string s.

Two operations are allowed:
1) Rotation:
   Move the first character to the end.

2) Flip:
   Change '0' to '1' or '1' to '0'.

Goal:
Find the minimum number of flip operations needed
to make the string alternating.

Alternating patterns possible:
Pattern A: 010101...
Pattern B: 101010...

Approach:

Instead of physically rotating the string many times,
we simulate it using a sliding window over s + s.

All rotations of s appear as substrings of length n
inside the string s + s.

For each window we calculate how many flips are needed
to match the two alternating patterns.
"""

def minFlips(s):

    n = len(s)

    diff1 = 0   # mismatches with pattern 010101...
    diff2 = 0   # mismatches with pattern 101010...

    ans = float('inf')

    for i in range(2 * n):

        c = s[i % n]

        target1 = '0' if i % 2 == 0 else '1'
        target2 = '1' if i % 2 == 0 else '0'

        if c != target1:
            diff1 += 1
        if c != target2:
            diff2 += 1

        # Remove left element when window exceeds size n
        if i >= n:

            outC = s[(i - n) % n]

            outTarget1 = '0' if (i - n) % 2 == 0 else '1'
            outTarget2 = '1' if (i - n) % 2 == 0 else '0'

            if outC != outTarget1:
                diff1 -= 1
            if outC != outTarget2:
                diff2 -= 1

        # Evaluate answer once window size reaches n
        if i >= n - 1:
            ans = min(ans, diff1, diff2)

    return ans


# User Input
s = input("Enter binary string: ")

result = minFlips(s)

print("Minimum flips required:", result)