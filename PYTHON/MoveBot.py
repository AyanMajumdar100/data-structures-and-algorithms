"""
Problem: 657. Robot Return to Origin

Same logic as Java.

-----------------------------------------------------

🧠 HUMAN THINKING:

We just track position.

Start at (0,0)

Every move shifts position:
- U → go up (y + 1)
- D → go down (y - 1)
- R → go right (x + 1)
- L → go left (x - 1)

-----------------------------------------------------

🎯 FINAL CHECK:

If after all moves:
    x == 0 and y == 0
→ returned to origin

Else:
→ not at origin

-----------------------------------------------------
"""

class MoveBot:

    def judgeCircle(self, moves: str) -> bool:

        x, y = 0, 0

        for move in moves:

            if move == 'U':
                y += 1
            elif move == 'D':
                y -= 1
            elif move == 'R':
                x += 1
            elif move == 'L':
                x -= 1

        return x == 0 and y == 0


# -------- USER INPUT DRIVER --------

if __name__ == "__main__":

    moves = input()

    obj = MoveBot()
    print(obj.judgeCircle(moves))