/*
Problem Statement:
You are given an array tasks where tasks[i] = [actual_i, minimum_i]:
- actual_i is the actual amount of energy you spend to finish the i-th task.
- minimum_i is the minimum amount of energy you require to begin the i-th task.
You can finish the tasks in any order you like.
Return the minimum initial amount of energy you will need to finish all the tasks.

Constraints:
1 <= tasks.length <= 10^5
1 <= actual_i <= minimum_i <= 10^4
*/

import java.util.Arrays;
import java.util.Scanner;

public class MinimumInitialEnergy {

    public static int minimumEffort(int[][] tasks) {
        int n = tasks.length;
        long[] packed = new long[n];
        
        for (int i = 0; i < n; i++) {
            long act = tasks[i][0];
            long min = tasks[i][1];
            long diff = min - act;
            
            // Pack the data into a single 64-bit integer to sort primitives extremely fast:
            // Top 32 bits = diff (for sorting priority)
            // Next 16 bits = minimum energy
            // Lowest 16 bits = actual energy
            packed[i] = (diff << 32) | (min << 16) | act;
        }
        
        // Sort ascending by the packed values. 
        // Because 'diff' is in the highest bits, it dictates the primary sort order.
        Arrays.sort(packed);
        
        int ans = 0;
        int currentEnergy = 0;
        
        // Iterate backwards to process the largest 'diff' values first
        for (int i = n - 1; i >= 0; i--) {
            long val = packed[i];
            
            // Extract the original values using bitwise AND masking
            int act = (int) (val & 0xFFFF);
            int min = (int) ((val >> 16) & 0xFFFF);
            
            // If we don't have enough current energy to start the task, we must add to our initial pool
            if (currentEnergy < min) {
                ans += min - currentEnergy;
                currentEnergy = min;
            }
            // Consume the actual energy required for the task
            currentEnergy -= act;
        }
        
        return ans;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the number of tasks:");
            int n = Integer.parseInt(scanner.nextLine().trim());
            
            int[][] tasks = new int[n][2];
            System.out.println("Enter each task's actual and minimum energy separated by a space (e.g., '1 2'):");
            for (int i = 0; i < n; i++) {
                String[] parts = scanner.nextLine().trim().split("\\s+");
                tasks[i][0] = Integer.parseInt(parts[0]);
                tasks[i][1] = Integer.parseInt(parts[1]);
            }
            
            int result = minimumEffort(tasks);
            System.out.println("Minimum Initial Energy Required: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}
