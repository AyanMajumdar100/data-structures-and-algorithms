/*
 * Problem Statement:
 * You are given an array of strings words, where each string represents a word containing lowercase English letters.
 * You are also given an integer array weights of length 26, where weights[i] represents the weight of the ith lowercase English letter.
 * The weight of a word is defined as the sum of the weights of its characters.
 * For each word, take its weight modulo 26 and map the result to a lowercase English letter using reverse alphabetical order (0 -> 'z', 1 -> 'y', ..., 25 -> 'a').
 * Return a string formed by concatenating the mapped characters for all words in order.
 */

/*
 * Approach:
 * 1. Maintain a character array `result` of size equal to the number of words.
 * 2. Loop through each word in the input array.
 * 3. For each word, initialize `totalWeight` to 0 and sum up the weights of all its characters by mapping them to their positions via (char - 'a').
 * 4. Apply modulo 26 to the accumulated weight.
 * 5. Map the final index to a reverse-alphabetical character using the expression: 'z' - (totalWeight % 26).
 * 6. Construct and return a single concatenated string from the character array.
 */
import java.util.Scanner;

public class WrdWghtMap3838 {
    public String mapWordWeights(String[] words, int[] weights) {
        // Prepare character array to store the result mapping for each word
        char[] result = new char[words.length];
        
        // Process every word in the input sequence
        for (int i = 0; i < words.length; i++) {
            int totalWeight = 0;
            String word = words[i];
            
            // Sum the weight of each character in the current word
            for (int j = 0; j < word.length(); j++) {
                totalWeight += weights[word.charAt(j) - 'a'];
            }
            
            // Map the remainder to its character equivalent in reverse alphabetical order
            result[i] = (char) ('z' - (totalWeight % 26));
        }
        
        // Return the final accumulated mapping as a new string
        return new String(result);
    }

    public static void main(String[] args) {
        // Setup scanner to read user inputs
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter words separated by space:");
        String[] words = scanner.nextLine().trim().split("\\s+");
        
        System.out.println("Enter 26 integer weights separated by space:");
        String[] weightsStr = scanner.nextLine().trim().split("\\s+");
        int[] weights = new int[26];
        for (int i = 0; i < 26; i++) {
            weights[i] = Integer.parseInt(weightsStr[i]);
        }
        
        // Instantiate solver and display result
        WrdWghtMap3838 solver = new WrdWghtMap3838();
        String output = solver.mapWordWeights(words, weights);
        System.out.println("Mapped Output String: " + output);
        
        scanner.close();
    }
}
