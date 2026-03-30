/*
Problem: 2840. Check if Strings Can be Made Equal With Operations II

--------------------------------------------------

🧠 UNDERSTANDING THE OPERATION:

We can swap indices i and j such that:
(j - i) is EVEN

👉 This means:
Both indices must have SAME PARITY

So:
- Even index ↔ Even index
- Odd index ↔ Odd index

--------------------------------------------------

💡 CORE INSIGHT:

We can rearrange:
✔ All EVEN positions among themselves
✔ All ODD positions among themselves

BUT:
❌ Cannot mix even ↔ odd

--------------------------------------------------

🎯 GOAL:

To make s1 == s2,
we must ensure:

1. Even index characters match (frequency-wise)
2. Odd index characters match (frequency-wise)

--------------------------------------------------

🧪 HOW WE CHECK:

We use frequency arrays:

evenCounts[char] → tracks difference between s1 and s2 at even indices
oddCounts[char]  → tracks difference at odd indices

Process:
- Increment for s1
- Decrement for s2

--------------------------------------------------

✅ If all counts become 0:
→ Perfect match → return true

❌ Else:
→ Impossible → return false

--------------------------------------------------

⏱ Complexity:

Time: O(n)
Space: O(1) (since only 26 letters)

--------------------------------------------------
*/

import java.util.*;

class ParitySwap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s1 = sc.next();
        String s2 = sc.next();

        System.out.println(checkStrings(s1, s2));

        sc.close();
    }

    public static boolean checkStrings(String s1, String s2) {

        int[] evenCounts = new int[26];
        int[] oddCounts = new int[26];

        for (int i = 0; i < s1.length(); i++) {

            // Even index group
            if (i % 2 == 0) {
                evenCounts[s1.charAt(i) - 'a']++;
                evenCounts[s2.charAt(i) - 'a']--;
            }
            // Odd index group
            else {
                oddCounts[s1.charAt(i) - 'a']++;
                oddCounts[s2.charAt(i) - 'a']--;
            }
        }

        // Validate both groups
        for (int i = 0; i < 26; i++) {
            if (evenCounts[i] != 0 || oddCounts[i] != 0) {
                return false;
            }
        }

        return true;
    }
}