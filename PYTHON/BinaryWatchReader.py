"""
401. Binary Watch

A binary watch has:
• 4 LEDs for hours (0–11)
• 6 LEDs for minutes (0–59)

Each LED represents a bit (1 = ON, 0 = OFF).

Given an integer turnedOn representing how many LEDs are ON,
return all possible valid times the watch can display.

Rules:
• Hour must NOT have a leading zero → "1:00" valid
• Minute must always have 2 digits → "10:02" valid
• Return results in any order

Examples:
Input: turnedOn = 1
Output: ["0:01","0:02","0:04","0:08","0:16","0:32","1:00","2:00","4:00","8:00"]

Input: turnedOn = 9
Output: []
"""


class BinaryWatchReader:

    def readBinaryWatch(self, turnedOn: int):
        # I will store all valid times here
        result = []

        # I try every possible hour
        for hour in range(12):

            # I try every possible minute
            for minute in range(60):

                # Count ON bits in hour + minute
                total_bits = bin(hour).count('1') + bin(minute).count('1')

                # If it matches turnedOn, I format the time
                if total_bits == turnedOn:
                    time = f"{hour}:{minute:02d}"
                    result.append(time)

        return result


# ---------------- USER INPUT DRIVER ----------------

if __name__ == "__main__":
    turnedOn = int(input("Enter number of LEDs that are ON: "))

    solver = BinaryWatchReader()
    times = solver.readBinaryWatch(turnedOn)

    print("Possible times:")
    for t in times:
        print(t)
