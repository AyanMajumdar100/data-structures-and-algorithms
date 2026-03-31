/*
Problem: 3474. Lexicographically Smallest Generated String

--------------------------------------------------

🧠 PROBLEM UNDERSTANDING:

We need to build a string `word` such that:

Length = n + m - 1

For every index i:
If str1[i] == 'T':
    word[i ... i+m-1] MUST equal str2

If str1[i] == 'F':
    word[i ... i+m-1] MUST NOT equal str2

--------------------------------------------------

💡 CORE STRATEGY (3 PHASES):

---------------------------------
PHASE 1: INITIALIZE
---------------------------------
We create answer array filled with '?'
→ means "not decided yet"

---------------------------------
PHASE 2: HANDLE 'T' (FORCED MATCH)
---------------------------------
If str1[i] == 'T':
→ we MUST place str2 at position i

While placing:
✔ If already filled, it must match
❌ Else conflict → return ""

---------------------------------
PHASE 3: HANDLE 'F' (AVOID MATCH)
---------------------------------

For 'F', we must ensure substring != str2

Case 1:
If substring already FULLY matches str2
→ impossible → return ""

Case 2:
If there's at least one '?':
→ we will BREAK match later

We track:
👉 endsAt[k] = list of i such that
position k can break substring starting at i

---------------------------------
PHASE 4: FILL '?' GREEDILY
---------------------------------

For each '?' position k:

We try smallest character ('a' → 'z')

BUT:
We must avoid making any forbidden substring match str2

So:
For each substring i that depends on k:
→ check if rest matches str2
→ if yes, forbid that character

Then:
Pick smallest allowed character

If none → return ""

----------------------------------

🎯 WHY GREEDY WORKS?

We always pick smallest valid char
→ ensures lexicographically smallest string

----------------------------------

⏱ Complexity:

Time: O(n * m)
Space: O(n + m)

--------------------------------------------------
*/

import java.util.ArrayList;
import java.util.List;

class LexiGen {
    public static void main(String[] args) {

        java.util.Scanner sc = new java.util.Scanner(System.in);

        String str1 = sc.next();
        String str2 = sc.next();

        System.out.println(generateString(str1, str2));

        sc.close();
    }

    public static String generateString(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();
        int len = n + m - 1;

        char[] ans = new char[len];

        // ----------- PHASE 1: Initialize with '?' -----------
        for (int i = 0; i < len; i++) {
            ans[i] = '?';
        }

        // ----------- PHASE 2: Force 'T' placements -----------
        for (int i = 0; i < n; i++) {

            if (str1.charAt(i) == 'T') {

                for (int j = 0; j < m; j++) {

                    // Conflict check
                    if (ans[i + j] != '?' && ans[i + j] != str2.charAt(j)) {
                        return "";
                    }

                    ans[i + j] = str2.charAt(j);
                }
            }
        }

        // ----------- PHASE 3: Prepare 'F' constraints -----------

        @SuppressWarnings("unchecked")
        List<Integer>[] endsAt = new ArrayList[len];

        for (int i = 0; i < n; i++) {

            if (str1.charAt(i) == 'F') {

                int lastQ = -1;

                // Find rightmost '?' in this substring
                for (int j = m - 1; j >= 0; j--) {
                    if (ans[i + j] == '?') {
                        lastQ = i + j;
                        break;
                    }
                }

                // No '?' → check if already equal → invalid
                if (lastQ == -1) {

                    boolean match = true;

                    for (int j = 0; j < m; j++) {
                        if (ans[i + j] != str2.charAt(j)) {
                            match = false;
                            break;
                        }
                    }

                    if (match) return "";

                } else {

                    // Store dependency: this position must break match
                    if (endsAt[lastQ] == null) {
                        endsAt[lastQ] = new ArrayList<>();
                    }

                    endsAt[lastQ].add(i);
                }
            }
        }

        // ----------- PHASE 4: Fill remaining '?' greedily -----------

        for (int k = 0; k < len; k++) {

            if (ans[k] != '?') continue;

            boolean[] forbidden = new boolean[26];

            // Check all substrings that depend on this position
            if (endsAt[k] != null) {

                for (int i : endsAt[k]) {

                    boolean match = true;

                    for (int j = 0; j < m; j++) {

                        if (i + j == k) continue;

                        if (ans[i + j] != str2.charAt(j)) {
                            match = false;
                            break;
                        }
                    }

                    // If rest matches, this char must NOT match
                    if (match) {
                        forbidden[str2.charAt(k - i) - 'a'] = true;
                    }
                }
            }

            // Pick smallest valid character
            char picked = '?';

            for (char c = 'a'; c <= 'z'; c++) {
                if (!forbidden[c - 'a']) {
                    picked = c;
                    break;
                }
            }

            if (picked == '?') return "";

            ans[k] = picked;
        }

        return new String(ans);
    }
}