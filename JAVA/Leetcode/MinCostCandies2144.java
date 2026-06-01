/*
 * Problem Statement:
 * A shop is selling candies at a discount. For every two candies sold, the shop gives a third candy for free.
 * The customer can choose any candy to take away for free as long as the cost of the chosen candy is less than or equal to the minimum cost of the two candies bought.
 * Given a 0-indexed integer array cost, where cost[i] denotes the cost of the ith candy, return the minimum cost of buying all the candies.
 */

/*
 * Approach: Counting Sort / Greedy
 * To minimize the total cost, we should always pair the most expensive candies together
 * so we can get the next most expensive candy for free.
 * Since the maximum cost is 100, we can use a frequency array (counting sort) to sort the candies in O(N) time.
 * We iterate from the highest price to the lowest. We buy the first two, skip the third (free), and repeat.
 */
import java.util.Scanner;

public class MinCostCandies2144 {
    public int minimumCost(int[] cost) {
        // Frequency array to count occurrences of each price, possible since cost[i] <= 100
        int[] count = new int[101];
        for (int c : cost) {
            count[c]++;
        }
        
        int res = 0;
        int bought = 0;
        
        // Iterate from the most expensive candy down to the cheapest
        for (int i = 100; i > 0; i--) {
            // Process all candies of the current price
            while (count[i] > 0) {
                // If we have paid for 2 candies, we get the 3rd one for free
                if (bought == 2) {
                    bought = 0; // Reset the counter since we used our free candy
                } else {
                    res += i;   // Add the cost of the current candy to the total
                    bought++;   // Increment the count of bought candies
                }
                count[i]--; // Decrease the frequency count for this price
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        // Setup Scanner for user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter candy costs separated by space:");
        
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Minimum cost: 0");
            return;
        }
        
        // Parse input string to integer array
        String[] parts = inputLine.split("\\s+");
        int[] cost = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            cost[i] = Integer.parseInt(parts[i]);
        }
        
        // Instantiate solver and display result
        MinCostCandies2144 solver = new MinCostCandies2144();
        System.out.println("Minimum cost: " + solver.minimumCost(cost));
        
        scanner.close();
    }
}