'''
Problem Statement:
You are given an m x n matrix of characters boxGrid representing a side-view of a box.
A stone '#', A stationary obstacle '*', Empty '.'
The box is rotated 90 degrees clockwise, causing stones to fall due to gravity until they 
hit an obstacle, another stone, or the bottom. 
Return an n x m matrix representing the box after the rotation.

Constraints:
m == boxGrid.length
n == boxGrid[i].length
1 <= m, n <= 500
boxGrid[i][j] is either '#', '*', or '.'.
'''

def rotate_the_box(box_grid: list[list[str]]) -> list[list[str]]:
    m, n = len(box_grid), len(box_grid[0])
    
    # Initialize the n x m result grid with empty spaces
    res = [['.'] * m for _ in range(n)]
    
    for i in range(m):
        # 'empty' pointer marks the furthest down a stone can fall in this column
        empty = n - 1
        
        # Traverse from right to left
        for j in range(n - 1, -1, -1):
            if box_grid[i][j] == '*':
                # Place obstacle in the rotated grid
                res[j][m - 1 - i] = '*'
                # Next empty spot is right above the obstacle
                empty = j - 1
            elif box_grid[i][j] == '#':
                # Place stone at the lowest empty spot
                res[empty][m - 1 - i] = '#'
                # Decrease empty pointer as this spot is now filled
                empty -= 1
                
    return res

if __name__ == "__main__":
    try:
        dims_input = input("Enter number of rows (m) and columns (n) separated by space:\n").strip().split()
        m, n = int(dims_input[0]), int(dims_input[1])
        
        box_grid = []
        print("Enter the grid rows (characters '.', '#', '*' continuously without spaces):")
        for _ in range(m):
            row_str = input().strip()
            if len(row_str) != n:
                print("Row length does not match specified columns. Exiting.")
                exit()
            box_grid.append(list(row_str))
            
        result = rotate_the_box(box_grid)
        
        print("\nRotated Box:")
        for row in result:
            print(row)
            
    except Exception as e:
        print("An error occurred processing your input. Please ensure it is correctly formatted.")