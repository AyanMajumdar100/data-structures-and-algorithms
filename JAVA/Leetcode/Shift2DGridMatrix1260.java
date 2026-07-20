/*
 * Problem Statement:
 * Given a 2D grid of size m x n and an integer k, shift the grid k times.
 * Shifting moves each element one position to the right, wrapping to the next row at the column bound,
 * and wrapping the bottom-right element back to the top-left corner.
 */

/*
 * Approach: Linear Flat Index Flattening Mapping (O(M * N) Time & Space)
 * 1. Calculate the total number of elements `totalElements` = m * n.
 * 2. Optimize shifting factor using modulo scale arithmetic: `k = k % totalElements`.
 * 3. Initialize the nested matrix collection lists with placeholder zeros.
 * 4. Iterate from 0 up to `totalElements - 1` treating the grid as a flat 1D array. 
 *    For each position `i`, the original 2D coordinates are `oldRow = i / n` and `oldCol = i % n`.
 * 5. Compute the destination flat index `newIndex = (i + k) % totalElements` and unpack it to 2D using 
 *    `newRow = newIndex / n` and `newCol = newIndex % n`.
 * 6. Move values straight to their final destinations.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shift2DGridMatrix1260 {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int rowDimension = grid.length;
        int colDimension = grid[0].length;
        int totalElements = rowDimension * colDimension;
        k = k % totalElements; // Reduce redundant full-loop shifts
        
        List<List<Integer>> shiftedGridResult = new ArrayList<>();
        
        // Step 1: Pre-populate the dynamic multi-dimensional result list with matching zero slots
        for (int i = 0; i < rowDimension; i++) {
            List<Integer> singleRowList = new ArrayList<>(colDimension);
            for (int j = 0; j < colDimension; j++) {
                singleRowList.add(0);
            }
            shiftedGridResult.add(singleRowList);
        }
        
        // Step 2: Linearized index conversion to shift values directly to their final positions
        for (int i = 0; i < totalElements; i++) {
            int newFlattenedIndex = (i + k) % totalElements;
            int oldRow = i / colDimension, oldCol = i % colDimension;
            int newRow = newFlattenedIndex / colDimension, newCol = newFlattenedIndex % colDimension;
            
            shiftedGridResult.get(newRow).set(newCol, grid[oldRow][oldCol]);
        }
        
        return shiftedGridResult;
    }

    public static void main(String[] args) {
        // Step 3: Handle console array configuration parsing using clear variable parameters
        Scanner gridScanner = new Scanner(System.in);
        System.out.println("Enter number of rows (m) and columns (n):");
        int userRowsParam = gridScanner.nextInt();
        int userColsParam = gridScanner.nextInt();
        
        int[][] userMatrixGrid = new int[userRowsParam][userColsParam];
        System.out.println("Enter matrix elements line by line:");
        for (int i = 0; i < userRowsParam; i++) {
            for (int j = 0; j < userColsParam; j++) {
                userMatrixGrid[i][j] = gridScanner.nextInt();
            }
        }
        
        System.out.println("Enter shift times (k):");
        int userShiftFactorK = gridScanner.nextInt();
        
        // Step 4: Run matrix index relocation logic class
        Shift2DGridMatrix1260 solverInstance = new Shift2DGridMatrix1260();
        List<List<Integer>> finalGridState = solverInstance.shiftGrid(userMatrixGrid, userShiftFactorK);
        System.out.println("Shifted 2D grid result: " + finalGridState);
        
        gridScanner.close();
    }
}