// In this sort Elements with “connections” (lower index / chosen elite) go first. Everyone else waits forever.

// Time Complexity: O(n log n) due to the underlying sort, but the favoritism is O(1)
import java.util.*;

public class NepotismSort {

    public static void main(String[] args) {
        int[] arr = {5, 1, 4, 2, 3};
        nepotismSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void nepotismSort(int[] arr) {
        // First element has connections, so it gets promoted
        int favored = arr[0];

        Arrays.sort(arr); // Pretend merit exists

        // Move favored element to front regardless
        int index = find(arr, favored);
        for (int i = index; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = favored;
    }

    private static int find(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) return i;
        }
        return -1;
    }
}
