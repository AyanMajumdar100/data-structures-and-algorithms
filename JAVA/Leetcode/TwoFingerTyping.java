/*
Problem Statement:
You have a keyboard layout in the X-Y plane, where each English uppercase letter is located at some coordinate.
Width of the keyboard is 6 (e.g., 'A' is (0,0), 'B' is (0,1), 'G' is (1,0)).
Given the string word, return the minimum total distance to type such string using only two fingers.
The distance between coordinates (x1, y1) and (x2, y2) is |x1 - x2| + |y1 - y2|.
Initial positions of your two fingers are free (cost = 0).

Constraints:
2 <= word.length <= 300
word consists of uppercase English letters.
*/

import java.util.Scanner;

public class TwoFingerTyping {

    // Helper method to calculate the Manhattan distance on the 6-width keyboard
    private static int getDist(int a, int b) {
        // 26 represents an unplaced finger. Placing it costs nothing.
        if (a == 26) return 0; 
        
        // Division by 6 gives the row, modulo 6 gives the column.
        return Math.abs(a / 6 - b / 6) + Math.abs(a % 6 - b % 6);
    }

    public static int minimumDistance(String word) {
        // We use a highly optimized DP array.
        // Instead of tracking both fingers and the string index (3D DP), 
        // we realize that after typing the i-th character, ONE finger MUST be at word[i-1].
        // So, we only need to track the position of the "other" finger!
        // The array index represents the position of the "other" finger (0-25 for A-Z, 26 for unplaced).
        int[] dp = new int[27];
        int[] newDp = new int[27];
        
        // Initial state: One finger is on the first letter of the word.
        // The "other" finger is completely unplaced (represented by index 26).
        for (int i = 0; i < 26; i++) {
            dp[i] = 100000; // Infinity placeholder
        }
        dp[26] = 0; // Cost is 0 when the other finger is unplaced
        
        for (int i = 1; i < word.length(); i++) {
            int curr = word.charAt(i) - 'A';
            int prev = word.charAt(i - 1) - 'A';
            
            // Reset the new state array for this iteration
            for (int j = 0; j < 27; j++) {
                newDp[j] = 100000;
            }
            
            for (int other = 0; other < 27; other++) {
                if (dp[other] != 100000) {
                    // Option 1: The finger at 'prev' moves to 'curr'.
                    // The 'other' finger stays exactly where it is.
                    newDp[other] = Math.min(newDp[other], dp[other] + getDist(prev, curr));
                    
                    // Option 2: The 'other' finger moves to 'curr'.
                    // The finger at 'prev' stays there, so it becomes the new "other" finger.
                    newDp[prev] = Math.min(newDp[prev], dp[other] + getDist(other, curr));
                }
            }
            
            // Swap arrays to move forward in time
            int[] temp = dp;
            dp = newDp;
            newDp = temp;
        }
        
        // The answer is the minimum cost among all possible positions of the "other" finger
        int ans = 100000;
        for (int i = 0; i < 27; i++) {
            if (dp[i] < ans) {
                ans = dp[i];
            }
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter the uppercase word to type:");
            String word = scanner.nextLine().trim();
            
            if (word.isEmpty() || !word.matches("[A-Z]+")) {
                System.out.println("Invalid input. Please enter only uppercase English letters.");
                return;
            }
            
            int result = minimumDistance(word);
            System.out.println("Minimum Total Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("An error occurred during input processing.");
        } finally {
            scanner.close();
        }
    }
}
