import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Quick Sort - Enter elements: ");
        String[] input = sc.nextLine().split(" ");
        int[] arr = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();
        arr = quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static int[] quickSort(int[] arr) {
        if (arr.length <= 1) return arr;
        int pivot = arr[arr.length / 2];
        List<Integer> left = new ArrayList<>();
        List<Integer> middle = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int x : arr) {
            if (x < pivot) left.add(x);
            else if (x == pivot) middle.add(x);
            else right.add(x);
        }
        int[] sortedLeft = quickSort(left.stream().mapToInt(Integer::intValue).toArray());
        int[] sortedRight = quickSort(right.stream().mapToInt(Integer::intValue).toArray());
        int[] result = new int[arr.length];
        int idx = 0;
        for (int x : sortedLeft) result[idx++] = x;
        for (int x : middle) result[idx++] = x;
        for (int x : sortedRight) result[idx++] = x;
        return result;
    }
}
