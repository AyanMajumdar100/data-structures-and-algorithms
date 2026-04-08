/*
Problem Statement:
You are given an integer array nums of length n and a 2D integer array queries of size q, 
where queries[i] = [li, ri, ki, vi].

For each query, you must apply the following operations in order:
- Set idx = li.
- While idx <= ri:
    - Update: nums[idx] = (nums[idx] * vi) % (10^9 + 7)
    - Set idx += ki.

Return the bitwise XOR of all elements in nums after processing all queries.

Constraints:
1 <= n == nums.length <= 10^3
1 <= queries.length <= 10^3
Because n and q are small (<= 1000), an O(n * q) simulation approach is optimal and easily passes within time limits. 
Advanced data structures (like segment trees) are unnecessary here and complicate the variable step size (ki).
*/

import java.util.Scanner;

public class XorRangeMulti {

    public static int executeQueries(int[] nums, int[][] queries) {
        int MOD = 1000000007;
        
        // Process each query one by one
        for (int[] query : queries) {
            int l = query[0];  // starting index
            int r = query[1];  // ending index limit
            int k = query[2];  // step size
            long v = query[3]; // multiplier (use long to prevent overflow during multiplication)
            
            // Traverse the specified range with the given step size
            for (int i = l; i <= r; i += k) {
                // Multiply, take modulo to prevent the number from growing too large, and cast back to int
                nums[i] = (int) ((nums[i] * v) % MOD);
            }
        }
        
        int result = 0;
        // Compute the bitwise XOR of all elements in the modified array
        for (int num : nums) {
            result ^= num;
        }
        
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter the number of elements in nums:");
            int n = scanner.nextInt();
            int[] nums = new int[n];
            
            System.out.println("Enter the elements of nums:");
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
            }
            
            System.out.println("Enter the number of queries:");
            int q = scanner.nextInt();
            int[][] queries = new int[q][4];
            
            if (q > 0) {
                System.out.println("Enter each query (l r k v) separated by space:");
                for (int i = 0; i < q; i++) {
                    queries[i][0] = scanner.nextInt();
                    queries[i][1] = scanner.nextInt();
                    queries[i][2] = scanner.nextInt();
                    queries[i][3] = scanner.nextInt();
                }
            }
            
            int finalXor = executeQueries(nums, queries);
            System.out.println("XOR of all elements after queries: " + finalXor);
            
        } catch (Exception e) {
            System.out.println("Invalid input format. Please try again.");
        } finally {
            scanner.close();
        }
    }
}
