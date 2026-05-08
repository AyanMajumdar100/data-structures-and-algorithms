/*
Problem Statement:
You are given an integer array nums of length n. You start at index 0, and your goal is to reach index n - 1.
From any index i, you may perform one of the following operations:
1. Adjacent Step: Jump to index i + 1 or i - 1.
2. Prime Teleportation: If nums[i] is a prime number p, you may instantly jump to any index j != i 
   such that nums[j] % p == 0.
Return the minimum number of jumps required to reach index n - 1.

Constraints:
1 <= n == nums.length <= 10^5
1 <= nums[i] <= 10^6
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimeTeleportation {

    public static int minJumps(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) maxVal = num;
        }
        
        if (maxVal < 2) return n - 1;
        
        // Step 1: Sieve of Eratosthenes to precompute the Smallest Prime Factor (SPF)
        int[] spf = new int[maxVal + 1];
        for (int i = 2; i <= maxVal; i++) spf[i] = i;
        for (int i = 2; i * i <= maxVal; i++) {
            if (spf[i] == i) {
                for (int j = i * i; j <= maxVal; j += i) {
                    if (spf[j] == j) spf[j] = i;
                }
            }
        }
        
        // Step 2: Build buckets mapping each prime to the indices of numbers divisible by it
        @SuppressWarnings("unchecked")
        List<Integer>[] primeToIndices = new ArrayList[maxVal + 1];
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            while (val > 1) {
                int p = spf[val];
                if (primeToIndices[p] == null) {
                    primeToIndices[p] = new ArrayList<>();
                }
                primeToIndices[p].add(i);
                
                // Divide out all occurrences of this prime factor
                while (val % p == 0) {
                    val /= p;
                }
            }
        }
        
        // Step 3: BFS to find the shortest path
        int[] q = new int[n];
        int head = 0, tail = 0;
        boolean[] visIndex = new boolean[n];
        boolean[] visPrime = new boolean[maxVal + 1];
        
        q[tail++] = 0;
        visIndex[0] = true;
        int jumps = 0;
        
        while (head < tail) {
            int size = tail - head;
            for (int i = 0; i < size; i++) {
                int curr = q[head++];
                
                // Reached the end
                if (curr == n - 1) return jumps;
                
                // Adjacent step forward
                if (curr + 1 < n && !visIndex[curr + 1]) {
                    visIndex[curr + 1] = true;
                    q[tail++] = curr + 1;
                }
                // Adjacent step backward
                if (curr - 1 >= 0 && !visIndex[curr - 1]) {
                    visIndex[curr - 1] = true;
                    q[tail++] = curr - 1;
                }
                
                // Prime Teleportation
                int val = nums[curr];
                // Check if the current value itself is a prime
                if (val >= 2 && spf[val] == val && !visPrime[val]) {
                    visPrime[val] = true; // Mark prime as used to prevent redundant BFS processing
                    
                    if (primeToIndices[val] != null) {
                        for (int nextIdx : primeToIndices[val]) {
                            if (!visIndex[nextIdx]) {
                                visIndex[nextIdx] = true;
                                q[tail++] = nextIdx;
                            }
                        }
                    }
                }
            }
            jumps++;
        }
        
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the array elements separated by spaces:");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Array cannot be empty.");
                return;
            }
            
            String[] strArr = input.split("\\s+");
            int[] nums = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                nums[i] = Integer.parseInt(strArr[i]);
            }
            
            int result = minJumps(nums);
            System.out.println("Minimum Jumps: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input detected. Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}
