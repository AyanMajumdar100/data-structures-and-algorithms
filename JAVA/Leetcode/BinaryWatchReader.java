/*
401. Binary Watch

A binary watch has:
• 4 LEDs for hours (0–11)
• 6 LEDs for minutes (0–59)

Each LED represents a bit (1 = ON, 0 = OFF).

Given an integer turnedOn representing how many LEDs are ON,
return all possible valid times the watch can display.

Rules:
• Hour must NOT have a leading zero → "1:00" valid, "01:00" invalid
• Minute must always have 2 digits → "10:02" valid, "10:2" invalid
• Return results in any order

Examples:
Input: turnedOn = 1
Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]

Input: turnedOn = 9
Output: []
*/

import java.util.*;

class BinaryWatchReader {

    public List<String> readBinaryWatch(int turnedOn) {

        // I will store all valid times here
        List<String> result = new ArrayList<>();

        // I try every possible hour (0 to 11)
        for (int hour = 0; hour < 12; hour++) {

            // I try every possible minute (0 to 59)
            for (int minute = 0; minute < 60; minute++) {

                // Count total number of ON bits in hour + minute
                int totalBits = Integer.bitCount(hour) + Integer.bitCount(minute);

                // If it matches turnedOn, I format the time
                if (totalBits == turnedOn) {

                    // Ensure minute always has two digits
                    String time = hour + ":" + (minute < 10 ? "0" : "") + minute;

                    result.add(time);
                }
            }
        }

        return result;
    }

    // ---------------- USER INPUT DRIVER ----------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of LEDs that are ON: ");
        int turnedOn = sc.nextInt();

        BinaryWatchReader solver = new BinaryWatchReader();
        List<String> times = solver.readBinaryWatch(turnedOn);

        System.out.println("Possible times:");
        for (String t : times) {
            System.out.println(t);
        }

        sc.close();
    }
}
