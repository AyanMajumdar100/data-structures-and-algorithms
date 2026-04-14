'''
Problem Statement:
There are some robots and factories on the X-axis. 
You are given an integer array robot where robot[i] is the position of the ith robot. 
You are also given a 2D integer array factory where factory[j] = [position, limit] indicates 
that the jth factory is at 'position' and can repair at most 'limit' robots.

All robots are initially broken and move in one direction. You can set the initial direction 
to minimize the total distance traveled by all robots to be repaired.
Return the minimum total distance traveled by all the robots.

Constraints:
1 <= robot.length, factory.length <= 100
factory[j].length == 2
-10^9 <= robot[i], positionj <= 10^9
0 <= limitj <= robot.length
'''

import math

def minimum_total_distance(robot: list[int], factory: list[list[int]]) -> int:
    # Step 1: Sort robots and factories by their positions on the X-axis
    robot.sort()
    factory.sort(key=lambda x: x[0])
    
    n = len(robot)
    
    # Step 2: Flatten the factories into individual slots.
    # We cap the capacity of any single factory to 'n' because we don't need more slots than robots.
    factory_positions = []
    for pos, limit in factory:
        factory_positions.extend([pos] * min(limit, n))
        
    # Step 3: Dynamic Programming setup
    # dp[i] represents the minimum distance to repair the first 'i' robots
    dp = [math.inf] * (n + 1)
    dp[0] = 0  # Cost to repair 0 robots is 0
    
    # Step 4: Iterate through each available slot from left to right
    for p in factory_positions:
        # Traverse backwards to prevent using the same factory slot multiple times in one pass
        for i in range(n, 0, -1):
            if dp[i - 1] != math.inf:
                # Distance for the (i-1)th robot to travel to factory slot 'p'
                current_distance = abs(robot[i - 1] - p)
                # Keep the minimum between ignoring this slot or assigning the robot to it
                dp[i] = min(dp[i], dp[i - 1] + current_distance)
                
    return dp[n]

if __name__ == "__main__":
    try:
        # Robust user input handling
        robot_input = input("Enter robot positions separated by space:\n").strip().split()
        robot = [int(x) for x in robot_input]
        
        num_factories = int(input("Enter number of factories:\n").strip())
        factory = []
        
        print("Enter factory data (position limit) on separate lines:")
        for _ in range(num_factories):
            pos, limit = map(int, input().strip().split())
            factory.append([pos, limit])
            
        result = minimum_total_distance(robot, factory)
        print(f"Minimum Total Distance: {result}")
        
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")