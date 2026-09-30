/*
 * Problem Statement: LeetCode 1111 - Maximum Nesting Depth of Two Valid Parentheses Strings
 * Given a valid parentheses string `seq`, split it into two disjoint subsequences A and B (which are also VPS's)
 * such that `max(depth(A), depth(B))` is minimized. Return an answer array where `answer[i]` is 0 if `seq[i]` is in A, and 1 if in B.
 */

/*
 * Approach: Parity-Based Depth Splitting (O(N) Time, O(N) Space)
 * 1. Nesting Depth Property:
 *    - By assigning opening parentheses to alternating groups based on their current depth modulo 2 (`depth % 2`),
 *      we distribute the nesting levels evenly between the two components A and B.
 * 2. Algorithm:
 *    - Keep track of a running `depth` counter starting at 0.
 *    - For each character in `seq`:
 *      * If it is `'('`, assign its group based on `depth % 2`, then increment `depth`.
 *      * If it is `')'`, decrement `depth` first, then assign its group based on `depth % 2`.
 * 3. This guarantees that both resulting subsequences have a maximum nesting depth of at most `(max_depth + 1) / 2`.
 */

import java.util.Arrays;
import java.util.Scanner;

public class MaximumNestingDepthOfTwoValidParenthesesStrings1111 {
    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        int depth = 0;

        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '(') {
                ans[i] = depth % 2;
                depth++;
            } else {
                depth--;
                ans[i] = depth % 2;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter valid parentheses string seq:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: []");
            return;
        }

        MaximumNestingDepthOfTwoValidParenthesesStrings1111 solver = new MaximumNestingDepthOfTwoValidParenthesesStrings1111();
        int[] result = solver.maxDepthAfterSplit(rawInputString);

        System.out.println("Split assignment array: " + Arrays.toString(result));
        scanner.close();
    }
}
