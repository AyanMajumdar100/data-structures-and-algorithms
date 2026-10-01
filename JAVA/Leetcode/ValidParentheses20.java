/*
 * Problem Statement: LeetCode 20 - Valid Parentheses
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid (brackets are closed by the same type and in the correct order).
 */

/*
 * Approach: Stack-Based Matching (O(N) Time, O(N) Space)
 * 1. Use a stack to keep track of expected closing brackets or pending opening brackets.
 * 2. Iterate through each character of string `s`:
 *    - If the character is an opening bracket (`'('`, `'{'`, `'['`), push it onto the stack.
 *    - If the character is a closing bracket (`')'`, `'}'`, `']'`):
 *      * Check if the stack is empty (meaning no matching open bracket exists).
 *      * Pop the top element from the stack and verify that it matches the corresponding opening bracket type.
 *      * If it does not match, return false immediately.
 * 3. After processing all characters, return true if and only if the stack is completely empty.
 */

import java.util.Scanner;
import java.util.Stack;

public class ValidParentheses20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Push opening brackets onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // If closing bracket is encountered while stack is empty, it's invalid
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                // Validate correct matching pairs
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }

        // String is valid only if all opening brackets have been matched and popped
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string of brackets:");
        String rawInputString = scanner.nextLine().trim();

        if (rawInputString.isEmpty()) {
            System.out.println("Is valid: true");
            return;
        }

        ValidParentheses20 solver = new ValidParentheses20();
        boolean isValid = solver.isValid(rawInputString);

        System.out.println("Is valid string: " + isValid);
        scanner.close();
    }
}
