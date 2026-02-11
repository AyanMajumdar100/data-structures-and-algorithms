"""
3721. Longest Balanced Subarray II

A subarray is BALANCED if:
number of DISTINCT even values == number of DISTINCT odd values

We convert this into a running balance:
odd value contributes +1
even value contributes -1

For a fixed left index l:
if balance becomes 0 at some right index r,
then subarray [l..r] is balanced.

Important rule:
Each value contributes ONLY ONCE inside a window,
specifically at its FIRST occurrence within that window.

We maintain:
1) For every value → queue of all its positions
2) Segment tree over right endpoints r
3) Range updates to simulate contribution of first occurrences
4) Query for rightmost r where balance == 0

Time Complexity: O(n log n)
Space Complexity: O(n)
"""

from collections import defaultdict, deque
import sys


class Solution:
    def longestBalanced(self, nums):
        self.n = len(nums)

        # Segment tree arrays
        # minv[i] = minimum value in segment
        # maxv[i] = maximum value in segment
        # lazy[i] = pending addition to propagate
        size = 4 * self.n
        self.minv = [0] * size
        self.maxv = [0] * size
        self.lazy = [0] * size

        # Build empty tree (all zeros)
        self._build(1, 0, self.n - 1)

        # Map value -> queue of all indices where it appears
        pos = defaultdict(deque)
        for i, v in enumerate(nums):
            pos[v].append(i)

        # Activate first occurrence of each value
        # If value is odd -> +1
        # If value is even -> -1
        for v, dq in pos.items():
            first_index = dq[0]
            sign = 1 if (v & 1) else -1

            # This value contributes to all subarrays ending at r >= first_index
            self._add(1, 0, self.n - 1, first_index, self.n - 1, sign)

        ans = 0

        # Slide left boundary l from 0 → n-1
        for l in range(self.n):

            # Find farthest r >= l where balance == 0
            r = self._rightmost_zero(1, 0, self.n - 1, l)
            if r != -1:
                ans = max(ans, r - l + 1)

            v = nums[l]
            sign = 1 if (v & 1) else -1

            dq = pos[v]

            # Remove old first occurrence
            removed_index = dq.popleft()

            # Next occurrence becomes new first occurrence
            next_index = dq[0] if dq else self.n

            # Remove old contribution range
            if removed_index <= next_index - 1:
                self._add(1, 0, self.n - 1, removed_index, next_index - 1, -sign)

        return ans

    # Build segment tree
    def _build(self, i, l, r):
        if l == r:
            return
        m = (l + r) // 2
        self._build(i * 2, l, m)
        self._build(i * 2 + 1, m + 1, r)

    # Apply range addition to node
    def _apply(self, i, v):
        self.minv[i] += v
        self.maxv[i] += v
        self.lazy[i] += v

    # Push lazy value to children
    def _push(self, i):
        if self.lazy[i] != 0:
            v = self.lazy[i]
            self._apply(i * 2, v)
            self._apply(i * 2 + 1, v)
            self.lazy[i] = 0

    # Pull values from children
    def _pull(self, i):
        self.minv[i] = min(self.minv[i * 2], self.minv[i * 2 + 1])
        self.maxv[i] = max(self.maxv[i * 2], self.maxv[i * 2 + 1])

    # Range add on [ql, qr]
    def _add(self, i, l, r, ql, qr, v):
        if ql > r or qr < l:
            return

        if ql <= l and r <= qr:
            self._apply(i, v)
            return

        self._push(i)
        m = (l + r) // 2
        self._add(i * 2, l, m, ql, qr, v)
        self._add(i * 2 + 1, m + 1, r, ql, qr, v)
        self._pull(i)

    # Find rightmost index r >= ql where balance == 0
    def _rightmost_zero(self, i, l, r, ql):
        # If segment cannot contain zero → skip
        if r < ql or self.minv[i] > 0 or self.maxv[i] < 0:
            return -1

        if l == r:
            return l

        self._push(i)
        m = (l + r) // 2

        # Try right child first to get farthest index
        res = self._rightmost_zero(i * 2 + 1, m + 1, r, ql)
        if res != -1:
            return res

        return self._rightmost_zero(i * 2, l, m, ql)


# ----------- USER INPUT DRIVER -----------

# Input format:
# n
# a1 a2 a3 ... an

data = list(map(int, sys.stdin.read().strip().split()))
n = data[0]
nums = data[1:]

sol = Solution()
print(sol.longestBalanced(nums))
