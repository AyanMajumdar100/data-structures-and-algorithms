/*
 * Problem Statement: LeetCode 3568 - Minimum Moves to Clean the Classroom
 * You are given an m x n grid classroom where a student needs to collect all litter ('L').
 * 'S' = Start, 'L' = Litter, 'R' = Energy Reset, 'X' = Obstacle, '.' = Empty.
 * The student starts with `energy` units. Each move costs 1 energy.
 * If energy reaches 0, the student cannot move unless they are on 'R' (which resets energy to max capacity).
 * Return the minimum number of moves to collect all litter items, or -1 if impossible.
 */

/*
 * Approach: Multi-State Breadth-First Search (BFS) with Bitmask & Energy Pruning
 * 1. Bitmask State Representation:
 *    - Assign an index 0 to (L - 1) for each litter cell 'L' (L <= 10).
 *    - Represent collected litter using a bitmask of length L (`targetMask = (1 << L) - 1`).
 * 2. BFS State & Dominance Optimization:
 *    - State is defined as `(row, col, litterMask)`.
 *    - Maintain `maxEnergy[r][c][mask]`, storing the maximum remaining energy seen so far at that state.
 *    - A new state is visited only if the remaining energy `ne > maxEnergy[nr][nc][nmask]`.
 * 3. Shortest Path Property:
 *    - Standard level-by-level BFS guarantees the first time `nmask == targetMask` is reached,
 *      the number of moves is minimal.
 */

import java.util.Arrays;
import java.util.Scanner;

public class MinimumMovesToCleanClassroom3568 {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1;
        int startC = -1;
        int numLitters = 0;

        int[][] litterId = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterId[i], -1);
        }

        // Parse starting position and assign indices to litter cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterId[i][j] = numLitters++;
                }
            }
        }

        int targetMask = (1 << numLitters) - 1;
        if (targetMask == 0) {
            return 0;
        }

        // maxEnergy[r][c][mask] tracks maximum remaining energy for state (r, c, mask)
        int[][][] maxEnergy = new int[m][n][1 << numLitters];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergy[i][j], -1);
            }
        }

        // Bit-packed circular array queue for fast BFS traversal
        int queueCapacity = 1 << 22;
        int maskQ = queueCapacity - 1;
        int[] q = new int[queueCapacity];
        int head = 0;
        int tail = 0;

        // Encode state: r (5 bits) | c (5 bits) | mask (10 bits) | e (6 bits)
        int startVal = startR | (startC << 5) | (0 << 10) | (energy << 20);
        q[tail] = startVal;
        tail = (tail + 1) & maskQ;

        maxEnergy[startR][startC][0] = energy;
        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (((tail - head) & maskQ) > 0) {
            int curSize = (tail - head) & maskQ;

            for (int i = 0; i < curSize; i++) {
                int val = q[head];
                head = (head + 1) & maskQ;

                int r = val & 31;
                int c = (val >> 5) & 31;
                int mask = (val >> 10) & 1023;
                int e = (val >> 20) & 63;

                // Skip suboptimal branches
                if (e < maxEnergy[r][c][mask] || e == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                        char ch = classroom[nr].charAt(nc);
                        if (ch == 'X') {
                            continue;
                        }

                        int ne = e - 1;
                        int nmask = mask;

                        if (ch == 'L') {
                            nmask |= (1 << litterId[nr][nc]);
                        } else if (ch == 'R') {
                            ne = energy; // Reset full capacity
                        }

                        if (nmask == targetMask) {
                            return moves + 1;
                        }

                        if (ne > maxEnergy[nr][nc][nmask]) {
                            maxEnergy[nr][nc][nmask] = ne;

                            int newVal = nr | (nc << 5) | (nmask << 10) | (ne << 20);
                            q[tail] = newVal;
                            tail = (tail + 1) & maskQ;
                        }
                    }
                }
            }
            moves++;
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of rows (m):");
        int mParam = scanner.nextInt();
        scanner.nextLine();

        String[] classroomParam = new String[mParam];
        System.out.println("Enter grid rows:");
        for (int i = 0; i < mParam; i++) {
            classroomParam[i] = scanner.nextLine().trim();
        }

        System.out.println("Enter energy capacity:");
        int energyParam = scanner.nextInt();

        MinimumMovesToCleanClassroom3568 solver = new MinimumMovesToCleanClassroom3568();
        int result = solver.minMoves(classroomParam, energyParam);

        System.out.println("Minimum moves to clean the classroom: " + result);
        scanner.close();
    }
}
