/*
 * Problem Statement:
 * You are given a palindromic string s.
 * Return the lexicographically smallest palindromic permutation of s.
 */

/*
 * Approach: Half-String Sorting / Frequency Bucket Reconstruction (O(N) Time, O(1) Space)
 * 1. Since the string is guaranteed to be a palindrome, its characters are symmetrically mirrored.
 * 2. To get the lexicographically smallest palindromic permutation, we simply need to collect 
 *    the left half of the string, sort its characters in ascending order, and mirror it.
 * 3. Algorithm:
 *    - Extract the first `n / 2` characters of string `s`.
 *    - Sort these characters in ascending order (or use a 26-character frequency bucket array for O(N) time).
 *    - If `n` is odd, place the middle character `s[n / 2]` at the center.
 *    - Construct the result as `sortedHalf + middleChar + reverse(sortedHalf)`.
 */

import java.util.Scanner;

public class SmallestPalindromicRearrangement3517 {
    public String smallestPalindrome(String s) {
        int stringLength = s.length();
        int halfLength = stringLength / 2;
        
        // Step 1: Count character frequencies in the first half (or whole string)
        int[] charFrequencies = new int[26];
        for (int i = 0; i < stringLength; i++) {
            charFrequencies[s.charAt(i) - 'a']++;
        }

        StringBuilder leftHalfBuilder = new StringBuilder();
        char middleCharacter = 0;

        // Step 2: Build the lexicographically smallest left half in sorted order ('a' to 'z')
        for (int i = 0; i < 26; i++) {
            if (charFrequencies[i] % 2 != 0) {
                middleCharacter = (char) ('a' + i);
            }
            for (int j = 0; j < charFrequencies[i] / 2; j++) {
                leftHalfBuilder.append((char) ('a' + i));
            }
        }

        // Step 3: Combine left half, middle character (if odd length), and reversed left half
        StringBuilder resultBuilder = new StringBuilder(leftHalfBuilder);
        if (middleCharacter != 0) {
            resultBuilder.append(middleCharacter);
        }
        resultBuilder.append(new StringBuilder(leftHalfBuilder).reverse());

        return resultBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner palindromeScanner = new Scanner(System.in);
        System.out.println("Enter palindromic string s:");
        String userStringParam = palindromeScanner.nextLine().trim();

        SmallestPalindromicRearrangement3517 solverInstance = new SmallestPalindromicRearrangement3517();
        String smallestPalindromeResult = solverInstance.smallestPalindrome(userStringParam);

        System.out.println("Lexicographically smallest palindromic permutation: " + smallestPalindromeResult);
        palindromeScanner.close();
    }
}
