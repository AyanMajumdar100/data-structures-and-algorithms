/*
Problem: 2751. Robot Collisions

--------------------------------------------------

🧠 CORE IDEA:

Robots move either:
→ Right (R)
→ Left (L)

Collisions ONLY happen when:
👉 A right-moving robot meets a left-moving robot

--------------------------------------------------

💡 KEY OBSERVATION:

To simulate correctly:
👉 Process robots in order of POSITION (not input order)

Why?
Because collisions happen based on spatial order.

--------------------------------------------------

⚙️ STRATEGY:

1. Sort robots by position
2. Use a STACK to track "active" robots

Stack will store:
👉 indices of robots moving RIGHT

--------------------------------------------------

🚨 COLLISION LOGIC:

When we encounter a LEFT-moving robot:

We check:
👉 Does it collide with any RIGHT-moving robot in stack?

While stack not empty and top is 'R':

Case 1:
health[top] < health[curr]
→ top dies
→ curr health--
→ continue checking

Case 2:
health[top] > health[curr]
→ curr dies
→ top health--
→ STOP

Case 3:
equal health
→ both die
→ STOP

--------------------------------------------------

If LEFT robot survives all collisions:
→ push it into stack

--------------------------------------------------

📦 FINAL STEP:

Return all robots with health > 0
(in ORIGINAL ORDER)

--------------------------------------------------

⏱ Complexity:

Time: O(n log n) (sorting)
Space: O(n)

--------------------------------------------------
*/

import java.util.*;

class RoboCol {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] positions = new int[n];
        int[] healths = new int[n];

        for (int i = 0; i < n; i++) positions[i] = sc.nextInt();
        for (int i = 0; i < n; i++) healths[i] = sc.nextInt();

        String directions = sc.next();

        List<Integer> result = survivedRobotsHealths(positions, healths, directions);

        for (int x : result) {
            System.out.print(x + " ");
        }

        sc.close();
    }

    public static List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {

        int n = positions.length;

        // Step 1: sort indices based on positions
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (a, b) -> Integer.compare(positions[a], positions[b]));

        // Stack to keep track of RIGHT-moving robots
        Stack<Integer> stack = new Stack<>();

        // Step 2: process robots in sorted order
        for (int currIdx : indices) {

            // If robot is moving RIGHT → push to stack
            if (directions.charAt(currIdx) == 'R') {
                stack.push(currIdx);
            }
            // If robot is moving LEFT → possible collisions
            else {

                boolean survived = true;

                // Check collisions with stack top
                while (!stack.isEmpty() && directions.charAt(stack.peek()) == 'R') {

                    int topIdx = stack.peek();

                    // Case 1: current robot stronger
                    if (healths[topIdx] < healths[currIdx]) {
                        stack.pop();
                        healths[currIdx]--;
                        healths[topIdx] = 0;
                    }
                    // Case 2: stack robot stronger
                    else if (healths[topIdx] > healths[currIdx]) {
                        healths[topIdx]--;
                        healths[currIdx] = 0;
                        survived = false;
                        break;
                    }
                    // Case 3: equal health
                    else {
                        stack.pop();
                        healths[topIdx] = 0;
                        healths[currIdx] = 0;
                        survived = false;
                        break;
                    }
                }

                // If current robot survived → push it
                if (survived) {
                    stack.push(currIdx);
                }
            }
        }

        // Step 3: collect survivors in original order
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (healths[i] > 0) {
                result.add(healths[i]);
            }
        }

        return result;
    }
}
