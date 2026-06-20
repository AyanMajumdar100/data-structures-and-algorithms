'''
Problem Statement:
You want to build n new buildings in a city labeled from 1 to n.
- The height of each building must be a non-negative integer.
- The height of the first building must be 0.
- The height difference between any two adjacent buildings cannot exceed 1.
- You are given a 2D integer array restrictions where restrictions[i] = [idi, maxHeighti].
Return the maximum possible height of the tallest building.
'''

'''
Approach: Two-Pass Optimization (Similar to Candy / Trapping Rain Water)
1. Boundary Extensions: Add a pseudo-restriction at building 1 with height 0, and at building n 
   with a theoretical max height of n - 1 (since it grows at most 1 per step from building 1).
2. Sorting: Sort the restrictions based on building IDs so we can propagate limits linearly.
3. Forward Pass: Update each building's max height constraint by checking how high it can go 
   starting from its left neighbor: r[i][1] = min(r[i][1], r[i-1][1] + distance).
4. Backward Pass: Pass backwards to propagate constraints from right neighbors back to left: 
   r[i][1] = min(r[i][1], r[i+1][1] + distance).
5. Peak Maximization: Between any two restricted buildings, the height can grow to a peak 
   and drop down. The theoretical peak height between two points (id1, h1) and (id2, h2) is 
   given by the math formula: floor((h1 + h2 + (id2 - id1)) / 2). Track the maximum peak found.
'''
class MaxBuildingHeight1840:
    def maxBuilding(self, n: int, restrictions: list[list[int]]) -> int:
        # Append boundary conditions to complete limits definition
        # Building 1 must have height 0; Building n can at most scale up to n - 1
        restrictions.append([1, 0])
        restrictions.append([n, n - 1])
        
        # Sort values based on the building index positions
        restrictions.sort(key=lambda x: x[0])
        
        m = len(restrictions)
        
        # Pass 1: Left-to-Right push to cap limits based on leftward bottlenecks
        for i in range(1, m):
            dist = restrictions[i][0] - restrictions[i - 1][0]
            restrictions[i][1] = min(restrictions[i][1], restrictions[i - 1][1] + dist)
            
        # Pass 2: Right-to-Left pull to tighten restrictions based on rightward bottlenecks
        for i in range(m - 2, -1, -1):
            dist = restrictions[i + 1][0] - restrictions[i][0]
            restrictions[i][1] = min(restrictions[i][1], restrictions[i + 1][1] + dist)
            
        max_height = 0
        
        # Final Sweep: Deduce optimal peaks generated between adjacent restriction points
        for i in range(m - 1):
            id1, h1 = restrictions[i]
            id2, h2 = restrictions[i + 1]
            
            # Intersection peak math formula: peak = ((h1 + h2) + distance) // 2
            peak = (h1 + h2 + (id2 - id1)) // 2
            max_height = max(max_height, peak)
            
        return max_height

if __name__ == '__main__':
    try:
        n_val = int(input("Enter total number of buildings n: "))
        m_val = int(input("Enter number of restrictions: "))
        
        restrictions_arr = []
        print("Enter restrictions (id height) line by line:")
        for _ in range(m_val):
            row = list(map(int, input().split()))
            restrictions_arr.append(row)
            
        solver = MaxBuildingHeight1840()
        result = solver.maxBuilding(n_val, restrictions_arr)
        print("Maximum possible building height:", result)
    except ValueError:
        print("Invalid input format.")