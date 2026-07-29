/*
 * Problem Statement:
 * You are given a palindromic string s and an integer k.
 * Return the k-th lexicographically smallest palindromic permutation of s. 
 * If there are fewer than k distinct palindromic permutations, return an empty string.
 */

/*
 * Approach: Combinatorial Permutation Counting with Digit-by-Digit Placement
 * 1. Problem Reduction:
 *    Since palindromes are completely determined by their first half (length n / 2) and their middle character 
 *    (for odd lengths), finding the k-th palindromic permutation reduces to finding the k-th lexicographical 
 *    multiset permutation of the first-half characters.
 * 2. Combinatorics & Capping:
 *    - Given remaining character counts in the multiset, the number of distinct permutations of length L is 
 *      the multinomial coefficient: L! / (c1! * c2! * ... * c26!).
 *    - To avoid overflow and for speed, we compute this using combinations nCr and cap all counts at k + 1.
 * 3. Constructing the First Half:
 *    - Position by position (0 to n / 2 - 1), iterate through candidate characters 'a' to 'z'.
 *    - Temporarily pick character 'c' and calculate how many valid permutations can be formed with the remaining characters.
 *    - If `k <= ways`, fixing character 'c' at this position guarantees our answer is within this branch. Break and move to the next position.
 *    - Otherwise (`k > ways`), subtract `ways` from `k`, restore the count for 'c', and try the next character.
 * 4. Assembly:
 *    - Concatenate `firstHalf + middleChar (if odd) + reverse(firstHalf)` to return the final string.
 */

import java.util.Scanner;

public class SmallestPalindromicRearrangementII3518 {
    public String smallestPalindrome(String s, int k) {
        int stringLength = s.length();
        int halfLength = stringLength / 2;
        int[] fullCharCount = new int[26];
        
        for (int i = 0; i < stringLength; i++) {
            fullCharCount[s.charAt(i) - 'a']++;
        }

        char middleChar = 0;
        int[] halfCharCount = new int[26];
        for (int i = 0; i < 26; i++) {
            if (fullCharCount[i] % 2 != 0) {
                middleChar = (char) ('a' + i);
            }
            halfCharCount[i] = fullCharCount[i] / 2;
        }

        long capLimit = k + 1L;

        // Step 1: Verify total possible permutations; return empty if k exceeds limit
        long totalPermutations = computePermutationCount(halfCharCount, halfLength, capLimit);
        if (k > totalPermutations) {
            return "";
        }

        char[] halfResultArray = new char[halfLength];
        int remainingLength = halfLength;

        // Step 2: Construct the first half character by character in lexicographical order
        for (int pos = 0; pos < halfLength; pos++) {
            for (int charIdx = 0; charIdx < 26; charIdx++) {
                if (halfCharCount[charIdx] == 0) continue;

                halfCharCount[charIdx]--;
                remainingLength--;

                long validWaysCount = computePermutationCount(halfCharCount, remainingLength, capLimit);

                if (k <= validWaysCount) {
                    halfResultArray[pos] = (char) ('a' + charIdx);
                    break;
                } else {
                    k -= validWaysCount;
                    halfCharCount[charIdx]++;
                    remainingLength++;
                }
            }
        }

        // Step 3: Reconstruct full palindrome
        StringBuilder palindromeBuilder = new StringBuilder();
        for (char c : halfResultArray) {
            palindromeBuilder.append(c);
        }
        if (stringLength % 2 != 0) {
            palindromeBuilder.append(middleChar);
        }
        for (int i = halfLength - 1; i >= 0; i--) {
            palindromeBuilder.append(halfResultArray[i]);
        }

        return palindromeBuilder.toString();
    }

    // Computes total ways to arrange multiset with counts using multinomial product
    private long computePermutationCount(int[] counts, int totalLen, long capLimit) {
        long totalWays = 1;
        int remaining = totalLen;
        for (int count : counts) {
            if (count > 0) {
                totalWays = totalWays * computeCombinations(remaining, count, capLimit);
                if (totalWays >= capLimit) return capLimit;
                remaining -= count;
            }
        }
        return totalWays;
    }

    // Computes nCr capped at capLimit
    private long computeCombinations(int n, int r, long capLimit) {
        if (r < 0 || r > n) return 0;
        if (r == 0 || r == n) return 1;
        if (r > n - r) r = n - r;
        
        long result = 1;
        for (int i = 1; i <= r; i++) {
            result = result * (n - i + 1) / i;
            if (result >= capLimit) return capLimit;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter palindromic string s:");
        String userStringParam = scanner.nextLine().trim();

        System.out.println("Enter integer k:");
        int userKParam = scanner.nextInt();

        SmallestPalindromicRearrangementII3518 solverInstance = new SmallestPalindromicRearrangementII3518();
        String kThPalindromeResult = solverInstance.smallestPalindrome(userStringParam, userKParam);

        System.out.println("k-th lexicographically smallest palindromic permutation: \"" + kThPalindromeResult + "\"");
        scanner.close();
    }
}
