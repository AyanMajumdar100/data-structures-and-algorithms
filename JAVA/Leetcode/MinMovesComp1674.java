/*
 * Problem Statement:
 * You are given an integer array nums of even length n and an integer limit. In one move, you can 
 * replace any integer from nums with another integer between 1 and limit, inclusive.
 * The array nums is complementary if for all indices i (0-indexed), nums[i] + nums[n - 1 - i] equals 
 * the same number. Return the minimum number of moves required to make nums complementary.
 */

/*
 * Approach: Line Sweep / Difference Array
 * For each pair of symmetric elements, let a = min(left, right) and b = max(left, right).
 * Any target sum T must be in the range [2, 2*limit]. 
 * By default, assume reaching any sum T takes 2 moves. 
 * Then apply intervals of adjustments using a difference array (delta):
 * - T in [a+1, a+b-1] takes 1 move (we subtract 1 move at index a+1).
 * - T == a+b takes 0 moves (we subtract another 1 move at index a+b).
 * - T in [a+b+1, b+limit] takes 1 move (we add 1 move at index a+b+1).
 * - T in [b+limit+1, 2*limit] takes 2 moves (we add 1 move at index b+limit+1).
 * After processing all pairs, a prefix sum over delta reveals the total moves for each target T.
 */
import java.util.Scanner;
import java.util.Arrays;

public class MinMovesComp1674 {

    public int minMoves(int[] nums, int limit) {
        int n = nums.length;
        // Difference array to track moves changes. Size covers up to 2*limit + 1, so +2 for safety.
        int[] delta = new int[2 * limit + 2];
        int half = n / 2;
        
        // Process each pair of symmetric elements
        for (int i = 0; i < half; i++) {
            int left = nums[i];
            int right = nums[n - 1 - i];
            // Identify smaller and larger elements of the pair
            int a = Math.min(left, right);
            int b = Math.max(left, right);
            
            // Base case: starting from sum = 2, requires 2 moves maximum
            delta[2] += 2;
            
            // Sum range [a+1, a+b-1] needs 1 move instead of 2. Decrease requirement by 1.
            delta[a + 1] -= 1;
            
            // Sum exact matching (a+b) needs 0 moves. Decrease requirement by another 1.
            delta[a + b] -= 1;
            
            // Sum range [a+b+1, b+limit] needs 1 move. Increase requirement by 1.
            delta[a + b + 1] += 1;
            
            // Sum range [b+limit+1, 2*limit] needs 2 moves. Increase requirement by 1.
            delta[b + limit + 1] += 1;
        }
        
        int minMoves = n; // Maximum possible moves is changing all elements
        int currentMoves = 0;
        int maxSum = 2 * limit;
        
        // Calculate prefix sum to find the minimum moves required for any target sum T
        for (int i = 2; i <= maxSum; i++) {
            currentMoves += delta[i];
            if (currentMoves < minMoves) {
                minMoves = currentMoves;
            }
        }
        
        return minMoves;
    }

    public static void main(String[] args) {
        // User Input Simulation
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter limit:");
        int limit = scanner.nextInt();
        
        System.out.println("Enter number of elements (even):");
        int n = scanner.nextInt();
        
        int[] nums = new int[n];
        System.out.println("Enter the array elements:");
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        
        MinMovesComp1674 solution = new MinMovesComp1674();
        int result = solution.minMoves(nums, limit);
        System.out.println("Minimum moves: " + result);
        scanner.close();
    }
}