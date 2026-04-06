/*
Problem Statement:
A robot on an infinite XY-plane starts at point (0, 0) facing north. The robot receives an array 
of integers commands, representing a sequence of moves.
Types of instructions:
-2: Turn left 90 degrees.
-1: Turn right 90 degrees.
1 <= k <= 9: Move forward k units, one unit at a time.
Obstacles are at grid point obstacles[i] = (xi, yi). If the robot runs into an obstacle, it stays 
in its current location and moves onto the next command.
Return the maximum squared Euclidean distance that the robot reaches at any point in its path.
*/

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RobotSim {

    public static int executeSimulation(int[] commands, int[][] obstacles) {
        // Directions represent: North, East, South, West respectively.
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        
        int d = 0; // Starts facing North (index 0)
        int x = 0, y = 0; // Starting coordinates
        int maxDistSq = 0; // Tracks the maximum distance squared
        
        // HashSet to store obstacles for O(1) lookup time
        Set<Long> obsSet = new HashSet<>();
        for (int[] obs : obstacles) {
            // Optimize: Pack two 32-bit ints into one 64-bit long to save memory/time.
            // Left shift X by 32 bits, and use bitwise OR with Y (masked to handle negative numbers).
            obsSet.add(((long) obs[0] << 32) | (obs[1] & 0xFFFFFFFFL));
        }
        
        for (int cmd : commands) {
            if (cmd == -2) {
                // Turn left: Add 3 to simulate moving backward in the circular array (-1 + 4)
                d = (d + 3) % 4;
            } else if (cmd == -1) {
                // Turn right: Move forward in the circular array
                d = (d + 1) % 4;
            } else {
                // Move forward step by step
                for (int i = 0; i < cmd; i++) {
                    int nx = x + dirs[d][0];
                    int ny = y + dirs[d][1];
                    
                    // Calculate the packed long representation of the next step
                    long nextPos = ((long) nx << 32) | (ny & 0xFFFFFFFFL);
                    
                    // If the next step is NOT an obstacle, move there
                    if (!obsSet.contains(nextPos)) {
                        x = nx;
                        y = ny;
                        // Update max distance squared
                        maxDistSq = Math.max(maxDistSq, x * x + y * y);
                    } else {
                        // Hit an obstacle, stop moving for this command
                        break;
                    }
                }
            }
        }
        return maxDistSq;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("Enter number of commands:");
            int n = scanner.nextInt();
            int[] commands = new int[n];
            System.out.println("Enter commands:");
            for (int i = 0; i < n; i++) {
                commands[i] = scanner.nextInt();
            }
            
            System.out.println("Enter number of obstacles:");
            int m = scanner.nextInt();
            int[][] obstacles = new int[m][2];
            if (m > 0) {
                System.out.println("Enter obstacles (x y for each):");
                for (int i = 0; i < m; i++) {
                    obstacles[i][0] = scanner.nextInt();
                    obstacles[i][1] = scanner.nextInt();
                }
            }
            
            int result = executeSimulation(commands, obstacles);
            System.out.println("Max Euclidean Distance Squared: " + result);
            
        } catch (Exception e) {
            System.out.println("Invalid input format.");
        } finally {
            scanner.close();
        }
    }
}