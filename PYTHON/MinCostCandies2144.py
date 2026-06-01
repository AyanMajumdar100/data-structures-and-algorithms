'''
Problem Statement:
A shop is selling candies at a discount. For every two candies sold, the shop gives a third candy for free.
The customer can choose any candy to take away for free as long as the cost of the chosen candy is less than or equal to the minimum cost of the two candies bought.
Given a 0-indexed integer array cost, where cost[i] denotes the cost of the ith candy, return the minimum cost of buying all the candies.
'''

'''
Approach: Counting Sort / Greedy
To minimize the total cost, we should always pair the most expensive candies together
so we can get the next most expensive candy for free.
Since the maximum cost is 100, we can use a frequency array (counting sort) to sort the candies in O(N) time.
We iterate from the highest price to the lowest. We buy the first two, skip the third (free), and repeat.
'''
class MinCostCandies2144:
    def minimumCost(self, cost: list[int]) -> int:
        # Frequency array to count occurrences of each price, possible since cost[i] <= 100
        count = [0] * 101
        for c in cost:
            count[c] += 1
            
        res = 0
        bought = 0
        
        # Iterate from the most expensive candy down to the cheapest
        for i in range(100, 0, -1):
            # Process all candies of the current price
            while count[i] > 0:
                # If we have paid for 2 candies, we get the 3rd one for free
                if bought == 2:
                    bought = 0 # Reset the counter since we used our free candy
                else:
                    res += i   # Add the cost of the current candy to the total
                    bought += 1 # Increment the count of bought candies
                count[i] -= 1 # Decrease the frequency count for this price
                
        return res

if __name__ == '__main__':
    try:
        # Prompt user for candy costs
        user_input = input("Enter candy costs separated by space: ").strip()
        
        if not user_input:
            print("Minimum cost: 0")
        else:
            # Convert input string to a list of integers
            cost = list(map(int, user_input.split()))
            
            # Instantiate solver and display result
            solver = MinCostCandies2144()
            result = solver.minimumCost(cost)
            print("Minimum cost:", result)
            
    except ValueError:
        print("Invalid input. Please enter valid integers.")