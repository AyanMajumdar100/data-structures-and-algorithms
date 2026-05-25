/*
 * Problem Statement:
 * You are given a 0-indexed binary string s and two integers minJump and maxJump. 
 * In the beginning, you are standing at index 0, which is equal to '0'. 
 * You can move from index i to index j if:
 * - i + minJump <= j <= min(i + maxJump, s.length - 1)
 * - s[j] == '0'
 * Return true if you can reach index s.length - 1 in s, or false otherwise.
 */

/*
 * Approach: Sliding Window + Dynamic Programming
 * We use a boolean array dp where dp[i] is true if index i is reachable.
 * Instead of checking all possible previous indices for each i (which causes TLE), 
 * we maintain a sliding window of reachable indices. The variable 'active' keeps 
 * track of how many reachable indices exist in the window [i - maxJump, i - minJump].
 * - When moving to index i, the index (i - minJump) enters the window. If it's reachable, active++.
 * - The index (i - maxJump - 1) exits the window. If it was reachable, active--.
 * - If active > 0 and s[i] == '0', the current index i is reachable.
 */
import java.util.Scanner;

public class JumpGame7Reach1871 {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        // Early exit: if the last character is '1', we can never land on it
        if (s.charAt(n - 1) == '1') {
            return false;
        }
        
        char[] arr = s.toCharArray();
        // dp array to track reachable positions
        boolean[] dp = new boolean[n];
        // We start at index 0, so it's reachable
        dp[0] = true;
        
        // Tracks the number of reachable indices in the valid jump window
        int active = 0;
        
        for (int i = 1; i < n; i++) {
            // A new index enters the valid jump window [i - maxJump, i - minJump]
            if (i >= minJump && dp[i - minJump]) {
                active++;
            }
            // An old index exits the valid jump window
            if (i > maxJump && dp[i - maxJump - 1]) {
                active--;
            }
            
            // If there's at least one valid jump origin in the window and current spot is '0'
            if (active > 0 && arr[i] == '0') {
                dp[i] = true;
            }
        }
        
        // Return whether the last index is reachable
        return dp[n - 1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the binary string s:");
        String s = scanner.nextLine().trim();
        
        System.out.println("Enter minJump:");
        int minJump = scanner.nextInt();
        
        System.out.println("Enter maxJump:");
        int maxJump = scanner.nextInt();
        
        JumpGame7Reach1871 solver = new JumpGame7Reach1871();
        boolean result = solver.canReach(s, minJump, maxJump);
        
        System.out.println("Can reach end: " + result);
        scanner.close();
    }
}
