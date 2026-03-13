/*
3296. Minimum Number of Seconds to Make Mountain Height Zero

Problem Statement:
You are given an integer mountainHeight representing the height of a mountain.

You are also given an array workerTimes where workerTimes[i] represents the base
time taken by worker i.

Workers work simultaneously to reduce the mountain height.

For worker i:
To reduce the height by x units, the time required is:
workerTimes[i] * 1 + workerTimes[i] * 2 + ... + workerTimes[i] * x

This forms an arithmetic series:
time = workerTimes[i] * (x * (x + 1) / 2)

Goal:
Find the minimum number of seconds required so that the total height reduced
by all workers combined is at least mountainHeight.

Examples:

Example 1
Input:
mountainHeight = 4
workerTimes = [2,1,1]

Output:
3

Explanation:
Worker 0 reduces height by 1 -> 2 seconds
Worker 1 reduces height by 2 -> 3 seconds
Worker 2 reduces height by 1 -> 1 second

Total height reduced = 4
Maximum time taken = 3 seconds

Example 2
Input:
mountainHeight = 10
workerTimes = [3,2,2,4]

Output:
12

Example 3
Input:
mountainHeight = 5
workerTimes = [1]

Output:
15

Constraints:
1 <= mountainHeight <= 10^5
1 <= workerTimes.length <= 10^4
1 <= workerTimes[i] <= 10^6

Approach:
1. Use Binary Search on time.
2. For a given time, calculate how much height each worker can reduce.
3. Sum the reduced height from all workers.
4. If total >= mountainHeight → time is sufficient.
5. Otherwise increase time.

Time Complexity:
O(n log T)

Space Complexity:
O(1)
*/

import java.util.Scanner;

public class MinimumNumberOfSeconds {

    public static long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {

        long low = 1;

        /*
        Worst case:
        Slowest worker = 10^6
        Height = 10^5
        Sum formula = x(x+1)/2
        */
        long high = 1000000L * mountainHeight * (mountainHeight + 1L) / 2L;

        long ans = high;

        while (low <= high) {

            long mid = low + (high - low) / 2;

            if (canReduceMountain(mid, mountainHeight, workerTimes)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private static boolean canReduceMountain(long timeLimit, int targetHeight, int[] workerTimes) {

        long reducedHeight = 0;

        for (int w : workerTimes) {

            /*
            Find maximum x such that:
            w * x * (x + 1) / 2 <= timeLimit

            Rearranged:
            x(x+1) <= (timeLimit / w) * 2
            */

            long maxVal = (timeLimit / w) * 2L;

            long x = (long) Math.sqrt(maxVal);

            while (x * (x + 1) > maxVal) {
                x--;
            }

            while ((x + 1) * (x + 2) <= maxVal) {
                x++;
            }

            reducedHeight += x;

            if (reducedHeight >= targetHeight) {
                return true;
            }
        }

        return reducedHeight >= targetHeight;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter mountain height: ");
        int mountainHeight = sc.nextInt();

        System.out.print("Enter number of workers: ");
        int n = sc.nextInt();

        int[] workerTimes = new int[n];

        System.out.println("Enter worker times:");

        for (int i = 0; i < n; i++) {
            workerTimes[i] = sc.nextInt();
        }

        long result = minNumberOfSeconds(mountainHeight, workerTimes);

        System.out.println("Minimum seconds required: " + result);

        sc.close();
    }
}