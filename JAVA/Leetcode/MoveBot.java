/*
Problem: 657. Robot Return to Origin

A robot starts at (0, 0).

Given a string of moves:
'U' → move up
'D' → move down
'L' → move left
'R' → move right

Each move changes position by 1 unit.

Goal:
Return true if robot comes back to (0,0), else false.

-----------------------------------------------------

🧠 LOGIC EXPLANATION:

Think of this as tracking coordinates on a graph:

- x-axis → left/right movement
- y-axis → up/down movement

Rules:
- 'U' → y + 1
- 'D' → y - 1
- 'R' → x + 1
- 'L' → x - 1

We simulate the movement step by step.

-----------------------------------------------------

🎯 KEY IDEA:

If after all moves:
    x == 0 AND y == 0
→ robot returned to origin

Else:
→ robot ended somewhere else

-----------------------------------------------------

TIME COMPLEXITY: O(n)
SPACE COMPLEXITY: O(1)
*/

import java.util.*;

class MoveBot {  // short unique class name

    public boolean judgeCircle(String moves) {

        int x = 0; // horizontal position
        int y = 0; // vertical position

        // Process each move
        for (char move : moves.toCharArray()) {

            if (move == 'U') y++;       // move up
            else if (move == 'D') y--;  // move down
            else if (move == 'R') x++;  // move right
            else if (move == 'L') x--;  // move left
        }

        // Check if back at origin
        return x == 0 && y == 0;
    }

    // -------- USER INPUT DRIVER --------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input moves string
        String moves = sc.nextLine();

        MoveBot obj = new MoveBot();
        boolean result = obj.judgeCircle(moves);

        System.out.println(result);

        sc.close();
    }
}