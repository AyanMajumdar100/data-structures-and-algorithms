/*
Problem: 2573. Find the String with LCP

--------------------------------------------------

🧠 WHAT IS LCP?

lcp[i][j] =
Length of longest common prefix between:
word[i...n-1] and word[j...n-1]

Example:
word = "abab"
lcp[0][2] = 2 → "ab" matches

--------------------------------------------------

🎯 GOAL:

Construct the lexicographically smallest string
such that its LCP matrix matches given matrix.

If impossible → return ""

--------------------------------------------------

💡 CORE IDEA (2 PHASES):

---------------------------
PHASE 1 → BUILD STRING
---------------------------

We group indices that MUST have same character.

Rule:
If lcp[i][j] > 0 → means
word[i] == word[j]

So:
👉 Assign same character to all such indices

Process:
- Start from left
- Assign smallest possible character ('a', 'b', ...)
- Fill all j where lcp[i][j] > 0 with same char

This ensures:
✔ grouping constraint satisfied
✔ lexicographically smallest

---------------------------
PHASE 2 → VALIDATION
---------------------------

Now we VERIFY if constructed string is valid.

We recompute LCP matrix from string:

If s[i] == s[j]:
    actualLcp[i][j] = 1 + actualLcp[i+1][j+1]
Else:
    0

Then compare:
actualLcp[i][j] == given lcp[i][j]

If ANY mismatch → invalid → return ""

--------------------------------------------------

⚠️ EDGE CASE:

If characters exceed 'z' → impossible

--------------------------------------------------

⏱ Complexity:

Time: O(n^2)
Space: O(n^2)

--------------------------------------------------
*/

import java.util.*;

class LCPString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] lcp = new int[n][n];

        // Input LCP matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                lcp[i][j] = sc.nextInt();
            }
        }

        System.out.println(findTheString(lcp));

        sc.close();
    }

    public static String findTheString(int[][] lcp) {

        int n = lcp.length;

        char[] s = new char[n]; // result string
        char c = 'a';           // current character

        // ---------------- PHASE 1: BUILD STRING ----------------
        for (int i = 0; i < n; i++) {

            // If not assigned yet
            if (s[i] == 0) {

                // If we exceed 'z', impossible
                if (c > 'z') return "";

                s[i] = c;

                // Assign same character to all j where LCP > 0
                for (int j = i + 1; j < n; j++) {
                    if (lcp[i][j] > 0) {
                        s[j] = c;
                    }
                }

                c++; // move to next character
            }
        }

        // ---------------- PHASE 2: VALIDATION ----------------

        // Build actual LCP from constructed string
        int[][] actualLcp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                // If characters match → extend LCP
                if (s[i] == s[j]) {
                    actualLcp[i][j] = actualLcp[i + 1][j + 1] + 1;
                }

                // If mismatch with given LCP → invalid
                if (actualLcp[i][j] != lcp[i][j]) {
                    return "";
                }
            }
        }

        return new String(s);
    }
}