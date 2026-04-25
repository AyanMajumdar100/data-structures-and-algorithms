'''
Problem Statement:
You are given an integer side, representing the edge length of a square with corners at 
(0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.
You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] 
represents the coordinate of a point lying on the boundary of the square.

You need to select k elements among points such that the minimum Manhattan distance between any 
two points is maximized.
Return the maximum possible minimum Manhattan distance between the selected k points.

Constraints:
1 <= side <= 10^9
4 <= points.length <= min(4 * side, 15 * 10^3)
points[i] lies on the boundary of the square.
All points[i] are unique.
4 <= k <= min(25, points.length)
'''

def max_distance(side: int, points: list[list[int]], k: int) -> int:
    # Helper to map 2D boundary coordinate to 1D clockwise perimeter distance
    def get_pos(x: int, y: int) -> int:
        if y == 0: return x                 # Bottom edge
        if x == side: return side + y       # Right edge
        if y == side: return 3 * side - x   # Top edge
        return 4 * side - y                 # Left edge

    n = len(points)
    
    # Map points and sort them clockwise
    pos = sorted([get_pos(x, y) for x, y in points])
    
    perimeter = 4 * side
    
    # Duplicate array to simulate circular wrap-around seamlessly
    arr = pos + [p + perimeter for p in pos]

    # Helper function to check if a specific distance 'd' is achievable
    def check(d: int) -> bool:
        nxt = [0] * (2 * n)
        j = 0
        
        # Precompute the index of the next valid point for every point using two pointers
        for i in range(2 * n):
            while j < 2 * n and arr[j] - arr[i] < d:
                j += 1
            nxt[i] = j

        # Try to greedily select 'k' points starting from every possible original point
        for i in range(n):
            curr = i
            count = 1
            
            while count < k and curr < 2 * n:
                curr = nxt[curr]
                count += 1
                
            # If we picked 'k' points, verify the distance between the last and first point >= d
            if count == k and curr < 2 * n and arr[curr] - arr[i] <= perimeter - d:
                return True
                
        return False

    # Binary search on the answer space
    low, high = 1, side
    ans = 0
    
    while low <= high:
        mid = low + (high - low) // 2
        
        if check(mid):
            ans = mid
            low = mid + 1 # Try pushing for a larger minimum distance
        else:
            high = mid - 1 # Distance too large, scale down

    return ans

if __name__ == "__main__":
    try:
        side = int(input("Enter square side length: ").strip())
        k = int(input("Enter k (number of points to select): ").strip())
        n = int(input("Enter total number of points: ").strip())
        
        points = []
        print("Enter the points (x y) one per line:")
        for _ in range(n):
            x, y = map(int, input().strip().split())
            points.append([x, y])
            
        result = max_distance(side, points, k)
        print(f"Maximum Possible Minimum Manhattan Distance: {result}")
        
    except ValueError:
        print("Invalid input format detected. Ensure you enter valid integers.")