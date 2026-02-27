"""
Problem: Minimum Operations to Equalize Binary String

You are given a binary string s and an integer k.
In one operation, you must choose exactly k different indices and flip them.
Flipping means: '0' → '1' and '1' → '0'.

Return the minimum number of operations needed to make all characters '1'.
If impossible, return -1.

Example:
Input: s = "110", k = 1
Output: 1

Input: s = "0101", k = 3
Output: 2

Input: s = "101", k = 2
Output: -1
"""

import sys


class BinaryStringEqualization:
    """
    Beginner-friendly idea:

    Instead of modifying the string repeatedly,
    we track how many zeros exist.

    Each operation changes zero count based on how many zeros we flip.
    We treat zero-count as a state and use BFS to reach 0.

    next_valid helps skip already visited states efficiently.
    """

    @staticmethod
    def min_operations(s: str, k: int) -> int:
        n = len(s)

        # Count zeros
        z = s.count('0')
        if z == 0:
            return 0

        next_valid = list(range(n + 3))

        q = [0] * (n + 1)
        dist = [-1] * (n + 1)

        head = 0
        tail = 0

        q[tail] = z
        tail += 1
        dist[z] = 0
        next_valid[z] = z + 2

        while head < tail:
            u = q[head]
            head += 1

            max_i = min(k, u)
            min_i = max(0, k - (n - u))

            L = u + k - 2 * max_i
            R = u + k - 2 * min_i

            curr = L

            while curr <= R:
                p = curr

                while next_valid[p] != p:
                    p = next_valid[p]

                temp = curr
                while temp != p:
                    nxt = next_valid[temp]
                    next_valid[temp] = p
                    temp = nxt

                curr = p

                if curr > R:
                    break

                dist[curr] = dist[u] + 1

                if curr == 0:
                    return dist[curr]

                q[tail] = curr
                tail += 1

                next_valid[curr] = curr + 2
                curr += 2

        return dist[0]


# User input
s = sys.stdin.readline().strip()
k = int(sys.stdin.readline().strip())

result = BinaryStringEqualization.min_operations(s, k)
print(result)