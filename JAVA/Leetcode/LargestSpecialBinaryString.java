/*
761. Special Binary String

A special binary string is defined by TWO rules:
1) Number of 1's == number of 0's
2) For every prefix → count(1) >= count(0)

You are given a special binary string s.

Operation:
You may swap TWO consecutive non-empty special substrings.

Goal:
Return the lexicographically largest string possible after any number of swaps.

Example 1:
Input:  s = "11011000"
Output: "11100100"

Example 2:
Input:  s = "10"
Output: "10"

Constraints:
1 <= s.length <= 50
s contains only '0' and '1'
s is guaranteed to be special
*/

import java.util.*;

class LargestSpecialBinaryString {

    public String makeLargestSpecial(String s) {

        // I use a counter like balance of parentheses
        int count = 0;

        // Start index of current special substring
        int start = 0;

        List<String> parts = new ArrayList<>();

        // I scan the string to split it into primitive special substrings
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '1') count++;
            else count--;

            // When count becomes 0 → I found a valid special substring
            if (count == 0) {

                // I recursively optimize the inside part
                String inner = makeLargestSpecial(s.substring(start + 1, i));

                // Wrap with outer 1 and 0
                parts.add("1" + inner + "0");

                // Move to next segment
                start = i + 1;
            }
        }

        // I sort parts in descending lexicographic order
        Collections.sort(parts, Collections.reverseOrder());

        // I join them to form the largest string
        return String.join("", parts);
    }

    // ---------- USER INPUT DRIVER ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter special binary string: ");
        String s = sc.nextLine();

        LargestSpecialBinaryString solver = new LargestSpecialBinaryString();
        String result = solver.makeLargestSpecial(s);

        System.out.println("Lexicographically largest string: " + result);

        sc.close();
    }
}