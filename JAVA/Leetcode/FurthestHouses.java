/*
Problem Statement:
There are n houses evenly lined up on the street, and each house is beautifully painted. 
You are given a 0-indexed integer array colors of length n, where colors[i] represents the color of the ith house.

Return the maximum distance between two houses with different colors.
The distance between the ith and jth houses is abs(i - j).

Constraints:
n == colors.length
2 <= n <= 100
0 <= colors[i] <= 100
Test data are generated such that at least two houses have different colors.
*/

import java.util.Scanner;

public class FurthestHouses {

    public static int maxDistance(int[] colors) {
        int n = colors.length;
        int res = 0;
        
        // Scenario 1: Fix the first house (colors[0]) and search from the right end.
        // We look for the furthest house that has a different color than the first house.
        for (int i = n - 1; i >= 0; i--) {
            if (colors[i] != colors[0]) {
                // The distance is simply i - 0 = i
                res = Math.max(res, i);
                break; // Found the furthest one, no need to check closer houses
            }
        }
        
        // Scenario 2: Fix the last house (colors[n - 1]) and search from the left end.
        // We look for the furthest house that has a different color than the last house.
        for (int i = 0; i < n; i++) {
            if (colors[i] != colors[n - 1]) {
                // The distance is (n - 1) - i
                res = Math.max(res, n - 1 - i);
                break; // Found the furthest one, no need to check closer houses
            }
        }
        
        // The overall maximum distance must be the larger of Scenario 1 or Scenario 2
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the colors of the houses separated by spaces:");
            String[] inputStr = scanner.nextLine().trim().split("\\s+");
            
            if (inputStr.length < 2) {
                System.out.println("At least two houses are required.");
                return;
            }

            int[] colors = new int[inputStr.length];
            for (int i = 0; i < inputStr.length; i++) {
                colors[i] = Integer.parseInt(inputStr[i]);
            }
            
            int result = maxDistance(colors);
            System.out.println("Maximum Distance: " + result);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers only.");
        } finally {
            scanner.close();
        }
    }
}