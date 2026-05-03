/*
Problem Statement:
Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
A shift on s consists of moving the leftmost character of s to the rightmost position.

Constraints:
1 <= s.length, goal.length <= 100
s and goal consist of lowercase English letters.
*/

import java.util.Scanner;

public class RotateString {

    public static boolean rotateString(String s, String goal) {
        // If the lengths don't match, they can never be rotations of each other
        if (s.length() != goal.length()) {
            return false;
        }
        
        // s + s contains all possible rotations of s. 
        // For example, if s = "abcde", s + s = "abcdeabcde".
        // The rotation "cdeab" is perfectly contained inside it!
        return (s + s).contains(goal);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the original string (s):");
            String s = scanner.nextLine().trim();
            
            System.out.println("Enter the target string (goal):");
            String goal = scanner.nextLine().trim();
            
            // Basic validation
            if (s.isEmpty() || goal.isEmpty()) {
                System.out.println("Strings cannot be empty.");
                return;
            }
            
            boolean result = rotateString(s, goal);
            System.out.println("Can Rotate: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format detected.");
        } finally {
            scanner.close();
        }
    }
}