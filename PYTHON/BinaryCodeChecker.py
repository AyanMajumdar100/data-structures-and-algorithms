"""
1461. Check If a String Contains All Binary Codes of Size K

Return True if every binary code of length k appears
as a substring of s.
"""

class BinaryCodeChecker:

    def hasAllCodes(self, s: str, k: int) -> bool:

        n = len(s)
        total_codes = 1 << k

        # If string too short → impossible
        if n < total_codes + k - 1:
            return False

        seen = [False] * total_codes
        count = 0

        hash_val = 0
        mask = total_codes - 1

        for i in range(n):

            # Rolling hash of last k bits
            hash_val = ((hash_val << 1) & mask) | int(s[i])

            # Start checking once window size reaches k
            if i >= k - 1 and not seen[hash_val]:
                seen[hash_val] = True
                count += 1

                if count == total_codes:
                    return True

        return False


# ---------- USER INPUT DRIVER ----------
if __name__ == "__main__":
    s = input("Enter binary string: ")
    k = int(input("Enter k: "))

    solver = BinaryCodeChecker()
    result = solver.hasAllCodes(s, k)

    print("Contains all binary codes of size k:", result)