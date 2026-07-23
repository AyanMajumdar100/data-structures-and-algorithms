/*
 * Problem Statement:
 * You are given an integer array nums of length n, which is a permutation of integers in the range [1, n].
 * A XOR triplet is defined as nums[i] XOR nums[j] XOR nums[k] where i <= j <= k.
 * Return the number of unique XOR triplet values possible.
 */

/*
 * Approach: Bitwise Power-of-Two Bound (O(1) / O(log N))
 * 1. Base Cases:
 *    - For n = 1, the only element is 1. The only XOR triplet is 1 ^ 1 ^ 1 = 1. Output = 1.
 *    - For n = 2, possible values are 1 and 2. Triplets yield {1, 2}. Output = 2.
 * 2. General Case (n >= 3):
 *    - Because nums contains all numbers from 1 to n, we can generate any bit combination 
 *      up to the largest power of 2 required to represent n.
 *    - Specifically, if `x` is the position of the Most Significant Bit (MSB) of n, 
 *      we can form every integer from 0 up to `(2^(x + 1) - 1)` using XOR operations of 3 elements.
 *    - Thus, the total count of unique XOR values is equal to the smallest power of 2 
 *      strictly greater than n, which is `1 << (floor(log2(n)) + 1)`.
 */

import java.util.Scanner;

public class UniqueXorTriplets3513 {
    public int uniqueXorTriplets(int[] nums) {
        int arrayLength = nums.length;
        
        // Base cases for small array sizes where full bit combinations cannot be formed
        if (arrayLength < 3) {
            return arrayLength;
        }
        
        // Find the next power of 2 strictly greater than n
        int mostSignificantBit = Integer.highestOneBit(arrayLength);
        return mostSignificantBit << 1;
    }

    public static void main(String[] args) {
        Scanner tripletScanner = new Scanner(System.in);
        System.out.println("Enter permutation length n:");
        int userPermutationLength = tripletScanner.nextInt();
        
        int[] userPermutationArray = new int[userPermutationLength];
        System.out.println("Enter elements of permutation array from 1 to n:");
        for (int i = 0; i < userPermutationLength; i++) {
            userPermutationArray[i] = tripletScanner.nextInt();
        }
        
        UniqueXorTriplets3513 solverInstance = new UniqueXorTriplets3513();
        int uniqueTripletsCount = solverInstance.uniqueXorTriplets(userPermutationArray);
        
        System.out.println("Number of unique XOR triplet values: " + uniqueTripletsCount);
        tripletScanner.close();
    }
}