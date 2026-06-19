/*
 * Problem Statement:
 * There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes. 
 * The biker starts his trip on point 0 with altitude equal 0.
 * You are given an integer array gain of length n where gain[i] is the net gain in altitude 
 * between points i and i + 1. Return the highest altitude of a point.
 */

/*
 * Approach: Prefix Sum Tracking (Single Pass)
 * 1. Initialize `maxAltitude` to 0 because the biker starts at altitude 0.
 * 2. Track the `currentAltitude`, starting at 0.
 * 3. Iterate through the `gain` array. For each element, add the net gain to `currentAltitude`.
 * 4. At each point, compare `currentAltitude` with `maxAltitude` and update `maxAltitude` if the current one is higher.
 * 5. Return the maximum altitude recorded. This takes O(N) time and O(1) space.
 */
import java.util.Scanner;

public class MaxAltitude1732 {
    public int largestAltitude(int[] gain) {
        // Biker starts at altitude 0, so the max altitude is at least 0
        int maxAltitude = 0;
        int currentAltitude = 0;
        
        // Accumulate gains sequentially to map out the altitude timeline
        for (int g : gain) {
            currentAltitude += g;
            
            // Check if the newly reached altitude point breaks our record high
            if (currentAltitude > maxAltitude) {
                maxAltitude = currentAltitude;
            }
        }
        
        return maxAltitude;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter altitude gains separated by space:");
        
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Highest altitude: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] gain = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            gain[i] = Integer.parseInt(parts[i]);
        }
        
        MaxAltitude1732 solver = new MaxAltitude1732();
        int result = solver.largestAltitude(gain);
        
        System.out.println("Highest altitude: " + result);
        scanner.close();
    }
}
