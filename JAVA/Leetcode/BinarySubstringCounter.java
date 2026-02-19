/*
696. Count Binary Substrings

Given a binary string s, return the number of non-empty substrings that:
1) contain the same number of 0's and 1's
2) all 0's and all 1's in the substring are grouped consecutively

Substrings that appear multiple times are counted multiple times.

Examples:
Input:  s = "00110011"
Output: 6

Input:  s = "10101"
Output: 4

Constraints:
1 <= s.length <= 10^5
s contains only '0' and '1'
*/

import java.util.*;

class BinarySubstringCounter {

    public int countBinarySubstrings(String s) {

        // I track the length of the previous group of same chars
        int prev = 0;

        // Current group length starts at 1 (first character)
        int curr = 1;

        int ans = 0;

        char[] arr = s.toCharArray();

        // I scan from the second character
        for (int i = 1; i < arr.length; i++) {

            // If same character → extend current group
            if (arr[i] == arr[i - 1]) {
                curr++;
            } else {
                // When group changes (0→1 or 1→0),
                // valid substrings depend on the smaller group
                ans += Math.min(prev, curr);

                // Current group becomes previous
                prev = curr;

                // Start new group
                curr = 1;
            }
        }

        // I add result for the final pair of groups
        return ans + Math.min(prev, curr);
    }

    // ---------- USER INPUT ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter binary string: ");
        String s = sc.nextLine();

        BinarySubstringCounter solver = new BinarySubstringCounter();
        int result = solver.countBinarySubstrings(s);

        System.out.println("Number of valid substrings: " + result);

        sc.close();
    }
}
