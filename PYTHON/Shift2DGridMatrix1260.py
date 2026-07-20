'''
Problem Statement:
Given a 2D grid of size m x n and an integer k, shift the grid k times.
Shifting moves each element one position to the right, wrapping to the next row at the column bound,
and wrapping the bottom-right element back to the top-left corner.
'''

'''
Approach: Linear Flat Index Flattening Mapping (O(M * N) Time & Space)
1. Calculate the total number of elements `total_elements` = m * n.
2. Optimize shifting factor using modulo scale arithmetic: `k = k % total_elements`.
3. Iterate from 0 up to `total_elements - 1` treating the grid as a flat 1D array.
4. Calculate new coordinates via `new_row = new_idx // n` and `new_col = new_idx % n`.
5. Re-assemble shifted array groups back into standard nested list matrices.
'''

class Shift2DGridMatrix1260:
    def shiftGrid(self, grid: list[list[int]], k: int) -> list[list[int]]:
        row_dimension = len(grid)
        col_dimension = len(grid[0])
        total_elements = row_dimension * col_dimension
        k = k % total_elements # Reduce redundant full-loop shifts
        
        # Step 1: Initialize an empty result matrix framework placeholder setup
        shifted_grid_result = [[0] * col_dimension for _ in range(row_dimension)]
        
        # Step 2: Linearized index conversion to shift values directly to their final positions
        for i in range(total_elements):
            new_flattened_index = (i + k) % total_elements
            old_row, old_col = i // col_dimension, i % col_dimension
            new_row, new_col = new_flattened_index // col_dimension, new_flattened_index % col_dimension
            
            shifted_grid_result[new_row][new_col] = grid[old_row][old_col]
            
        return shifted_grid_result

if __name__ == '__main__':
    try:
        # Step 3: Parse input details via clear variable tags
        rows_count = int(input("Enter number of rows (m): "))
        cols_count = int(input("Enter number of columns (n): "))
        
        print("Enter matrix rows space-separated line by line:")
        user_matrix_grid = [list(map(int, input().split())) for _ in range(rows_count)]
        
        user_shift_factor_k = int(input("Enter shift times (k): "))
        
        # Step 4: Run matrix index relocation logic class
        python_solver = Shift2DGridMatrix1260()
        final_grid_state = python_solver.shiftGrid(user_matrix_grid, user_shift_factor_k)
        print("Shifted 2D grid result:", final_grid_state)
    except (ValueError, IndexError):
        print("Invalid array formatting input given.")