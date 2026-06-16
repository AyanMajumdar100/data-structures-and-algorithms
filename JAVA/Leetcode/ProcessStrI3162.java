/*
 * Problem Statement:
 * You are given a string s consisting of lowercase English letters and the special characters: *, #, and %.
 * Build a new string result by processing s according to the following rules from left to right:
 * - If the letter is a lowercase English letter, append it to result.
 * - A '*' removes the last character from result, if it exists.
 * - A '#' duplicates the current result and appends it to itself.
 * - A '%' reverses the current result.
 * Return the final string result after processing all characters in s.
 */

/*
 * Approach: Simulation using StringBuilder
 * Since the operations modify the string dynamically based on the current state from left to right,
 * we can simulate the process using a `StringBuilder` for efficient mutable string manipulation.
 * We iterate through the characters of s and match each operation:
 * - Lowercase character: standard append().
 * - '*': delete the character at index `length() - 1` if the builder is not empty.
 * - '#': append its own current string representation to itself.
 * - '%': use the built-in reverse() utility.
 * The constraints are very small (s.length <= 20), making simulation highly optimal and clear.
 */
import java.util.Scanner;

public class ProcessStrI3162 {
    public String processStr(String s) {
        StringBuilder result = new StringBuilder();
        
        // Scan the input string sequentially from left to right
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            // Rule 1: Lowercase alphabets are added directly to the tail
            if (c >= 'a' && c <= 'z') {
                result.append(c);
            } 
            // Rule 2: '*' acts as a backspace deleting the last character
            else if (c == '*') {
                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }
            } 
            // Rule 3: '#' mirrors the current buffer by appending itself
            else if (c == '#') {
                result.append(result.toString());
            } 
            // Rule 4: '%' mirrors the buffer longitudinally by inversion
            else if (c == '%') {
                result.reverse();
            }
        }
        
        return result.toString();
    }

    public static void main(String[] args) {
        // Setup scanner to read console inputs
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the instruction string s:");
        String s = scanner.nextLine().trim();
        
        // Execute the simulation mapping
        ProcessStrI3162 solver = new ProcessStrI3162();
        String output = solver.processStr(s);
        
        System.out.println("Result string: \"" + output + "\"");
        scanner.close();
    }
}
