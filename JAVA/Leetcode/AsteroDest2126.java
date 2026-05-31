/*
 * Problem Statement:
 * You are given an integer mass, which represents the original mass of a planet. 
 * You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.
 * You can arrange for the planet to collide with the asteroids in any arbitrary order. 
 * If the mass of the planet is greater than or equal to the mass of the asteroid, the asteroid is 
 * destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.
 * Return true if all asteroids can be destroyed. Otherwise, return false.
 */

/*
 * Approach: Greedy with Sorting
 * To maximize the chances of destroying all asteroids, the planet should always collide 
 * with the smallest available asteroids first. This allows the planet to build up its mass 
 * before facing larger asteroids. 
 * 1. Sort the asteroids array in ascending order.
 * 2. Use a `long` variable for the planet's mass to prevent integer overflow during accumulation.
 * 3. Iterate through the sorted asteroids. If the current mass is smaller than the asteroid's mass, return false.
 * 4. Otherwise, add the asteroid's mass to the current mass and continue.
 * 5. If the loop completes successfully, return true.
 */
import java.util.Arrays;
import java.util.Scanner;

public class AsteroDest2126 {

    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        // Sort the asteroids to face the smallest ones first (Greedy approach)
        Arrays.sort(asteroids);
        
        // Use long to prevent integer overflow when adding asteroid masses
        long currentMass = mass;
        
        // Iterate through each asteroid
        for (int asteroid : asteroids) {
            // If the planet's mass is less than the asteroid, it gets destroyed
            if (currentMass < asteroid) {
                return false;
            }
            // Otherwise, destroy the asteroid and absorb its mass
            currentMass += asteroid;
        }
        
        // All asteroids were successfully destroyed
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get the initial mass of the planet
        System.out.println("Enter the initial mass of the planet:");
        if (!scanner.hasNextInt()) return;
        int mass = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Get the masses of the asteroids
        System.out.println("Enter the asteroid masses separated by space:");
        String inputLine = scanner.nextLine().trim();
        
        if (inputLine.isEmpty()) {
            System.out.println("Result: true");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] asteroids = new int[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            asteroids[i] = Integer.parseInt(parts[i]);
        }
        
        // Execute the algorithm and print the result
        AsteroDest2126 solver = new AsteroDest2126();
        boolean result = solver.asteroidsDestroyed(mass, asteroids);
        
        System.out.println("All asteroids destroyed? " + result);
        
        scanner.close();
    }
}