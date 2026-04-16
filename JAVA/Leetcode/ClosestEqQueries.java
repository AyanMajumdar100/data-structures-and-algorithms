/*
Problem Statement:
You are given a circular array nums and an array queries.
For each query i, you have to find the minimum distance between the element at 
index queries[i] and any other index j in the circular array, where nums[j] == nums[queries[i]]. 
If no such index exists, the answer for that query should be -1.
Return an array answer of the same size as queries.

Constraints:
1 <= queries.length <= nums.length <= 10^5
1 <= nums[i] <= 10^6
0 <= queries[i] < nums.length
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClosestEqQueries {

    public static List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;
        
        // Find the maximum value in nums to size the head array properly.
        // This is extremely fast and avoids the overhead of HashMap.
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }
        
        // Use arrays to represent a linked list of indices for each distinct value
        int[] head = new int[maxVal + 1];
        Arrays.fill(head, -1);
        int[] next = new int[n];
        
        // Traverse backwards so the linked list inherently stays sorted by index
        for (int i = n - 1; i >= 0; i--) {
            int v = nums[i];
            next[i] = head[v];
            head[v] = i;
        }
        
        // Precompute the answer for all indices to process queries in O(1) time
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        
        for (int i = 0; i <= maxVal; i++) {
            // Only process if the value appears at least twice
            if (head[i] != -1 && next[head[i]] != -1) {
                int first = head[i];
                int last = first;
                
                // Find the very last occurrence to establish the circular connection
                while (next[last] != -1) {
                    last = next[last];
                }
                
                int prev = last;
                int curr = first;
                
                while (curr != -1) {
                    int nxt = next[curr];
                    if (nxt == -1) nxt = first; // Wrap around to the start
                    
                    // Calculate circular distance to the previous occurrence
                    int d1 = Math.abs(curr - prev);
                    d1 = Math.min(d1, n - d1);
                    
                    // Calculate circular distance to the next occurrence
                    int d2 = Math.abs(curr - nxt);
                    d2 = Math.min(d2, n - d2);
                    
                    // The minimum of the two is the absolute closest distance
                    ans[curr] = Math.min(d1, d2);
                    
                    prev = curr;
                    curr = next[curr]; // Advance to the next occurrence
                }
            }
        }
        
        // Construct the final result list for the given queries
        List<Integer> res = new ArrayList<>(queries.length);
        for (int q : queries) {
            res.add(ans[q]);
        }
        
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the array elements separated by spaces:");
            String[] numsInput = scanner.nextLine().trim().split("\\s+");
            int[] nums = new int[numsInput.length];
            for (int i = 0; i < numsInput.length; i++) {
                nums[i] = Integer.parseInt(numsInput[i]);
            }
            
            System.out.println("Enter the query indices separated by spaces:");
            String[] queriesInput = scanner.nextLine().trim().split("\\s+");
            int[] queries = new int[queriesInput.length];
            for (int i = 0; i < queriesInput.length; i++) {
                queries[i] = Integer.parseInt(queriesInput[i]);
            }
            
            List<Integer> result = solveQueries(nums, queries);
            System.out.println("Output: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}
