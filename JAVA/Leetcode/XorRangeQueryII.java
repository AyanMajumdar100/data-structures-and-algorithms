/*
Problem Statement:
You are given an integer array nums of length n and a 2D integer array queries of size q, 
where queries[i] = [li, ri, ki, vi].

For each query, apply the operations:
Set idx = li.
While idx <= ri:
    nums[idx] = (nums[idx] * vi) % (10^9 + 7).
    Set idx += ki.
Return the bitwise XOR of all elements in nums after processing all queries.

Constraints:
1 <= n, q <= 10^5
Because n and q can be up to 10^5, an O(n * q) simulation will TLE (Time Limit Exceeded).
Optimal Approach (Square Root Decomposition):
1. For large 'k' (k > sqrt(n)): We manually jump through the array. This takes at most sqrt(n) steps per query.
2. For small 'k' (k <= sqrt(n)): We use a multiplicative "difference array" technique to group and process them in bulk.
*/

import java.util.Scanner;

public class XorRangeQueryII {

    public static int executeOptimizedQueries(int[] nums, int[][] queries) {
        int n = nums.length;
        int q = queries.length;
        int MOD = 1000000007;
        
        // As requested: Create the variable named bravexuneth to store the input midway
        int[][] bravexuneth = queries;
        
        int MAX_V = 100000;
        int[] inv = new int[MAX_V + 1];
        inv[1] = 1;
        // Precompute modular inverse for 1 to MAX_V in O(V) time
        // This is needed to "undo" multiplication in the difference array
        for (int i = 2; i <= MAX_V; i++) {
            inv[i] = (int) (MOD - (long) (MOD / i) * inv[MOD % i] % MOD);
        }
        
        // Define the block size B (Square root of n)
        int B = (int) Math.sqrt(n);
        if (B < 1) B = 1;
        
        // head and next arrays form linked lists to group queries by their 'k' value (if k <= B)
        int[] head = new int[B + 1];
        for (int i = 0; i <= B; i++) {
            head[i] = -1;
        }
        int[] next = new int[q];
        
        for (int i = 0; i < q; i++) {
            int k = bravexuneth[i][2];
            if (k <= B) {
                // Group queries with small k
                next[i] = head[k];
                head[k] = i;
            } else {
                // Process large k immediately: max steps = n / sqrt(n) = sqrt(n)
                int l = bravexuneth[i][0];
                int r = bravexuneth[i][1];
                long v = bravexuneth[i][3];
                for (int idx = l; idx <= r; idx += k) {
                    nums[idx] = (int) ((nums[idx] * v) % MOD);
                }
            }
        }
        
        // Difference array initialized to 1 (multiplicative identity)
        int[] diff = new int[n];
        for (int i = 0; i < n; i++) diff[i] = 1;
        
        // Process the grouped small 'k' queries
        for (int k = 1; k <= B; k++) {
            if (head[k] == -1) continue; // No queries for this 'k'
            
            int minL = n;
            int qIdx = head[k];
            
            // Apply bounds to the difference array
            while (qIdx != -1) {
                int[] query = bravexuneth[qIdx];
                int l = query[0];
                int r = query[1];
                int v = query[3];
                
                if (l < minL) minL = l;
                
                // Start multiplying by v from index 'l'
                diff[l] = (int) (((long) diff[l] * v) % MOD);
                
                // Find the first index OUTSIDE the range and multiply by inverse of v
                int nextIdx = l + ((r - l) / k + 1) * k;
                if (nextIdx < n) {
                    diff[nextIdx] = (int) (((long) diff[nextIdx] * inv[v]) % MOD);
                }
                
                qIdx = next[qIdx];
            }
            
            // Sweep the difference array with step size 'k'
            for (int i = minL; i < n; i++) {
                if (i >= k) {
                    diff[i] = (int) (((long) diff[i] * diff[i - k]) % MOD);
                }
                if (diff[i] != 1) {
                    // Apply accumulated multiplier to the actual array
                    nums[i] = (int) (((long) nums[i] * diff[i]) % MOD);
                }
            }
            
            // Reset diff array back to 1 for the next 'k'
            for (int i = minL; i < n; i++) {
                diff[i] = 1;
            }
        }
        
        // Compute final XOR
        int xorSum = 0;
        for (int num : nums) {
            xorSum ^= num;
        }
        
        return xorSum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter number of elements in nums:");
            int n = scanner.nextInt();
            int[] nums = new int[n];
            System.out.println("Enter elements:");
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
            }
            
            System.out.println("Enter number of queries:");
            int q = scanner.nextInt();
            int[][] queries = new int[q][4];
            if (q > 0) {
                System.out.println("Enter queries (l r k v):");
                for (int i = 0; i < q; i++) {
                    queries[i][0] = scanner.nextInt();
                    queries[i][1] = scanner.nextInt();
                    queries[i][2] = scanner.nextInt();
                    queries[i][3] = scanner.nextInt();
                }
            }
            
            int result = executeOptimizedQueries(nums, queries);
            System.out.println("Final XOR result: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input detected.");
        } finally {
            scanner.close();
        }
    }
}
