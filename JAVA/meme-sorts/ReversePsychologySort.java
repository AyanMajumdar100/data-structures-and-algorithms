/*
(Gaslighting the Array)
Idea: Tell the array you don’t want it sorted. Do nothing.
*/

// Time Complexity: O(∞)
import java.util.*;

public class ReversePsychologySort {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1};

        reversePsychologySort(arr);

        System.out.println("Result:");
        System.out.println(Arrays.toString(arr));
    }

    public static void reversePsychologySort(int[] arr) {
        System.out.println("I absolutely do NOT want this array sorted.");
        System.out.println("Please stay unsorted.");
        // Array refuses to cooperate
    }
}
