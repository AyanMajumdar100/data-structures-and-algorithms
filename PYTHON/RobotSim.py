'''
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
'''

def robot_sim(commands, obstacles):
    # Directions: North, East, South, West
    dirs = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    
    d = 0 # 0 corresponds to North
    x, y = 0, 0
    max_dist_sq = 0
    
    # In Python, tuples are highly optimized and hashable.
    # Converting the list of lists into a set of tuples provides O(1) lookups.
    obs_set = set(map(tuple, obstacles))
    
    for cmd in commands:
        if cmd == -2:
            # Turn left 90 degrees
            d = (d + 3) % 4
        elif cmd == -1:
            # Turn right 90 degrees
            d = (d + 1) % 4
        else:
            # Move forward cmd steps
            for _ in range(cmd):
                nx = x + dirs[d][0]
                ny = y + dirs[d][1]
                
                # Check if the next intended step is an obstacle
                if (nx, ny) not in obs_set:
                    x, y = nx, ny
                    # Calculate and store the maximum distance squared
                    max_dist_sq = max(max_dist_sq, x*x + y*y)
                else:
                    # Obstacle hit, break out of moving loop
                    break
                    
    return max_dist_sq

if __name__ == "__main__":
    try:
        # Robust user input handling
        cmds_input = input("Enter commands separated by space: ").strip().split()
        commands = [int(c) for c in cmds_input]
        
        num_obs = int(input("Enter number of obstacles: ").strip())
        obstacles = []
        
        if num_obs > 0:
            print("Enter obstacles (x y for each line):")
            for _ in range(num_obs):
                obs = list(map(int, input().strip().split()))
                obstacles.append(obs)
                
        result = robot_sim(commands, obstacles)
        print(f"Max Euclidean Distance Squared: {result}")
        
    except ValueError:
        print("Invalid input. Please enter valid integers.")