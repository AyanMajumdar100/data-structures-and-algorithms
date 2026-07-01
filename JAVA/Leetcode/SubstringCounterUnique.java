/*
Problem: 1358. Number of Substrings Containing All Three Characters

Problem Statement:
Given a string s consisting only of characters 'a', 'b', and 'c',
return the number of substrings that contain at least one occurrence
of all three characters.

Example:
Input:  "abcabc"
Output: 10

Approach (Sliding Window / Two Pointers):
- Use two pointers (left and right) to create a window.
- Expand the window by moving 'right'.
- Keep track of counts of 'a', 'b', and 'c'.
- When the window contains all three characters:
    → All substrings from this window to the end are valid.
    → Add (n - right) to result.
- Then shrink the window from the left to find more valid substrings.

Time Complexity: O(n)
Space Complexity: O(1)

Commit Message:
feat: sliding window solution for counting substrings with a, b, c (java & python)
*/

import java.util.*;

class SubstringCounterUniqueJava {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Taking user input
        System.out.print("Enter the string: ");
        String s = sc.nextLine();

        // Calling function and printing result
        int result = countSubstrings(s);
        System.out.println("Number of valid substrings: " + result);
    }

    public static int countSubstrings(String s) {
        int n = s.length();

        // Array to store count of 'a', 'b', 'c'
        int[] count = new int[3];

        int left = 0;   // left pointer of window
        int result = 0; // final answer

        // Move right pointer step by step
        for (int right = 0; right < n; right++) {

            // Increase count of current character
            count[s.charAt(right) - 'a']++;

            // Check if window contains all 3 characters
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {

                // All substrings from current window to end are valid
                result += (n - right);

                // Shrink window from left
                count[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return result;
    }
}