'''
Problem Statement:
You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. 
DO NOT allocate another 2D matrix and do the rotation.

Constraints:
n == matrix.length == matrix[i].length
1 <= n <= 20
-1000 <= matrix[i][j] <= 1000
'''

def rotate(matrix: list[list[int]]) -> None:
    """
    Do not return anything, modify matrix in-place instead.
    """
    n = len(matrix)
    
    # Step 1: Transpose the matrix
    for i in range(n):
        for j in range(i, n):
            # Python's tuple unpacking allows us to swap without a temp variable
            matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
            
    # Step 2: Reverse each row
    for i in range(n):
        # C-optimized reverse method is incredibly fast
        matrix[i].reverse()

if __name__ == "__main__":
    try:
        n = int(input("Enter the size of the matrix (n):\n").strip())
        
        matrix = []
        print("Enter the matrix rows (numbers separated by spaces):")
        for _ in range(n):
            row = list(map(int, input().strip().split()))
            if len(row) != n:
                print(f"Expected {n} elements, got {len(row)}.")
                exit()
            matrix.append(row)
            
        rotate(matrix)
        
        print("Rotated Matrix:")
        for row in matrix:
            print(row)
            
    except ValueError:
        print("Invalid input detected. Please ensure all inputs are valid integers.")