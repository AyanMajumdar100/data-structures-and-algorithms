"""
3296. Minimum Number of Seconds to Make Mountain Height Zero

Problem:
Workers reduce a mountain height simultaneously.

For worker i:
Time to reduce height x = workerTimes[i] * (1 + 2 + ... + x)
                        = workerTimes[i] * x(x+1)/2

Goal:
Find the minimum time so that total reduced height >= mountainHeight.

Approach:
Binary search on time.
"""

import math


def canReduceMountain(timeLimit, targetHeight, workerTimes):

    reducedHeight = 0

    for w in workerTimes:

        # Solve x(x+1) <= (timeLimit/w)*2
        maxVal = (timeLimit // w) * 2

        x = int(math.sqrt(maxVal))

        while x * (x + 1) > maxVal:
            x -= 1

        while (x + 1) * (x + 2) <= maxVal:
            x += 1

        reducedHeight += x

        if reducedHeight >= targetHeight:
            return True

    return reducedHeight >= targetHeight


def minNumberOfSeconds(mountainHeight, workerTimes):

    low = 1
    high = 1000000 * mountainHeight * (mountainHeight + 1) // 2

    ans = high

    while low <= high:

        mid = (low + high) // 2

        if canReduceMountain(mid, mountainHeight, workerTimes):
            ans = mid
            high = mid - 1
        else:
            low = mid + 1

    return ans


# User Input
mountainHeight = int(input("Enter mountain height: "))
n = int(input("Enter number of workers: "))

workerTimes = list(map(int, input("Enter worker times: ").split()))

result = minNumberOfSeconds(mountainHeight, workerTimes)

print("Minimum seconds required:", result)