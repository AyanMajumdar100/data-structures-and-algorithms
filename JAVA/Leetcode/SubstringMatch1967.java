/*
 * Problem Statement:
 * Given an array of strings patterns and a string word, return the number of strings 
 * in patterns that exist as a substring in word.
 * A substring is a contiguous sequence of characters within a string.
 */

/*
 * Approach: Linear Scan using String Contains
 * 1. Initialize a counter `count` to 0.
 * 2. Iterate through each string `pattern` inside the `patterns` array.
 * 3. Use the built-in string utility `.contains()` to check if the current `pattern` 
 * is present as a contiguous sequence inside `word`.
 * 4. Increment the counter whenever a match is found.
 * 5. Since constraints are small (lengths <= 100), this straightforward check is highly efficient.
 */
import java.util.Scanner;

public class SubstringMatch1967 {
    public int numOfStrings(String[] patterns, String word) {
        int count = 0;
        
        // Loop through each individual string pattern in our collection
        for (String pattern : patterns) {
            // Check if the pattern sequence is contained anywhere inside the master word
            if (word.contains(pattern)) {
                count++; // Valid substring match found
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the master word string:");
        String word = scanner.nextLine().trim();
        
        System.out.println("Enter patterns separated by space:");
        String[] patterns = scanner.nextLine().trim().split("\\s+");
        
        // Execute lookup logic
        SubstringMatch1967 solver = new SubstringMatch1967();
        int result = solver.numOfStrings(patterns, word);
        
        System.out.println("Number of matching substrings: " + result);
        scanner.close();
    }
}
