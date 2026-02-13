/*
3714. Longest Balanced Substring II

You are given a string s consisting only of the characters 'a', 'b', and 'c'.

A substring of s is called balanced if all distinct characters in the substring
appear the same number of times.

Return the length of the longest balanced substring of s.

Examples:
Input:  s = "abbac"
Output: 4
Explanation: "abba" → 'a' and 'b' both appear 2 times.

Input:  s = "aabcc"
Output: 3
Explanation: "abc" → 'a', 'b', 'c' appear 1 time each.

Input:  s = "aba"
Output: 2
Explanation: "ab" or "ba".

Constraints:
1 <= s.length <= 100000
s contains only 'a', 'b', 'c'.
*/

import java.util.*;

class LongestBalancedSubstringII {

    public int longestBalanced(String s) {
        int n = s.length();

        // Minimum answer is 1 if string is non-empty
        int ans = n > 0 ? 1 : 0;

        // ---------------------------------------------------
        // CASE 1: Only ONE distinct character
        // Example: "aaaa"
        // Longest balanced substring is simply longest run
        // ---------------------------------------------------
        int run = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == s.charAt(i - 1)) run++;
            else run = 1;

            if (run > ans) ans = run;
        }

        // ---------------------------------------------------
        // CASE 2: Exactly TWO distinct characters
        // For each pair among (a,b), (a,c), (b,c)
        // We track prefix difference:
        // count(x) - count(y)
        //
        // If the same difference appears again,
        // substring between them has equal counts.
        //
        // We use stamping technique instead of clearing array
        // to keep solution O(n).
        // ---------------------------------------------------
        for (int x = 0; x < 3; x++) {
            for (int y = x + 1; y < 3; y++) {

                int diff = 0;

                int size = 2 * n + 1;
                int[] first = new int[size];
                int[] seen = new int[size];

                int stamp = 1;

                seen[n] = stamp;
                first[n] = -1;

                for (int i = 0; i < n; i++) {
                    char c = s.charAt(i);

                    if (c == (char) ('a' + x)) diff++;
                    else if (c == (char) ('a' + y)) diff--;
                    else {
                        // Reset when third character appears
                        diff = 0;
                        stamp++;
                        seen[n] = stamp;
                        first[n] = i;
                        continue;
                    }

                    int idx = diff + n;

                    if (seen[idx] == stamp) {
                        int len = i - first[idx];
                        if (len > ans) ans = len;
                    } else {
                        seen[idx] = stamp;
                        first[idx] = i;
                    }
                }
            }
        }

        // ---------------------------------------------------
        // CASE 3: All THREE characters present
        // Balanced means:
        // count(a) = count(b) = count(c)
        //
        // We store prefix differences:
        // (b - a, c - a)
        //
        // If same pair repeats → substring balanced
        // ---------------------------------------------------
        int ca = 0, cb = 0, cc = 0;
        HashMap<Long, Integer> map = new HashMap<>();
        map.put(0L, -1);

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == 'a') ca++;
            else if (c == 'b') cb++;
            else cc++;

            long key = (((long) (cb - ca)) << 32) ^ (cc - ca);

            if (map.containsKey(key)) {
                int len = i - map.get(key);
                if (len > ans) ans = len;
            } else {
                map.put(key, i);
            }
        }

        return ans;
    }

    // ---------------- USER INPUT DRIVER ----------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string containing only a, b, c:");
        String s = sc.nextLine();

        LongestBalancedSubstringII sol = new LongestBalancedSubstringII();
        int result = sol.longestBalanced(s);

        System.out.println("Length of longest balanced substring: " + result);

        sc.close();
    }
}
