'''
Problem Statement:
Given two numbers, hour and minutes, return the smaller angle (in degrees) 
formed between the hour and the minute hand.
'''

'''
Approach: Math / Reference Angle from 12:00
A complete clock circle is 360 degrees, divided into 60 minutes and 12 hours.

1. Minute Hand: Moves 360 / 60 = 6 degrees per minute.
2. Hour Hand: Moves 360 / 12 = 30 degrees per hour. 
   Additionally, it moves slightly as minutes pass: 30 degrees / 60 minutes = 0.5 degrees per minute.

We calculate the absolute position of both hands relative to the 12:00 mark (0 degrees):
- minute_angle = minutes * 6
- hour_angle = (hour % 12) * 30 + minutes * 0.5

The difference between these two angles gives the angle between the hands. 
Since the problem asks for the *smaller* angle, we return min(diff, 360 - diff).
'''
class ClockAngle1344:
    def angleClock(self, hour: int, minutes: int) -> float:
        # Calculate the absolute angle of the minute hand from 12 o'clock position
        minute_angle = minutes * 6.0
        
        # Calculate the absolute angle of the hour hand from 12 o'clock position
        # hour % 12 handles 12:00 behaving as 0 degrees baseline
        hour_angle = (hour % 12) * 30.0 + minutes * 0.5
        
        # Find the absolute difference between both angles
        diff = abs(hour_angle - minute_angle)
        
        # Return the minimal interior angle
        return min(diff, 360.0 - diff)

if __name__ == '__main__':
    try:
        hour_val = int(input("Enter the hour (1-12): "))
        min_val = int(input("Enter the minutes (0-59): "))
        
        if not (1 <= hour_val <= 12) or not (0 <= min_val <= 59):
            print("Please enter values within the clock constraints.")
        else:
            solver = ClockAngle1344()
            result = solver.angleClock(hour_val, min_val)
            print(f"Smaller angle between hands: {result}°")
    except ValueError:
        print("Invalid input format. Please enter integers only.")