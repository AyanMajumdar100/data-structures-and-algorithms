/*
 * Problem Statement:
 * You are given an integer array nums.
 * A XOR triplet is defined as the XOR of three elements nums[i] XOR nums[j] XOR nums[k] where i <= j <= k.
 * Return the number of unique XOR triplet values from all possible triplets (i, j, k).
 */

/*
 * Approach: Dynamic Bitwise Subset State Aggregation (O(U^2 + 2048 * U) Time, O(2048) Space)
 * 1. Collect unique elements into an array `uniqueArr` since indices can be repeated.
 * 2. Compute all possible 2-element XOR combinations into a boolean array `xor2` of size 2048 (since max nums[i] <= 1500 < 2048).
 * 3. Combine valid 2-element XOR values with single unique elements to construct `xor3`.
 * 4. Count all `true` flags in `xor3`.
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UniqueXorTripletsII3514 {
    public int uniqueXorTriplets(int[] nums) {
        // Step 1: Collect unique elements to avoid redundant combinations
        Set<Integer> uniqueNumsSet = new HashSet<>();
        for (int num : nums) {
            uniqueNumsSet.add(num);
        }
        
        int[] uniqueArr = new int[uniqueNumsSet.size()];
        int arrayIdx = 0;
        for (int num : uniqueNumsSet) {
            uniqueArr[arrayIdx++] = num;
        }
        
        int uniqueCount = uniqueArr.length;
        
        // Step 2: Compute all possible 2-element XOR values (bounded by 2048)
        boolean[] xor2 = new boolean[2048];
        for (int i = 0; i < uniqueCount; i++) {
            for (int j = i; j < uniqueCount; j++) {
                xor2[uniqueArr[i] ^ uniqueArr[j]] = true;
            }
        }
        
        // Step 3: Compute all possible 3-element XOR values by pairing xor2 flags with unique elements
        boolean[] xor3 = new boolean[2048];
        for (int val = 0; val < 2048; val++) {
            if (xor2[val]) {
                for (int num : uniqueArr) {
                    xor3[val ^ num] = true;
                }
            }
        }
        
        // Step 4: Count unique 3-element XOR results
        int resultCount = 0;
        for (boolean present : xor3) {
            if (present) {
                resultCount++;
            }
        }
        
        return resultCount;
    }

    public static void main(String[] args) {
        // Handle input streams using custom variable names
        Scanner xorScanner = new Scanner(System.in);
        System.out.println("Enter array elements separated by space:");
        String rawInputString = xorScanner.nextLine().trim();
        
        if (rawInputString.isEmpty()) {
            System.out.println("Number of unique XOR triplets: 0");
            return;
        }
        
        String[] tokenParts = rawInputString.split("\\s+");
        int[] userNumsParam = new int[tokenParts.length];
        for (int i = 0; i < tokenParts.length; i++) {
            userNumsParam[i] = Integer.parseInt(tokenParts[i]);
        }
        
        UniqueXorTripletsII3514 solverInstance = new UniqueXorTripletsII3514();
        int uniqueCountResult = solverInstance.uniqueXorTriplets(userNumsParam);
        
        System.out.println("Number of unique XOR triplets: " + uniqueCountResult);
        xorScanner.close();
    }
}
