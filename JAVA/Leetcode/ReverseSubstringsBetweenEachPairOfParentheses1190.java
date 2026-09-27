/*
 * Problem Statement: LeetCode 1190 - Reverse Substrings Between Each Pair of Parentheses
 * You are given a string s that consists of lower case English letters and brackets.
 * Reverse the strings in each pair of matching parentheses, starting from the innermost one.
 * Your result should not contain any brackets.
 */

/*
 * Approach: Wormhole Index Matching / Direction-Flipping Traversal (O(N) Time, O(N) Space)
 * 1. Parentheses Matching:
 *    - Use a stack to find indices of matching parenthesis pairs. Store them in an array `pair`,
 *      where `pair[i] = j` means the parenthesis at index `i` matches the one at index `j`.
 * 2. Wormhole Traversal:
 *    - Start traversing the string from `i = 0` with a moving `direction = 1` (moving forward).
 *    - Whenever you encounter a parenthesis (`'('` or `')'`):
 *      * Teleport pointer `i` directly to its matching partner: `i = pair[i]`.
 *      * Flip the traversal direction: `direction = -direction`.
 *    - If it's a regular character, append it to the result `StringBuilder`.
 *    - Increment/decrement `i` by `direction`.
 * 3. This clever traversal effectively "reverses" string reading order on-the-fly in linear time $O(N)$.
 */

import java.util.Scanner;
import java.util.Stack;

public class ReverseSubstringsBetweenEachPairOfParentheses1190 {
    public String reverseParentheses(String s) {
        int n = s.length();
        int[] pair = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Step 1: Pre-compute matching parenthesis indices using a stack
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }

        // Step 2: Traverse using wormhole jumps and direction flipping
        StringBuilder sb = new StringBuilder();
        int i = 0, direction = 1;
        while (i < n) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                i = pair[i];
                direction = -direction;
            } else {
                sb.append(c);
            }
            i += direction;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s with parentheses:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Result: ");
            return;
        }

        ReverseSubstringsBetweenEachPairOfParentheses1190 solver = new ReverseSubstringsBetweenEachPairOfParentheses1190();
        String result = solver.reverseParentheses(rawInputString);

        System.out.println("Reversed parentheses result: " + result);
        scanner.close();
    }
}
