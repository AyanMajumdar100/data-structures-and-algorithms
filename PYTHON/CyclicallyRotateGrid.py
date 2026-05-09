'''
Problem Statement:
You are given an m x n integer matrix grid, where m and n are both even integers, and an integer k.
A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. 
To cyclically rotate a layer once, each element in the layer will take the place of the 
adjacent element in the counter-clockwise direction.
Return the matrix after applying k cyclic rotations to it.

Constraints:
m == grid.length
n == grid[i].length
2 <= m, n <= 50
Both m and n are even integers.
1 <= grid[i][j] <= 5000
1 <= k <= 10^9
'''

def rotate_grid(grid: list[list[int]], k: int) -> list[list[int]]:
    m, n = len(grid), len(grid[0])
    num_layers = min(m, n) // 2
    
    for layer in range(num_layers):
        r1, c1 = layer, layer
        r2, c2 = m - 1 - layer, n - 1 - layer
        
        # Flatten the layer counter-clockwise
        arr = []
        
        # Left column (down)
        for i in range(r1, r2): arr.append(grid[i][c1])
        # Bottom row (right)
        for j in range(c1, c2): arr.append(grid[r2][j])
        # Right column (up)
        for i in range(r2, r1, -1): arr.append(grid[i][c2])
        # Top row (left)
        for j in range(c2, c1, -1): arr.append(grid[r1][j])
        
        size = len(arr)
        rot = k % size
        
        # Shift the 1D array effectively by combining slices
        # Shifting counter-clockwise pushes elements "forward" in the array,
        # which equates to a right-rotation in the 1D list structure.
        shifted_arr = arr[-rot:] + arr[:-rot]
        idx = 0
        
        # Write the shifted elements back in the exact same counter-clockwise path
        for i in range(r1, r2):
            grid[i][c1] = shifted_arr[idx]
            idx += 1
        for j in range(c1, c2):
            grid[r2][j] = shifted_arr[idx]
            idx += 1
        for i in range(r2, r1, -1):
            grid[i][c2] = shifted_arr[idx]
            idx += 1
        for j in range(c2, c1, -1):
            grid[r1][j] = shifted_arr[idx]
            idx += 1
            
    return grid

if __name__ == "__main__":
    try:
        dims_input = input("Enter number of rows (m) and columns (n) - both must be EVEN:\n").strip().split()
        m, n = int(dims_input[0]), int(dims_input[1])
        
        if m % 2 != 0 or n % 2 != 0:
            print("Both dimensions must be even integers.")
            exit()
            
        grid = []
        print("Enter the grid rows (numbers separated by spaces):")
        for _ in range(m):
            row = list(map(int, input().strip().split()))
            grid.append(row)
            
        k = int(input("Enter the number of rotations (k):\n").strip())
        
        result = rotate_grid(grid, k)
        
        print("\nRotated Grid:")
        for row in result:
            print(row)
            
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")