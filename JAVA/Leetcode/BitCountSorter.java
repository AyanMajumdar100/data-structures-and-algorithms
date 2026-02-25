/*
Problem: Sort Integers by The Number of 1 Bits

You are given an integer array arr.
Sort the integers in ascending order based on:
1) Number of 1s in their binary representation.
2) If two numbers have the same number of 1s → sort by value.

Example:
Input: arr = [0,1,2,3,4,5,6,7,8]
Output: [0,1,2,4,8,3,5,6,7]

Input: arr = [1024,512,256,128,64,32,16,8,4,2,1]
Output: [1,2,4,8,16,32,64,128,256,512,1024]
*/

import java.util.*;

class BitCountSorter {

    /*
    Core idea (logic preserved exactly):

    We temporarily store two pieces of information inside one integer:
    - Higher 16 bits → number of 1s in binary representation
    - Lower 16 bits → original number

    This allows normal sorting to automatically:
    - Sort by bit count first
    - Then by actual value if bit count is same

    After sorting → we extract back the original values.
    */
    public static int[] sortByBits(int[] arr) {

        // Attach bit count into higher bits
        for (int i = 0; i < arr.length; i++) {
            arr[i] |= Integer.bitCount(arr[i]) << 16;
        }

        // Default numeric sort handles both priorities
        Arrays.sort(arr);

        // Remove bit count information to restore original values
        for (int i = 0; i < arr.length; i++) {
            arr[i] &= 0xFFFF;
        }

        return arr;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User enters array elements separated by space
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\\s+");

        int[] arr = new int[parts.length];

        // Convert input to integer array safely
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        int[] result = sortByBits(arr);

        // Print result in array format
        for (int num : result) {
            System.out.print(num + " ");
        }
        scanner.close();
    }
}