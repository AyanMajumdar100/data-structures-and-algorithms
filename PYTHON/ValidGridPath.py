'''
Problem Statement:
You are given an m x n grid. Each cell of grid represents a street. The street of grid[i][j] can be:
1: a street connecting the left cell and the right cell.
2: a street connecting the upper cell and the lower cell.
3: a street connecting the left cell and the lower cell.
4: a street connecting the right cell and the lower cell.
5: a street connecting the left cell and the upper cell.
6: a street connecting the right cell and the upper cell.

You will initially start at the street of the upper-left cell (0, 0). A valid path in the grid is a path 
that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1). 
The path should only follow the streets. Notice that you are not allowed to change any street.
Return true if there is a valid path in the grid or false otherwise.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 300
1 <= grid[i][j] <= 6
'''

from collections import deque

def has_valid_path(grid: list[list[int]]) -> bool:
    m, n = len(grid), len(grid[0])
    
    if m == 1 and n == 1:
        return True
        
    visited = [[False] * n for _ in range(m)]
    q = deque([(0, 0)])
    visited[0][0] = True
    
    # Directions: 0: Up, 1: Right, 2: Down, 3: Left
    dr = [-1, 0, 1, 0]
    dc = [0, 1, 0, -1]
    
    # Bitmask mappings for street types 1-6. 
    # Binary positions map to directions: Left(bit 3), Down(bit 2), Right(bit 1), Up(bit 0)
    masks = [0, 10, 5, 12, 6, 9, 3]
    
    while q:
        r, c = q.popleft()
        mask = masks[grid[r][c]]
        
        for d in range(4):
            # If current piece connects in direction 'd'
            if mask & (1 << d):
                nr = r + dr[d]
                nc = c + dc[d]
                
                # Verify bounds and visited state
                if 0 <= nr < m and 0 <= nc < n and not visited[nr][nc]:
                    n_mask = masks[grid[nr][nc]]
                    
                    # The piece we are stepping into must connect BACK to us 
                    # from the opposite direction (d + 2) % 4
                    op = (d + 2) % 4
                    
                    if n_mask & (1 << op):
                        if nr == m - 1 and nc == n - 1:
                            return True
                            
                        visited[nr][nc] = True
                        q.append((nr, nc))
                        
    return False

if __name__ == "__main__":
    try:
        dims_input = input("Enter number of rows and columns (e.g. '2 3'):\n").strip().split()
        m, n = int(dims_input[0]), int(dims_input[1])
        
        grid = []
        print("Enter the grid rows (numbers separated by spaces):")
        for _ in range(m):
            row = list(map(int, input().strip().split()))
            grid.append(row)
            
        result = has_valid_path(grid)
        print(f"Has Valid Path: {result}")
        
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")