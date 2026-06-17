/*
 * Problem Statement:
 * You are given a string s consisting of lowercase English letters and the special characters: '*', '#', and '%'.
 * You are also given an integer k.
 * Build a new string result by processing s according to the following rules from left to right:
 * - If the letter is a lowercase English letter, append it to result.
 * - A '*' removes the last character from result, if it exists.
 * - A '#' duplicates the current result and appends it to itself.
 * - A '%' reverses the current result.
 * Return the kth character of the final string result. If k is out of bounds, return '.'.
 * * Constraints:
 * 1 <= s.length <= 10^5
 * 0 <= k <= 10^15
 * Length of result will not exceed 10^15.
 */

/*
 * Approach: Backward Tracking / Index Demapping
 * Since the final string length can reach up to 10^15, simulating or storing the entire string 
 * is impossible due to memory and time constraints.
 * * 1. Forward Pass: Track the exact length of the simulated string at every instruction step `i` 
 * and store it in an array `L`.
 * 2. Bounds Check: If `k` is out of the bounds of the total final length (`L[n - 1]`), return '.'.
 * 3. Backward Pass: Work backwards from the last operation (`n-1` down to `0`) to figure out 
 * what index the current position mapped to in the previous state:
 * - Lowercase letter: If `k` matches the newly added index (`prevLen`), we found our character.
 * - '#': If `k` falls into the second duplicated half (`k >= prevLen`), subtract `prevLen` 
 * to remap it back into the first half.
 * - '%': Since the string is reversed, the target index `k` corresponds to index `prevLen - 1 - k` 
 * in the unreversed string block.
 */
import java.util.Scanner;

public class ProcessStrII3614 {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] L = new long[n];
        
        // Pass 1: Compute length transformations step-by-step
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            long prevLen = (i == 0) ? 0 : L[i - 1];
            
            if (c >= 'a' && c <= 'z') {
                L[i] = prevLen + 1;
            } else if (c == '*') {
                L[i] = Math.max(0, prevLen - 1);
            } else if (c == '#') {
                L[i] = prevLen * 2;
            } else if (c == '%') {
                L[i] = prevLen;
            }
        }
        
        // Return '.' if index k is outside the bounds of the generated string
        if (k < 0 || k >= L[n - 1]) {
            return '.';
        }
        
        // Pass 2: Map the index backward to uncover the source character
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            long prevLen = (i == 0) ? 0 : L[i - 1];
            
            if (c >= 'a' && c <= 'z') {
                // If the target index matches the newly appended character's position
                if (k == prevLen) {
                    return c;
                }
            } else if (c == '#') {
                // If the target index is located in the duplicated segment
                if (k >= prevLen) {
                    k -= prevLen;
                }
            } else if (c == '%') {
                // Reflect index position across the inversion midpoint
                k = prevLen - 1 - k;
            }
        }
        
        return '.';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the instruction string s:");
        String s = scanner.nextLine().trim();
        
        System.out.println("Enter the target index k:");
        long k = scanner.nextLong();
        
        ProcessStrII3614 solver = new ProcessStrII3614();
        char result = solver.processStr(s, k);
        
        System.out.println("Character at index k: " + result);
        scanner.close();
    }
}
