/*
 * Problem Statement: LeetCode 3498 - Reverse Degree of a String
 * Given a string s, calculate its reverse degree.
 * The reverse degree is the sum over each character of:
 * (position in reversed alphabet: 'a' = 26, 'b' = 25, ..., 'z' = 1) * (1-indexed position in the string).
 * Return the total reverse degree of s.
 */

/*
 * Approach: Linear Scan & Position Multiplication (O(N) Time, O(1) Space)
 * 1. Iterate through each character of string `s` with 0-based index `i`.
 * 2. Calculate the reversed alphabet position: `revAlphabet = 26 - (c - 'a')`.
 * 3. Calculate the 1-based string position: `stringIndex = i + 1`.
 * 4. Multiply these two values and accumulate into a running `total` sum.
 * 5. Return `total`.
 */

import java.util.Scanner;

public class ReverseDegreeOfString3498 {
    public int reverseDegree(String s) {
        int total = 0;
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            int revAlphabet = 26 - (c - 'a');
            int stringIndex = i + 1;
            total += revAlphabet * stringIndex;
        }
        
        return total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string s:");
        String sParam = scanner.nextLine().trim();

        ReverseDegreeOfString3498 solver = new ReverseDegreeOfString3498();
        int result = solver.reverseDegree(sParam);

        System.out.println("Reverse degree of string: " + result);
        scanner.close();
    }
}
