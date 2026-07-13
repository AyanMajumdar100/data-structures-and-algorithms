'''
Problem Statement:
Given an array of integers arr, replace each element with its rank.
Rank rules: Starts from 1, larger elements get larger ranks, equal elements share the same rank.
'''

'''
Approach: Sorting + Hash Map Ranking Sequence
1. Extract unique elements using a set, sort them to determine global value positioning.
2. Build a hash map dictionary matching each sorted element to its 1-indexed rank position.
3. Transform the original values using a list comprehension referencing the rank dictionary mappings.
'''

class ArrayElementValueRankTransformer1331:
    def arrayRankTransform(self, arr: list[int]) -> list[int]:
        # Step 1: Deduplicate and sort the array values to organize their rank indices
        unique_sorted_elements = sorted(list(set(arr)))
        
        # Step 2: Map each unique item to its corresponding sequential 1-indexed position value
        element_to_rank_dict = {value: index + 1 for index, value in enumerate(unique_sorted_elements)}
        
        # Step 3: Map the values of the original array stream directly to their calculated ranks
        return [element_to_rank_dict[element] for element in arr]