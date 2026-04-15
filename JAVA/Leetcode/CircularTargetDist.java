/*
Problem Statement:
You are given a 0-indexed circular string array words and a string target. 
A circular array means that the array's end connects to the array's beginning.
Starting from startIndex, you can move to either the next word or the previous word with 1 step at a time.
Return the shortest distance needed to reach the string target. 
If the string target does not exist in words, return -1.

Constraints:
1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] and target consist of only lowercase English letters.
0 <= startIndex < words.length
*/

import java.util.Scanner;

public class CircularTargetDist {

    public static int closestTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        
        // Optimize: Expand outward from the startIndex.
        // We only need to check up to n / 2 steps because if the distance is greater than n / 2 
        // in one direction, it means it's a shorter distance going the opposite direction!
        for (int i = 0; i <= n / 2; i++) {
            // Check 'i' steps to the right. Modulo 'n' handles the wrap-around at the end.
            if (words[(startIndex + i) % n].equals(target)) {
                return i;
            }
            
            // Check 'i' steps to the left. We add 'n' before modulo to prevent negative numbers.
            if (words[(startIndex - i + n) % n].equals(target)) {
                return i;
            }
        }
        
        // Target was not found anywhere in the array
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the words separated by spaces:");
            String[] words = scanner.nextLine().trim().split("\\s+");
            
            if (words.length == 0 || words[0].isEmpty()) {
                System.out.println("Words array cannot be empty.");
                return;
            }
            
            System.out.println("Enter the target string:");
            String target = scanner.nextLine().trim();
            
            System.out.println("Enter the start index:");
            int startIndex = scanner.nextInt();
            
            if (startIndex < 0 || startIndex >= words.length) {
                System.out.println("Invalid start index. Must be within array bounds.");
                return;
            }

            int result = closestTarget(words, target, startIndex);
            System.out.println("Shortest Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input detected. Please try again.");
        } finally {
            scanner.close();
        }
    }
}
