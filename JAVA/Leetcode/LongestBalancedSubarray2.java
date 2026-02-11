/*
3721. Longest Balanced Subarray II

A subarray is BALANCED if:
number of DISTINCT even values == number of DISTINCT odd values

We convert this into a running balance:
odd value contributes +1
even value contributes -1

For a fixed left index l:
if balance suggests 0 at some right index r,
then subarray [l..r] is balanced.

Challenge:
A value should contribute ONLY ONCE inside a window,
specifically at its FIRST occurrence in that window.

We use:
1) For every value → queue of its positions
2) Segment tree over right endpoints r
3) Range updates to simulate contribution of "first occurrences"
4) Query for rightmost r where balance == 0

Time: O(n log n)
Space: O(n)
*/

import java.util.*;

public class LongestBalancedSubarray2 {

    static class Solution {

        int n;

        // Segment tree arrays:
        // min[] → minimum balance in segment
        // max[] → maximum balance in segment
        // lazy[] → pending range addition
        int[] min, max, lazy;

        // Build empty segment tree
        // Initially all balances are 0
        void build(int i, int l, int r) {
            if (l == r) return;
            int m = (l + r) >>> 1;
            build(i << 1, l, m);
            build(i << 1 | 1, m + 1, r);
        }

        // Apply value to a node (range add)
        void apply(int i, int v) {
            min[i] += v;
            max[i] += v;
            lazy[i] += v;
        }

        // Push lazy value to children
        // This ensures children reflect correct values before further operations
        void push(int i) {
            if (lazy[i] != 0) {
                int v = lazy[i];
                apply(i << 1, v);
                apply(i << 1 | 1, v);
                lazy[i] = 0;
            }
        }

        // Recalculate node from children
        void pull(int i) {
            min[i] = Math.min(min[i << 1], min[i << 1 | 1]);
            max[i] = Math.max(max[i << 1], max[i << 1 | 1]);
        }

        /*
        Add value v to range [ql, qr]
        This simulates adding/removing contribution of a number
        to all subarrays ending at r >= firstOccurrence
        */
        void add(int i, int l, int r, int ql, int qr, int v) {
            if (ql > r || qr < l) return;

            if (ql <= l && r <= qr) {
                apply(i, v);
                return;
            }

            push(i);

            int m = (l + r) >>> 1;
            add(i << 1, l, m, ql, qr, v);
            add(i << 1 | 1, m + 1, r, ql, qr, v);

            pull(i);
        }

        /*
        Find the RIGHTMOST index r >= ql
        where segment tree value == 0.

        Why check min <= 0 <= max ?
        Because if 0 is not inside range, no zero exists here.
        */
        int rightmostZero(int i, int l, int r, int ql) {
            if (r < ql || min[i] > 0 || max[i] < 0) return -1;

            if (l == r) return l;

            push(i);
            int m = (l + r) >>> 1;

            int res = rightmostZero(i << 1 | 1, m + 1, r, ql);
            if (res != -1) return res;

            return rightmostZero(i << 1, l, m, ql);
        }

        public int longestBalanced(int[] nums) {
            n = nums.length;

            min = new int[4 * n];
            max = new int[4 * n];
            lazy = new int[4 * n];

            build(1, 0, n - 1);

            /*
            pos[value] = queue of indices where value appears
            This lets us know:
            - current first occurrence
            - next occurrence when sliding window moves
            */
            HashMap<Integer, ArrayDeque<Integer>> pos = new HashMap<>();

            for (int i = 0; i < n; i++) {
                pos.computeIfAbsent(nums[i], k -> new ArrayDeque<>()).add(i);
            }

            /*
            Initially left pointer l = 0.
            For each distinct value,
            its first occurrence contributes to all r >= position.
            */
            for (int v : pos.keySet()) {
                int firstIndex = pos.get(v).peekFirst();

                int sign = (v & 1) == 1 ? 1 : -1;

                add(1, 0, n - 1, firstIndex, n - 1, sign);
            }

            int ans = 0;

            /*
            Slide left pointer l from 0 to n-1
            At each step:
            1) find best r
            2) remove effect of nums[l]
            3) activate next occurrence of nums[l]
            */
            for (int l = 0; l < n; l++) {

                int r = rightmostZero(1, 0, n - 1, l);
                if (r != -1) ans = Math.max(ans, r - l + 1);

                int value = nums[l];
                int sign = (value & 1) == 1 ? 1 : -1;

                ArrayDeque<Integer> dq = pos.get(value);

                int removedIndex = dq.pollFirst();
                int nextIndex = dq.isEmpty() ? n : dq.peekFirst();

                /*
                Remove contribution from old first occurrence range
                because it is no longer inside window
                */
                if (removedIndex <= nextIndex - 1) {
                    add(1, 0, n - 1, removedIndex, nextIndex - 1, -sign);
                }
            }

            return ans;
        }
    }

    // Simple input format:
    // n
    // a1 a2 a3 ... an
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.longestBalanced(nums));
    }
}
