/*
 * Problem Statement:
 * You are given two arrays of strings wordsContainer and wordsQuery.
 * For each wordsQuery[i], you need to find a string from wordsContainer that has the 
 * longest common suffix with wordsQuery[i]. If there are two or more strings in 
 * wordsContainer that share the longest common suffix, find the string that is the 
 * smallest in length. If there are two or more such strings that have the same 
 * smallest length, find the one that occurred earlier in wordsContainer.
 * Return an array of integers ans, where ans[i] is the index of the string in 
 * wordsContainer that has the longest common suffix with wordsQuery[i].
 */

/*
 * Approach: Trie (Suffix Tree)
 * To efficiently find common suffixes, we can insert all strings from wordsContainer 
 * into a Trie in reverse order (backwards). 
 * Each TrieNode will maintain a `bestIdx`, which stores the index of the optimal string 
 * (shortest length, breaking ties with the earliest index) that passes through this node.
 * For each query, we search the Trie backwards character by character. We stop when we 
 * can't match a character anymore, and return the `bestIdx` of the deepest matched node.
 */
import java.util.Scanner;
import java.util.Arrays;

public class LngComSufTrie3093 {
    
    // Inner class representing a node in the Trie
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int bestIdx;
        
        // Constructor initializes the node with the current best string index
        TrieNode(int bestIdx) {
            this.bestIdx = bestIdx;
        }
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        // Track the index of the globally best string (shortest length, earliest index)
        // This handles cases where a query has no common suffix with any string.
        int globalBest = 0;
        for (int i = 1; i < wordsContainer.length; i++) {
            if (wordsContainer[i].length() < wordsContainer[globalBest].length()) {
                globalBest = i;
            }
        }

        // Initialize the root with the global best index
        TrieNode root = new TrieNode(globalBest);

        // Build the Trie with reversed words from wordsContainer
        for (int i = 0; i < wordsContainer.length; i++) {
            String w = wordsContainer[i];
            TrieNode curr = root;
            
            // Traverse the string backwards to insert suffixes
            for (int j = w.length() - 1; j >= 0; j--) {
                int c = w.charAt(j) - 'a';
                
                // If child node doesn't exist, create it with the current string's index
                if (curr.children[c] == null) {
                    curr.children[c] = new TrieNode(i);
                } else {
                    // If child node exists, check if the current string is a better fit 
                    // (shorter length) than the one previously recorded at this node.
                    int currBest = curr.children[c].bestIdx;
                    if (w.length() < wordsContainer[currBest].length()) {
                        curr.children[c].bestIdx = i;
                    }
                }
                // Move down the Trie
                curr = curr.children[c];
            }
        }

        int[] ans = new int[wordsQuery.length];
        
        // Process each query
        for (int i = 0; i < wordsQuery.length; i++) {
            String q = wordsQuery[i];
            TrieNode curr = root;
            
            // Traverse the Trie using the query string reversed
            for (int j = q.length() - 1; j >= 0; j--) {
                int c = q.charAt(j) - 'a';
                // Stop traversing if there is no further matching suffix character
                if (curr.children[c] == null) {
                    break;
                }
                curr = curr.children[c];
            }
            
            // The bestIdx at the deepest reached node is our answer for this query
            ans[i] = curr.bestIdx;
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter wordsContainer separated by space:");
        String[] wordsContainer = scanner.nextLine().trim().split("\\s+");
        
        System.out.println("Enter wordsQuery separated by space:");
        String[] wordsQuery = scanner.nextLine().trim().split("\\s+");
        
        LngComSufTrie3093 solver = new LngComSufTrie3093();
        int[] result = solver.stringIndices(wordsContainer, wordsQuery);
        
        System.out.println("Result: " + Arrays.toString(result));
        
        scanner.close();
    }
}