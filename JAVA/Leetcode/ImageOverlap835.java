/*
 * Problem Statement: LeetCode 835 - Image Overlap
 * You are given two binary square matrices img1 and img2 of size n x n.
 * You can translate img1 by sliding all 1 bits in any direction.
 * Return the largest possible overlap (number of positions with 1 in both images after translation).
 */

/*
 * Approach: Coordinate Vector Mapping / Hash Map Frequency Counting (O(K1 * K2) Time, O(K1 * K2) Space)
 * where K1 and K2 are the number of 1s in img1 and img2 respectively.
 * 1. Collect the coordinates of all 1s in `img1` into list `p1` and in `img2` into list `p2`.
 * 2. For every pair of points `(r1, c1)` from `p1` and `(r2, c2)` from `p2`:
 *    - Calculate the translation vector required to align them: `rDiff = r2 - r1`, `cDiff = c2 - c1`.
 *    - Each matching vector represents a valid shift that aligns at least one pair of 1 bits.
 * 3. Use a frequency map (or hash map) to count occurrences of each translation vector.
 * 4. The maximum frequency recorded across all shift vectors corresponds to the largest possible overlap.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;

public class ImageOverlap835 {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> p1 = new ArrayList<>();
        List<int[]> p2 = new ArrayList<>();

        // Step 1: Collect coordinates of all 1s
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) {
                    p1.add(new int[]{i, j});
                }
                if (img2[i][j] == 1) {
                    p2.add(new int[]{i, j});
                }
            }
        }

        // Step 2: Compute translation vectors and track frequencies
        Map<String, Integer> countMap = new HashMap<>();
        int maxOverlap = 0;

        for (int[] a : p1) {
            for (int[] b : p2) {
                int rDiff = b[0] - a[0];
                int cDiff = b[1] - a[1];
                String key = rDiff + "," + cDiff;
                int count = countMap.getOrDefault(key, 0) + 1;
                countMap.put(key, count);
                maxOverlap = Math.max(maxOverlap, count);
            }
        }

        return maxOverlap;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter matrix dimension n:");
        int n = scanner.nextInt();

        int[][] img1 = new int[n][n];
        System.out.println("Enter rows for img1:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                img1[i][j] = scanner.nextInt();
            }
        }

        int[][] img2 = new int[n][n];
        System.out.println("Enter rows for img2:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                img2[i][j] = scanner.nextInt();
            }
        }

        ImageOverlap835 solver = new ImageOverlap835();
        int result = solver.largestOverlap(img1, img2);

        System.out.println("Largest possible image overlap: " + result);
        scanner.close();
    }
}
