def minimum_deletions(s):
    b_count = 0        # Counts the number of 'b's seen so far
    min_deletions = 0  # Tracks the minimum deletions needed

    # Iterate through each character in the string
    for c in s:
        if c == 'b':
            # If we see a 'b', increment the count
            b_count += 1
        else:
            # If we see an 'a' after some 'b's,
            # we might need to delete it to maintain order
            if min_deletions < b_count:
                min_deletions += 1  # Delete this 'a'
            # Otherwise, previous deletions already handled it

    # Return the total minimum deletions needed
    return min_deletions


if __name__ == "__main__":
    s = input("Enter a string of 'a' and 'b': ")  # Take input from user
    result = minimum_deletions(s)
    print("Minimum deletions needed:", result)
