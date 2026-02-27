/*
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
*/

import java.util.*;

class BinaryStringEqualization {

    /*
    Beginner-friendly explanation of the idea:

    Instead of flipping characters directly, we track:
    "How many zeros currently exist?"

    Each operation flips exactly k indices.
    If we flip i zeros and (k-i) ones:
        newZeroCount = zeros + k - 2*i

    So from one zero-count we can reach a range of new zero-counts.

    We treat zero-count as a state and perform BFS to reach state 0.
    BFS ensures minimum number of operations.

    next_valid[] helps us skip already visited states efficiently.
    */
    public static int minOperations(String s, int k) {
        int n = s.length();

        // Count zeros in string
        int z = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') z++;
        }

        if (z == 0) return 0;

        // Helps us quickly jump to next unvisited state of same parity
        int[] next_valid = new int[n + 3];
        for (int i = 0; i <= n + 2; i++) {
            next_valid[i] = i;
        }

        // BFS queue and distance tracking
        int[] q = new int[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        int head = 0, tail = 0;

        q[tail++] = z;
        dist[z] = 0;
        next_valid[z] = z + 2;

        while (head < tail) {
            int u = q[head++];

            int max_i = Math.min(k, u);
            int min_i = Math.max(0, k - (n - u));

            int L = u + k - 2 * max_i;
            int R = u + k - 2 * min_i;

            int curr = L;

            while (curr <= R) {
                int p = curr;

                // Path compression style skipping
                while (next_valid[p] != p) {
                    p = next_valid[p];
                }

                int temp = curr;
                while (temp != p) {
                    int nxt = next_valid[temp];
                    next_valid[temp] = p;
                    temp = nxt;
                }

                curr = p;

                if (curr > R) break;

                dist[curr] = dist[u] + 1;

                if (curr == 0) return dist[curr];

                q[tail++] = curr;
                next_valid[curr] = curr + 2;

                curr += 2;
            }
        }

        return dist[0];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input binary string
        String s = scanner.nextLine().trim();

        // Input k
        int k = scanner.nextInt();

        int result = minOperations(s, k);
        System.out.println(result);
        scanner.close();
    }
}