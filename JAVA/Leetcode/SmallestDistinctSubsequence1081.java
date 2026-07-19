/*
 * Problem Statement:
 * Given a string s, return the lexicographically smallest subsequence of s 
 * that contains all the distinct characters of s exactly once.
 */

/*
 * Approach: Monotonic Greedy Stack Optimization (O(N) Time, O(1) Space)
 * 1. Find the final index position of each character in the string `s` using an integer array `lastOccurrence`.
 * 2. Maintain a dynamic stack via `StringBuilder` to collect the chosen character result sequence.
 * 3. Use a boolean array `inStack` to quickly prevent duplicate characters from entering our collection.
 * 4. Scan the string linearly:
 *    - If the current character `ch` is already in our stack, skip it.
 *    - While the top element of our stack is lexicographically LARGER than `ch`, AND that top character 
 *      appears again later in the string (`lastOccurrence[top] > i`), pop the top character off the stack 
 *      and mark it as out-of-stack. This guarantees the smallest dictionary order.
 *    - Append the current character to the stack and mark it as present.
 */

import java.util.Scanner;

public class SmallestDistinctSubsequence1081 {
    public String smallestSubsequence(String s) {
        // Step 1: Precompute the last index where each character appears in the string s
        int[] lastOccurrence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }
        
        boolean[] inStack = new boolean[26];
        StringBuilder resultBuilderStack = new StringBuilder();
        
        // Step 2: Traverse the string character by character
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int idx = ch - 'a';
            
            // Skip if the character is already picked in the stack
            if (inStack[idx]) {
                continue;
            }
            
            // Step 3: Pop elements if they are larger than the current char and appear later
            while (resultBuilderStack.length() > 0 
                   && resultBuilderStack.charAt(resultBuilderStack.length() - 1) > ch 
                   && lastOccurrence[resultBuilderStack.charAt(resultBuilderStack.length() - 1) - 'a'] > i) {
                
                char poppedChar = resultBuilderStack.charAt(resultBuilderStack.length() - 1);
                inStack[poppedChar - 'a'] = false;
                resultBuilderStack.deleteCharAt(resultBuilderStack.length() - 1);
            }
            
            // Step 4: Push the current character into our monotonic stack frame
            resultBuilderStack.append(ch);
            inStack[idx] = true;
        }
        
        return resultBuilderStack.toString();
    }

    public static void main(String[] args) {
        // Step 5: Process standard console inputs using custom labeled parameters
        Scanner discreteScanner = new Scanner(System.in);
        System.out.println("Enter string s consisting of lowercase English letters:");
        String userStringParam = discreteScanner.nextLine().trim();
        
        // Step 6: Initialize solver logic class to parse out the optimal subset result
        SmallestDistinctSubsequence1081 discreteSolver = new SmallestDistinctSubsequence1081();
        String uniqueSequenceOutput = discreteSolver.smallestSubsequence(userStringParam);
        
        System.out.println("Lexicographically smallest subsequence: " + uniqueSequenceOutput);
        discreteScanner.close();
    }
}