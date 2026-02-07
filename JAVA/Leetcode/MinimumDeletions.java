/*
You are given a string s consisting only of characters 'a' and 'b'​​​​.
You can delete any number of characters in s to make s balanced. s is balanced if there is no pair of indices (i,j) such that i < j and s[i] = 'b' and s[j]= 'a'.
Return the minimum number of deletions needed to make s balanced.

Example 1:
Input: s = "aababbab"
Output: 2
Explanation: You can either:
Delete the characters at 0-indexed positions 2 and 6 ("aababbab" -> "aaabbb"), or
Delete the characters at 0-indexed positions 3 and 6 ("aababbab" -> "aabbbb").
Example 2:
Input: s = "bbaaaaabb"
Output: 2
Explanation: The only solution is to delete the first two characters.
*/

import java.util.Scanner;

class Solution {

    public int minimumDeletions(String s) {
        int bCount = 0;       // Counts the number of 'b's seen so far
        int minDeletions = 0; // Tracks the minimum deletions needed

        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            if (c == 'b') {
                // If we see a 'b', increment the count
                bCount++;
            } else {
                // If we see an 'a' after some 'b's,
                // we might need to delete it to maintain order
                if (minDeletions < bCount) {
                    minDeletions++; // Delete this 'a'
                }
                // Otherwise, previous deletions already handled this
            }
        }
        // Return the total minimum deletions needed
        return minDeletions;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string of 'a' and 'b': ");
        String input = sc.nextLine();

        Solution sol = new Solution();
        int result = sol.minimumDeletions(input);

        System.out.println("Minimum deletions needed: " + result);
        sc.close();
    }
}
