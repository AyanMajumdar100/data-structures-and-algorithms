/*
 * Problem Statement:
 * You are given a string word. A letter is called special if it appears both in 
 * lowercase and uppercase in word.
 * Return the number of special letters in word.
 */

/*
 * Approach: Bit Manipulation
 * We use two integer bitmasks (`lower` and `upper`) to keep track of the letters 
 * we have seen. Since there are only 26 English letters, a 32-bit integer is sufficient.
 * As we iterate through the characters of the string:
 * - If it's a lowercase letter, we set the corresponding bit (0-25) in `lower`.
 * - If it's an uppercase letter, we set the corresponding bit (0-25) in `upper`.
 * Finally, a bitwise AND (`lower & upper`) will leave bits set only for letters 
 * that appeared in both cases. We return the count of set bits.
 */
import java.util.Scanner;

public class CntSpclCharsI3120 {
    public int numberOfSpecialChars(String word) {
        // Bitmask for tracking lowercase characters
        int lower = 0;
        // Bitmask for tracking uppercase characters
        int upper = 0;
        
        // Iterate over each character in the given word
        for (char c : word.toCharArray()) {
            // If the character is lowercase (ASCII value >= 'a')
            if (c >= 'a') {
                // Set the nth bit corresponding to the letter's alphabetical index
                lower |= 1 << (c - 'a');
            } else {
                // Otherwise, it is an uppercase letter. Set the nth bit in upper bitmask
                upper |= 1 << (c - 'A');
            }
        }
        
        // Perform bitwise AND to find letters that exist in both bitmasks
        // Count and return the number of set bits (special characters)
        return Integer.bitCount(lower & upper);
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        
        // Prompt user for input
        System.out.println("Enter the word:");
        String word = scanner.nextLine().trim();
        
        // Instantiate solution class and compute result
        CntSpclCharsI3120 solver = new CntSpclCharsI3120();
        int result = solver.numberOfSpecialChars(word);
        
        // Display result
        System.out.println("Number of special characters: " + result);
        
        // Close resources
        scanner.close();
    }
}