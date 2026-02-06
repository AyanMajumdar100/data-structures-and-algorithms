// This sort randomly shuffles the array until it accidentally becomes sorted. 
// Time Complexity: O(∞)
import java.util.*;

public class BogoSort {

    public static void main(String[] args) {
        int[] arr = {3, 1, 2};
        bogoSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bogoSort(int[] arr) {
        Random rand = new Random();
        while (!isSorted(arr)) {
            shuffle(arr, rand);
        }
    }

    private static void shuffle(int[] arr, Random rand) {
        for (int i = 0; i < arr.length; i++) {
            int j = rand.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
