'''
Problem Statement:
You are given an array tasks where tasks[i] = [actual_i, minimum_i]:
- actual_i is the actual amount of energy you spend to finish the i-th task.
- minimum_i is the minimum amount of energy you require to begin the i-th task.
You can finish the tasks in any order you like.
Return the minimum initial amount of energy you will need to finish all the tasks.

Constraints:
1 <= tasks.length <= 10^5
1 <= actual_i <= minimum_i <= 10^4
'''

def minimum_effort(tasks: list[list[int]]) -> int:
    # Sort tasks descending by (minimum - actual).
    # This prioritizes tasks that require a high threshold to start but consume less energy.
    tasks.sort(key=lambda x: x[1] - x[0], reverse=True)
    
    ans = 0
    current_energy = 0
    
    for act, min_req in tasks:
        # If our running current_energy is less than what's needed to start, 
        # we must increase our initial energy pool (ans) to bridge the gap.
        if current_energy < min_req:
            ans += min_req - current_energy
            current_energy = min_req
            
        # Complete the task, consuming the actual energy
        current_energy -= act
        
    return ans

if __name__ == "__main__":
    try:
        n = int(input("Enter the number of tasks:\n").strip())
        
        tasks = []
        print("Enter each task's actual and minimum energy separated by a space (e.g., '1 2'):")
        for _ in range(n):
            parts = input().strip().split()
            tasks.append([int(parts[0]), int(parts[1])])
            
        result = minimum_effort(tasks)
        print(f"Minimum Initial Energy Required: {result}")
            
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")
