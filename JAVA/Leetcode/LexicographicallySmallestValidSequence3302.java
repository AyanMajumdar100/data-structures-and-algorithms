/*
 * Problem Statement:
 * Given two strings word1 and word2, find the lexicographically smallest sequence of indices in word1 
 * of length word2.length such that the corresponding characters in word1 form a string almost equal to word2 
 * (at most 1 character mismatch allowed). Return an empty array if no such sequence exists.
 */

/*
 * Approach: Right-to-Left Precomputation Suffix Match + Left-to-Right Greedy Matching (O(N + M) Time)
 * 1. Precomputation (Right-to-Left):
 *    - Build an array `R` where `R[i]` represents the largest (right-most) index in `word1` where we can 
 *      match the suffix `word2[i...m-1]` completely without any mismatches.
 * 2. Greedy Matching (Left-to-Right):
 *    - Iterate from `i = 0` to `m - 1` to find the smallest possible valid index in `word1` for each character of `word2`.
 *    - Priority 1: Exact match (`word1[j] == word2[i]`). Always take the first matching index `j` to keep indices as small as possible.
 *    - Priority 2: Mismatch substitution (`word1[j] != word2[i]`). If `mismatchUsed` is false, we can use `j` as a wildcard 
 *      provided that the remaining suffix `word2[i+1...m-1]` can be perfectly matched in `word1` strictly after index `j` 
 *      (i.e., `j < R[i + 1]` or `i == m - 1`).
 */

import java.util.Arrays;
import java.util.Scanner;

public class LexicographicallySmallestValidSequence3302 {
    public int[] validSequence(String word1, String word2) {
        int length1 = word1.length();
        int length2 = word2.length();
        
        // R[i] stores the right-most starting index in word1 that perfectly matches suffix word2[i...length2-1]
        int[] R = new int[length2];
        int currPointer = length1 - 1;
        
        // Step 1: Precompute the right-most exact suffix matches
        for (int i = length2 - 1; i >= 0; i--) {
            while (currPointer >= 0 && word1.charAt(currPointer) != word2.charAt(i)) {
                currPointer--;
            }
            if (currPointer >= 0) {
                R[i] = currPointer;
                currPointer--;
            } else {
                R[i] = -1; // Suffix cannot be perfectly matched
            }
        }
        
        int[] sequenceResult = new int[length2];
        boolean mismatchUsed = false;
        int word1Pointer = 0;
        
        // Step 2: Build the lexicographically smallest valid sequence from left to right
        for (int i = 0; i < length2; i++) {
            boolean matchedThisChar = false;
            
            while (word1Pointer < length1) {
                if (word1.charAt(word1Pointer) == word2.charAt(i)) {
                    // Priority 1: Exact match found
                    sequenceResult[i] = word1Pointer;
                    word1Pointer++;
                    matchedThisChar = true;
                    break;
                } else if (!mismatchUsed && (i == length2 - 1 || word1Pointer < R[i + 1])) {
                    // Priority 2: Valid mismatch substitution
                    sequenceResult[i] = word1Pointer;
                    mismatchUsed = true;
                    word1Pointer++;
                    matchedThisChar = true;
                    break;
                } else {
                    // Skip index as it cannot safely form a valid prefix or suffix match
                    word1Pointer++;
                }
            }
            
            if (!matchedThisChar) {
                return new int[0];
            }
        }
        
        return sequenceResult;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string word1:");
        String word1Param = scanner.nextLine().trim();

        System.out.println("Enter string word2:");
        String word2Param = scanner.nextLine().trim();

        LexicographicallySmallestValidSequence3302 solverInstance = new LexicographicallySmallestValidSequence3302();
        int[] resultArray = solverInstance.validSequence(word1Param, word2Param);

        System.out.println("Lexicographically smallest sequence: " + Arrays.toString(resultArray));
        scanner.close();
    }
}
