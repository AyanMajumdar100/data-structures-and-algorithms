/*
 * Problem Statement:
 * You are given a string word. A letter c is called special if it appears both in 
 * lowercase and uppercase in word, and every lowercase occurrence of c appears 
 * before the first uppercase occurrence of c.
 * Return the number of special letters in word.
 */

/*
 * Approach:
 * We need to track the last index of each lowercase letter and the first index 
 * of each uppercase letter.
 * 1. Initialize two arrays (size 26) with -1 to store these indices.
 * 2. Iterate through the string:
 * - If it's lowercase, constantly update its position in lastLower (so we get the last occurrence).
 * - If it's uppercase, update its position in firstUpper ONLY if it's currently -1 (so we get the first occurrence).
 * 3. Finally, iterate from 0 to 25. A character is special if it exists in both cases 
 * and its lastLower index is strictly less than its firstUpper index.
 */
import java.util.Scanner;

public class CntSpclChars {
    public int numberOfSpecialChars(String word) {
        // Arrays to keep track of indices
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];
        
        // Initialize arrays with -1
        for (int i = 0; i < 26; i++) {
            lastLower[i] = -1;
            firstUpper[i] = -1;
        }
        
        char[] arr = word.toCharArray();
        // Iterate through the characters of the word
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            // Check if character is lowercase
            if (c >= 'a') {
                // Always update to get the LAST occurrence index
                lastLower[c - 'a'] = i;
            } else {
                int idx = c - 'A';
                // Only update if it's the FIRST occurrence of the uppercase letter
                if (firstUpper[idx] == -1) {
                    firstUpper[idx] = i;
                }
            }
        }
        
        int count = 0;
        // Check condition for all 26 letters
        for (int i = 0; i < 26; i++) {
            // Letter must appear in both cases, and all lowercases must precede first uppercase
            if (lastLower[i] != -1 && firstUpper[i] != -1 && lastLower[i] < firstUpper[i]) {
                count++;
            }
        }
        
        return count;
    }

    public static void main(String[] args) {
        // Setup scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word:");
        
        // Read input
        String word = scanner.nextLine().trim();
        
        // Instantiate solution and get result
        CntSpclCharsII3121 solver = new CntSpclCharsII3121();
        int result = solver.numberOfSpecialChars(word);
        
        // Output result
        System.out.println("Number of special characters: " + result);
        scanner.close();
    }
}
