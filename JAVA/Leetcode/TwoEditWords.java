/*
Problem Statement:
You are given two string arrays, queries and dictionary. All words in each array comprise 
of lowercase English letters and have the same length.

In one edit you can take a word from queries, and change any letter in it to any other letter. 
Find all words from queries that, after a maximum of two edits, equal some word from dictionary.
Return a list of all words from queries, that match with some word from dictionary after a 
maximum of two edits. Return the words in the same order they appear in queries.

Constraints:
1 <= queries.length, dictionary.length <= 100
n == queries[i].length == dictionary[j].length
1 <= n <= 100
All queries[i] and dictionary[j] are composed of lowercase English letters.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoEditWords {

    public static List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> res = new ArrayList<>();
        
        // Check every query against the dictionary
        for (String q : queries) {
            for (String d : dictionary) {
                int diff = 0;
                
                // Compare characters one by one
                for (int i = 0; i < q.length(); i++) {
                    if (q.charAt(i) != d.charAt(i)) {
                        diff++;
                    }
                    
                    // Optimization: Early exit if we exceed 2 edits. 
                    // No need to check the rest of the string.
                    if (diff > 2) {
                        break;
                    }
                }
                
                // If we found a match within the allowed edits, add to result and move to NEXT query
                if (diff <= 2) {
                    res.add(q);
                    break; 
                }
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the words for 'queries' separated by spaces:");
            String[] queries = scanner.nextLine().trim().split("\\s+");
            
            System.out.println("Enter the words for 'dictionary' separated by spaces:");
            String[] dictionary = scanner.nextLine().trim().split("\\s+");
            
            // Basic validation
            if (queries.length == 0 || dictionary.length == 0 || queries[0].isEmpty() || dictionary[0].isEmpty()) {
                System.out.println("Arrays cannot be empty.");
                return;
            }
            
            List<String> result = twoEditWords(queries, dictionary);
            System.out.println("Output: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}