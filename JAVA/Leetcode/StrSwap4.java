/*
Problem: 2839. Check if Strings Can be Made Equal With Operations I

--------------------------------------------------

🧠 UNDERSTANDING THE OPERATION:

You can swap characters only at positions:
i and j such that (j - i = 2)

So possible swaps:
Index 0 ↔ 2
Index 1 ↔ 3

👉 That means:
- Even indices (0, 2) form one group
- Odd indices (1, 3) form another group

--------------------------------------------------

💡 CORE IDEA:

We CANNOT mix characters between groups.

So:
✔ Characters at (0,2) can only rearrange among themselves
✔ Characters at (1,3) can only rearrange among themselves

--------------------------------------------------

🎯 GOAL:

Check if:
- s1's even index characters can match s2's even index characters
- s1's odd index characters can match s2's odd index characters

--------------------------------------------------

🧪 HOW WE CHECK:

For even positions:
Either:
(0→0 AND 2→2)  OR  (0→2 AND 2→0)

For odd positions:
Either:
(1→1 AND 3→3)  OR  (1→3 AND 3→1)

--------------------------------------------------

If BOTH match → return true
Else → false

--------------------------------------------------

Time Complexity: O(1)
Space Complexity: O(1)
*/

import java.util.*;

class StrSwap4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s1 = sc.next();
        String s2 = sc.next();

        System.out.println(canBeEqual(s1, s2));

        sc.close();
    }

    public static boolean canBeEqual(String s1, String s2) {

        // Check even indices (0 and 2)
        boolean evenMatch =
                (s1.charAt(0) == s2.charAt(0) && s1.charAt(2) == s2.charAt(2)) ||
                (s1.charAt(0) == s2.charAt(2) && s1.charAt(2) == s2.charAt(0));

        // Check odd indices (1 and 3)
        boolean oddMatch =
                (s1.charAt(1) == s2.charAt(1) && s1.charAt(3) == s2.charAt(3)) ||
                (s1.charAt(1) == s2.charAt(3) && s1.charAt(3) == s2.charAt(1));

        return evenMatch && oddMatch;
    }
}
