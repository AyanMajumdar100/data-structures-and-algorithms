/*
 * Problem Statement:
 * You are given a square board of characters. You start at the bottom right square marked with 'S'.
 * You need to reach the top left square marked with 'E'. The rest of the squares are either numeric 
 * ('1' to '9') or obstacles ('X'). In one move you can go up, left, or up-left (diagonally).
 * * Return an array of two integers: 
 * 1. The maximum sum of numeric characters you can collect.
 * 2. The number of paths that achieve this maximum sum modulo 10^9 + 7.
 * If no path exists, return [0, 0].
 */

/*
 * Approach: Dynamic Programming (Bottom-Up from 'S' to 'E')
 * Since we can only move Up, Left, or Up-Left from any cell, if we solve the problem backwards 
 * from 'S' (bottom-right) to 'E' (top-left), a cell (r, c) can receive transitions from:
 * 1. Down neighbor: (r + 1, c)
 * 2. Right neighbor: (r, c + 1)
 * 3. Down-Right neighbor: (r + 1, c + 1)
 * * We maintain two DP matrices:
 * - dpSum[r][c]: Stores the maximum score collectable from 'S' to cell (r, c).
 * - dpCount[r][c]: Stores the number of valid paths that reach (r, c) with that maximum score.
 * * We iterate backwards from row n-1 to 0 and column n-1 to 0. For each non-obstacle cell, 
 * we find the maximum score among its incoming neighbors that have a valid path count > 0.
 * We update our path combinations accordingly and add the cell's own value.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MaxScorePaths1301 {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int[][] dpSum = new int[n][n];
        int[][] dpCount = new int[n][n];
        int mod = 1_000_000_007;

        // Base case: 1 way to start at the source 'S' with a score of 0
        dpCount[n - 1][n - 1] = 1;

        // Process bottom-up, right-to-left
        for (int r = n - 1; r >= 0; r--) {
            String rowStr = board.get(r);
            for (int c = n - 1; c >= 0; c--) {
                // Skip the starting cell since it is already initialized
                if (r == n - 1 && c == n - 1) {
                    continue;
                }
                
                char ch = rowStr.charAt(c);
                // Obstacles block all path progressions
                if (ch == 'X') {
                    continue;
                }

                int maxPrevSum = -1;
                int paths = 0;

                // 1. Check incoming path from Down neighbor
                if (r + 1 < n && dpCount[r + 1][c] > 0) {
                    if (dpSum[r + 1][c] > maxPrevSum) {
                        maxPrevSum = dpSum[r + 1][c];
                        paths = dpCount[r + 1][c];
                    } else if (dpSum[r + 1][c] == maxPrevSum) {
                        paths = (paths + dpCount[r + 1][c]) % mod;
                    }
                }

                // 2. Check incoming path from Right neighbor
                if (c + 1 < n && dpCount[r][c + 1] > 0) {
                    if (dpSum[r][c + 1] > maxPrevSum) {
                        maxPrevSum = dpSum[r][c + 1];
                        paths = dpCount[r][c + 1];
                    } else if (dpSum[r][c + 1] == maxPrevSum) {
                        paths = (paths + dpCount[r][c + 1]) % mod;
                    }
                }

                // 3. Check incoming path from Down-Right diagonal neighbor
                if (r + 1 < n && c + 1 < n && dpCount[r + 1][c + 1] > 0) {
                    if (dpSum[r + 1][c + 1] > maxPrevSum) {
                        maxPrevSum = dpSum[r + 1][c + 1];
                        paths = dpCount[r + 1][c + 1];
                    } else if (dpSum[r + 1][c + 1] == maxPrevSum) {
                        paths = (paths + dpCount[r + 1][c + 1]) % mod;
                    }
                }

                // If at least one neighbor was reachable, accumulate current cell stats
                if (maxPrevSum != -1) {
                    int currVal = (ch == 'E') ? 0 : (ch - '0');
                    dpSum[r][c] = maxPrevSum + currVal;
                    dpCount[r][c] = paths;
                }
            }
        }

        return new int[]{dpSum[0][0], dpCount[0][0]};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter dimensions of the square board (N):");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        List<String> board = new ArrayList<>();
        System.out.println("Enter board rows line by line (e.g., E23):");
        for (int i = 0; i < n; i++) {
            board.add(scanner.nextLine().trim());
        }
        
        MaxScorePaths1301 solver = new MaxScorePaths1301();
        int[] result = solver.pathsWithMaxScore(board);
        System.out.println("Result: " + Arrays.toString(result));
        
        scanner.close();
    }
}
