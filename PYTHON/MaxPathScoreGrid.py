'''
Problem Statement:
You are given an m x n grid where each cell contains one of the values 0, 1, or 2. 
You are also given an integer k.

You start from the top-left corner (0, 0) and want to reach the bottom-right corner 
(m - 1, n - 1) by moving only right or down.

Each cell contributes a specific score and incurs an associated cost:
0: adds 0 to your score and costs 0.
1: adds 1 to your score and costs 1.
2: adds 2 to your score and costs 1.

Return the maximum score achievable without exceeding a total cost of k, 
or -1 if no valid path exists.
Note: If you reach the last cell but the total cost exceeds k, the path is invalid.

Constraints:
1 <= m, n <= 200
0 <= k <= 1000
grid[0][0] == 0
0 <= grid[i][j] <= 2
'''

def max_path_score(grid: list[list[int]], k: int) -> int:
    m, n = len(grid), len(grid[0])
    
    # prev[j][c] stores the max score at column j with total cost c for the previous row
    prev = [[-1] * (k + 1) for _ in range(n)]
    
    # Base case setup
    prev[0][0] = 0
    
    for i in range(m):
        # curr[j][c] stores the max score at column j with total cost c for the current row
        curr = [[-1] * (k + 1) for _ in range(n)]
        
        for j in range(n):
            if i == 0 and j == 0:
                curr[0][0] = 0
                continue
                
            cost = 0 if grid[i][j] == 0 else 1
            score = grid[i][j]
            
            # Evaluate all cost states that can afford the current cell
            for c in range(cost, k + 1):
                max_score = -1
                
                # Check path coming from the top
                if i > 0 and prev[j][c - cost] != -1:
                    max_score = max(max_score, prev[j][c - cost] + score)
                    
                # Check path coming from the left
                if j > 0 and curr[j - 1][c - cost] != -1:
                    max_score = max(max_score, curr[j - 1][c - cost] + score)
                    
                curr[j][c] = max_score
                
        # Advance the DP boundary
        prev = curr
        
    # Return the maximum score from all valid cost paths at the destination
    ans = max(prev[n - 1])
    return ans

if __name__ == "__main__":
    try:
        dims_input = input("Enter number of rows and columns (e.g., '2 2'):\n").strip().split()
        m, n = int(dims_input[0]), int(dims_input[1])
        
        grid = []
        print("Enter the grid rows (values 0, 1, or 2 separated by spaces):")
        for _ in range(m):
            row = list(map(int, input().strip().split()))
            grid.append(row)
            
        k = int(input("Enter the maximum allowed cost (k):\n").strip())
        
        result = max_path_score(grid, k)
        print(f"Maximum Achievable Score: {result}")
        
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")