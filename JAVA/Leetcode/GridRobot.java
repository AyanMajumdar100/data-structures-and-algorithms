/*
Problem Statement:
A width x height grid is on an XY-plane with the bottom-left cell at (0, 0) and the top-right cell 
at (width - 1, height - 1). The grid is aligned with the four cardinal directions. 
A robot is initially at cell (0, 0) facing direction "East".

The robot moves forward along the perimeter. If out of bounds, it turns 90 degrees counterclockwise.
Implement the Robot class:
- Robot(int width, int height): Initializes the grid.
- void step(int num): Moves forward num steps.
- int[] getPos(): Returns [x, y].
- String getDir(): Returns "North", "East", "South", or "West".
*/

import java.util.Arrays;
import java.util.Scanner;

public class GridRobot {
    private int w;
    private int h;
    private int perimeter; // Total steps to make a full loop around the grid
    private int pos;       // Current 1D position along the perimeter
    private boolean moved; // Tracks if the robot has ever moved to handle the (0,0) direction edge case

    public GridRobot(int width, int height) {
        this.w = width;
        this.h = height;
        // The perimeter is sum of all 4 sides minus the 4 overlapping corners
        this.perimeter = 2 * (w - 1) + 2 * (h - 1);
        this.pos = 0;
        this.moved = false;
    }
    
    public void step(int num) {
        // Optimize: Instead of simulating step-by-step, we use modulo math 
        // to instantly find the final position on the perimeter. O(1) time.
        pos = (pos + num) % perimeter;
        moved = true;
    }
    
    public int[] getPos() {
        // Map the 1D perimeter position back to 2D (x, y) coordinates
        if (pos < w) {
            // Bottom edge (moving East)
            return new int[]{pos, 0};
        } else if (pos < w + h - 1) {
            // Right edge (moving North)
            return new int[]{w - 1, pos - (w - 1)};
        } else if (pos < 2 * w + h - 2) {
            // Top edge (moving West)
            return new int[]{w - 1 - (pos - (w + h - 2)), h - 1};
        } else {
            // Left edge (moving South)
            return new int[]{0, h - 1 - (pos - (2 * w + h - 3))};
        }
    }
    
    public String getDir() {
        // Direction is purely based on which edge of the perimeter the robot is on
        if (pos == 0) {
            // Special case: If at (0,0), it faces East initially, but if it has moved, 
            // it means it completed a loop coming down the Left edge, so it faces South.
            return moved ? "South" : "East";
        } else if (pos < w) {
            return "East";  // Bottom edge
        } else if (pos < w + h - 1) {
            return "North"; // Right edge
        } else if (pos < 2 * w + h - 2) {
            return "West";  // Top edge
        } else {
            return "South"; // Left edge
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter grid width and height (e.g., 6 3): ");
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            
            GridRobot robot = new GridRobot(width, height);
            System.out.println("Robot initialized at (0,0) facing East.");
            System.out.println("Commands: 'step <num>', 'pos', 'dir', 'exit'");
            
            while (true) {
                System.out.print("> ");
                String cmd = scanner.next();
                
                if (cmd.equalsIgnoreCase("exit")) {
                    break;
                } else if (cmd.equalsIgnoreCase("step")) {
                    int steps = scanner.nextInt();
                    robot.step(steps);
                    System.out.println("Stepped " + steps + " times.");
                } else if (cmd.equalsIgnoreCase("pos")) {
                    System.out.println("Position: " + Arrays.toString(robot.getPos()));
                } else if (cmd.equalsIgnoreCase("dir")) {
                    System.out.println("Direction: " + robot.getDir());
                } else {
                    System.out.println("Invalid command.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input format detected. Exiting.");
        } finally {
            scanner.close();
        }
    }
}
