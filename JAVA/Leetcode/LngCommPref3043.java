/*
 * Problem Statement:
 * You are given two arrays with positive integers arr1 and arr2.
 * You need to find the length of the longest common prefix between all pairs of 
 * integers (x, y) such that x belongs to arr1 and y belongs to arr2.
 * Return the length of the longest common prefix among all pairs. If no common 
 * prefix exists among them, return 0.
 */

/*
 * Approach: HashSet for Prefix Storage
 * We extract every possible prefix from each number in arr1 by continuously dividing 
 * the number by 10, and store these prefixes in a HashSet.
 * Then, for each number in arr2, we also continuously divide by 10 to check if its 
 * prefix exists in our HashSet. If a match is found, we calculate the number of digits 
 * of this common prefix and update the maximum length found so far.
 */
import java.util.Scanner;
import java.util.HashSet;

public class LngCommPref3043 {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {
        // HashSet to store all prefixes from arr1
        HashSet<Integer> prefixes = new HashSet<>();
        
        // Generate all prefixes for each number in arr1
        for (int num : arr1) {
            while (num > 0) {
                prefixes.add(num);
                num /= 10; // Remove the last digit to get the next prefix
            }
        }
        
        int maxLength = 0;
        
        // Check prefixes of each number in arr2 against the HashSet
        for (int num : arr2) {
            // Keep reducing num until it's a prefix present in the HashSet or becomes 0
            while (num > 0 && !prefixes.contains(num)) {
                num /= 10;
            }
            
            // If a common prefix is found, calculate its length
            if (num > 0) {
                int len = (int) Math.log10(num) + 1;
                if (len > maxLength) {
                    maxLength = len;
                }
            }
        }
        
        return maxLength;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Handle user input for arr1
        System.out.println("Enter elements of arr1 separated by space:");
        String[] input1 = scanner.nextLine().trim().split("\\s+");
        int[] arr1 = new int[input1.length];
        for (int i = 0; i < input1.length; i++) {
            arr1[i] = Integer.parseInt(input1[i]);
        }
        
        // Handle user input for arr2
        System.out.println("Enter elements of arr2 separated by space:");
        String[] input2 = scanner.nextLine().trim().split("\\s+");
        int[] arr2 = new int[input2.length];
        for (int i = 0; i < input2.length; i++) {
            arr2[i] = Integer.parseInt(input2[i]);
        }
        
        LngCommPref3043 solver = new LngCommPref3043();
        System.out.println("Length of longest common prefix: " + solver.longestCommonPrefix(arr1, arr2));
        
        scanner.close();
    }
}
