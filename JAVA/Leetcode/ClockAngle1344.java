/*
 * Problem Statement:
 * Given two numbers, hour and minutes, return the smaller angle (in degrees) 
 * formed between the hour and the minute hand.
 */

/*
 * Approach: Math / Reference Angle from 12:00
 * A complete clock circle is 360 degrees, divided into 60 minutes and 12 hours.
 * * 1. Minute Hand: Moves 360 / 60 = 6 degrees per minute.
 * 2. Hour Hand: Moves 360 / 12 = 30 degrees per hour. 
 * Additionally, it moves slightly as minutes pass: 30 degrees / 60 minutes = 0.5 degrees per minute.
 * * We calculate the absolute position of both hands relative to the 12:00 mark (0 degrees):
 * - minuteAngle = minutes * 6
 * - hourAngle = (hour % 12) * 30 + minutes * 0.5
 * * The difference between these two angles gives the angle between the hands. 
 * Since the problem asks for the *smaller* angle, we return min(diff, 360 - diff).
 */
import java.util.Scanner;

public class ClockAngle1344 {
    public double angleClock(int hour, int minutes) {
        // Calculate the absolute angle of the minute hand from 12 o'clock position
        double minuteAngle = minutes * 6.0;
        
        // Calculate the absolute angle of the hour hand from 12 o'clock position
        // hour % 12 handles 12:00 behaving as 0 degrees baseline
        double hourAngle = (hour % 12) * 30.0 + minutes * 0.5;
        
        // Find the absolute difference between both angles
        double diff = Math.abs(hourAngle - minuteAngle);
        
        // Return the minimal interior angle
        return Math.min(diff, 360.0 - diff);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the hour (1-12):");
        int hour = scanner.nextInt();
        
        System.out.println("Enter the minutes (0-59):");
        int minutes = scanner.nextInt();
        
        ClockAngle1344 solver = new ClockAngle1344();
        double result = solver.angleClock(hour, minutes);
        
        System.out.println("Smaller angle between hands: " + result + "°");
        
        scanner.close();
    }
}