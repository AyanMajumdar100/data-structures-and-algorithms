/*
 * Problem Statement:
 * You are given a binary string s. You can perform at most one trade where you:
 * 1. Convert a contiguous block of '1's surrounded by '0's to all '0's.
 * 2. Afterward, convert a contiguous block of '0's surrounded by '1's to all '1's.
 * Note: s is augmented with a '1' at both ends (t = '1' + s + '1').
 * Return the maximum number of active sections ('1's) in s after making the optimal trade.
 */

/*
 * Approach: Zero-Block Combination Analysis (Run-Length Encoding)
 * 1. Augmented String Representation:
 *    Augmenting s as t = '1' + s + '1' ensures every block of '1's inside is surrounded by '0's or boundary '1's.
 *    A trade effectively flips an internal block of '1's into '0's, merging two adjacent blocks of '0's 
 *    separated by that single '1' block.
 * 2. Then, that whole newly merged block of '0's (surrounded by '1's on both sides) is converted into '1's.
 * 3. Net Effect of a Trade:
 *    Trading a block of '1's between two '0' blocks of length L1 and L2 replaces the '1's with 
 *    '1's across both L1 and L2. The net gain in '1's is equal to L1 + L2.
 * 4. Algorithm:
 *    - Count initial '1's in string s (`totalOnesCount`).
 *    - Collect the lengths of all contiguous blocks of '0's in s into a list `zeroBlockLengths`.
 *    - Find the maximum sum of any two adjacent '0' blocks in `zeroBlockLengths`.
 *    - If fewer than two '0' blocks exist, no valid trade can merge two '0' blocks, so max gain is 0.
 *    - Result is `totalOnesCount + maxGain`.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActiveSectionTradeOptimizer3499 {
    public int maxActiveSectionsAfterTrade(String s) {
        int totalOnesCount = 0;
        int stringLength = s.length();
        
        // Count initial '1's
        for (int idx = 0; idx < stringLength; idx++) {
            if (s.charAt(idx) == '1') {
                totalOnesCount++;
            }
        }

        // Step 1: Collect lengths of all contiguous '0' blocks in the string
        List<Integer> zeroBlockLengths = new ArrayList<>();
        int stringIndex = 0;
        
        while (stringIndex < stringLength) {
            if (s.charAt(stringIndex) == '0') {
                int blockStartPointer = stringIndex;
                while (stringIndex < stringLength && s.charAt(stringIndex) == '0') {
                    stringIndex++;
                }
                zeroBlockLengths.add(stringIndex - blockStartPointer);
            } else {
                stringIndex++;
            }
        }

        // Step 2: Compute maximum gain by merging two adjacent '0' blocks
        int maxTradeGain = 0;
        for (int blockIdx = 0; blockIdx < zeroBlockLengths.size() - 1; blockIdx++) {
            int currentCombinedGain = zeroBlockLengths.get(blockIdx) + zeroBlockLengths.get(blockIdx + 1);
            maxTradeGain = Math.max(maxTradeGain, currentCombinedGain);
        }

        // Step 3: Return original ones count plus optimal gain
        return totalOnesCount + maxTradeGain;
    }

    public static void main(String[] args) {
        // Step 4: Handle standard console interactions using clear variable parameters
        Scanner tradeConsoleScanner = new Scanner(System.in);
        System.out.println("Enter binary string s:");
        String userBinaryStringParam = tradeConsoleScanner.nextLine().trim();

        // Step 5: Execute trade optimizer logic and output result
        ActiveSectionTradeOptimizer3499 tradeSolverInstance = new ActiveSectionTradeOptimizer3499();
        int maximumActiveSections = tradeSolverInstance.maxActiveSectionsAfterTrade(userBinaryStringParam);
        
        System.out.println("Maximum active sections after optimal trade: " + maximumActiveSections);
        tradeConsoleScanner.close();
    }
}
