/*
 * Problem Statement:
 * At the store, there are n ice cream bars. You are given an array costs of length n, 
 * where costs[i] is the price of the ith ice cream bar in coins. The boy initially has 
 * coins coins to spend, and he wants to buy as many ice cream bars as possible. 
 * Return the maximum number of ice cream bars the boy can buy with coins coins.
 * Note: You must solve the problem using counting sort.
 */

/*
 * Approach: Counting Sort + Greedy
 * To maximize the count of purchased ice creams, we must always greedily purchase the 
 * cheapest ice cream bars available. 
 * 1. Find the maximum value in the costs array to define our frequency table range bound.
 * 2. Create a frequency array `freq` where `freq[price]` stores the total number of ice creams 
 * available at that exact price point (Counting Sort).
 * 3. Iterate through prices from 1 up to the max cost. For each price, compute how many ice cream 
 * bars we can afford: `Math.min(freq[price], coins / price)`.
 * 4. Deduct the cost from our total `coins` pool and add the count to `iceCreamBars`. 
 * If at any point the current price exceeds remaining coins, we terminate early.
 */
import java.util.Scanner;

public class MaxIceCreamBars1833 {
    public int maxIceCream(int[] costs, int coins) {
        int max = 0;
        // Step 1: Find the maximum cost to optimally size the frequency array
        for (int cost : costs) {
            if (cost > max) {
                max = cost;
            }
        }
        
        // Step 2: Build the frequency index map
        int[] freq = new int[max + 1];
        for (int cost : costs) {
            freq[cost]++;
        }
        
        int iceCreamBars = 0;
        
        // Step 3: Iterate through prices from lowest to highest (Greedy approach)
        for (int price = 1; price <= max; price++) {
            if (freq[price] > 0) {
                // If we cannot even afford a single bar at this price tier, stop buying
                if (coins < price) {
                    break;
                }
                
                // Calculate how many bars at this price point we can buy
                int buy = Math.min(freq[price], coins / price);
                
                // Deduct coins used and increment our running count
                coins -= buy * price;
                iceCreamBars += buy;
            }
        }
        
        return iceCreamBars;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter initial coins available:");
        int coins = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter the ice cream costs separated by space:");
        String inputLine = scanner.nextLine().trim();
        if (inputLine.isEmpty()) {
            System.out.println("Maximum ice cream bars: 0");
            return;
        }
        
        String[] parts = inputLine.split("\\s+");
        int[] costs = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            costs[i] = Integer.parseInt(parts[i]);
        }
        
        MaxIceCreamBars1833 solver = new MaxIceCreamBars1833();
        int result = solver.maxIceCream(costs, coins);
        
        System.out.println("Maximum ice cream bars purchased: " + result);
        scanner.close();
    }
}
