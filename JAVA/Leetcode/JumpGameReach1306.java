/*
 * Problem Statement:
 * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
 * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach any index with value 0.
 * Notice that you can not jump outside of the array at any time.
 */

/*
 * Approach: Depth-First Search (DFS)
 * We use a recursive DFS approach to explore all possible valid jumps (left and right) from the current index.
 * To prevent infinite loops (cycles), we mark visited indices by changing their value to -1.
 * If we go out of bounds or step on a visited index (value < 0), we return false.
 * If we land on an index with the value 0, we've found our target and return true.
 */
import java.util.Scanner;

public class JumpGameReach1306 {
    public boolean canReach(int[] arr, int start) {
        // Base case 1: Check if current index is out of bounds or already visited (marked negative)
        if (start < 0 || start >= arr.length || arr[start] < 0) {
            return false;
        }
        
        // Base case 2: If we reach an index containing 0, target is found
        if (arr[start] == 0) {
            return true;
        }
        
        // Store the jump length for the current index
        int jump = arr[start];
        
        // Mark the current index as visited by setting it to a negative value to prevent infinite loops
        arr[start] = -1; 
        
        // Recursively explore jumping to the right OR jumping to the left
        return canReach(arr, start + jump) || canReach(arr, start - jump);
    }

    public static void main(String[] args) {
        // Setup scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        
        // Parse the space-separated string into an integer array
        String[] input = scanner.nextLine().split(" ");
        int[] arr = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            arr[i] = Integer.parseInt(input[i]);
        }
        
        // Read the starting index
        System.out.println("Enter start index:");
        int start = scanner.nextInt();
        
        // Instantiate the solution class and execute the algorithm
        JumpGameReach1306 solution = new JumpGameReach1306();
        System.out.println("Can reach zero? " + solution.canReach(arr, start));
        
        // Clean up resources
        scanner.close();
    }
}