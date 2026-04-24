/*
Problem Statement:
You are given a string moves of length n consisting only of characters 'L', 'R', and '_'. 
The string represents your movement on a number line starting from the origin 0.

In the ith move, you can choose one of the following directions:
- move to the left if moves[i] = 'L' or moves[i] = '_'
- move to the right if moves[i] = 'R' or moves[i] = '_'

Return the distance from the origin of the furthest point you can get to after n moves.

Constraints:
1 <= moves.length == n <= 50
moves consists only of characters 'L', 'R' and '_'.
*/

import java.util.Scanner;

public class FurthestOrigin {

    public static int furthestDistanceFromOrigin(String moves) {
        int netPosition = 0;
        int freeMoves = 0;
        
        // Traverse the string exactly once
        for (int i = 0; i < moves.length(); i++) {
            char c = moves.charAt(i);
            
            if (c == 'L') {
                // Move left pushes us into the negatives
                netPosition--;
            } else if (c == 'R') {
                // Move right pushes us into the positives
                netPosition++;
            } else {
                // Underscores are wildcards we save for the end
                freeMoves++;
            }
        }
        
        // The furthest distance is the absolute value of our fixed moves 
        // plus all our wildcard moves acting in that same direction.
        return Math.abs(netPosition) + freeMoves;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the moves string (only L, R, _ allowed):");
            String moves = scanner.nextLine().trim();
            
            // Input robustness check
            if (!moves.matches("[LR_]+")) {
                System.out.println("Invalid input. Please use only 'L', 'R', or '_' characters.");
                return;
            }
            
            int result = furthestDistanceFromOrigin(moves);
            System.out.println("Furthest Distance: " + result);
            
        } catch (Exception e) {
            System.out.println("An error occurred processing your input.");
        } finally {
            scanner.close();
        }
    }
}
