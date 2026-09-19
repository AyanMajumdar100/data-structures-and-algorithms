/*
 * Problem Statement: LeetCode 1401 - Circle and Rectangle Overlapping
 * You are given a circle (radius, xCenter, yCenter) and an axis-aligned rectangle (x1, y1, x2, y2).
 * Return true if the circle and rectangle overlap (share at least one point), otherwise return false.
 */

/*
 * Approach: Nearest Point Clamp / Euclidean Distance Check (O(1) Time, O(1) Space)
 * 1. Find the point on (or inside) the rectangle that is closest to the circle's center `(xCenter, yCenter)`:
 *    - Clamp `xCenter` between `x1` and `x2`: `closestX = max(x1, min(xCenter, x2))`
 *    - Clamp `yCenter` between `y1` and `y2`: `closestY = max(y1, min(yCenter, y2))`
 * 2. Calculate the squared Euclidean distance from the circle center to this closest point:
 *    - `distX = xCenter - closestX`
 *    - `distY = yCenter - closestY`
 *    - `distanceSquared = distX * distX + distY * distY`
 * 3. Compare the squared distance with the squared radius:
 *    - Return true if `distanceSquared <= radius * radius`, meaning the closest point lies 
 *      within or on the boundary of the circle.
 */

import java.util.Scanner;

public class CircleAndRectangleOverlapping1401 {
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        // Step 1: Find the closest point on the rectangle to the circle center
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Step 2: Compute squared distances along x and y axes
        int distX = xCenter - closestX;
        int distY = yCenter - closestY;

        // Step 3: Check if squared distance is less than or equal to squared radius
        return (distX * distX + distY * distY) <= (radius * radius);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter radius, xCenter, yCenter separated by space:");
        int radius = scanner.nextInt();
        int xCenter = scanner.nextInt();
        int yCenter = scanner.nextInt();

        System.out.println("Enter rectangle bounds x1, y1, x2, y2 separated by space:");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        int x2 = scanner.nextInt();
        int y2 = scanner.nextInt();

        CircleAndRectangleOverlapping1401 solver = new CircleAndRectangleOverlapping1401();
        boolean isOverlapping = solver.checkOverlap(radius, xCenter, yCenter, x1, y1, x2, y2);

        System.out.println("Do the circle and rectangle overlap? " + isOverlapping);
        scanner.close();
    }
}
