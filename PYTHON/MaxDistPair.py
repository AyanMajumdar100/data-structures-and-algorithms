'''
Problem Statement:
You are given two non-increasing 0-indexed integer arrays nums1 and nums2.
A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, 
is valid if both i <= j and nums1[i] <= nums2[j]. The distance of the pair is j - i.
Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.

An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.

Constraints:
1 <= nums1.length, nums2.length <= 10^5
1 <= nums1[i], nums2[j] <= 10^5
Both nums1 and nums2 are non-increasing.
'''

def max_distance(nums1: list[int], nums2: list[int]) -> int:
    i = 0  # Pointer for nums1
    j = 0  # Pointer for nums2
    max_dist = 0
    
    n1 = len(nums1)
    n2 = len(nums2)
    
    # Use two pointers to scan from left to right in O(N + M) time
    while i < n1 and j < n2:
        if nums1[i] <= nums2[j]:
            # Pair is valid. Record distance and advance 'j' to look for an even larger distance.
            dist = j - i
            if dist > max_dist:
                max_dist = dist
            j += 1
        else:
            # Pair is invalid. nums1[i] is too large. 
            # Advance 'i' to find a smaller element since the array is sorted descending.
            i += 1
            
    return max_dist

if __name__ == "__main__":
    try:
        # Collect and parse input for the first array
        input1 = input("Enter elements for nums1 (non-increasing) separated by space:\n").strip()
        nums1 = [int(x) for x in input1.split()]
        
        # Collect and parse input for the second array
        input2 = input("Enter elements for nums2 (non-increasing) separated by space:\n").strip()
        nums2 = [int(x) for x in input2.split()]
        
        # Guard clause against empty arrays
        if not nums1 or not nums2:
            print("Arrays cannot be empty.")
        else:
            result = max_distance(nums1, nums2)
            print(f"Maximum Distance: {result}")
            
    except ValueError:
        print("Invalid input format. Please ensure you are entering valid integers.")