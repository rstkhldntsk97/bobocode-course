import java.util.Arrays;

public class Main {

    /**
     * it requires more or less time?
     * <p>
     * Merge sorting needs less time than insertion sort.
     * Merge sort --> O(N*log N) vs O(N2) <-- Insertion sort (Avg. time)
     * Use merge sort when you have big dataset
     * Use insertion sort when you have small dataset,
     * also can be useful when input is almost sorted,
     * only few elements are misplaced
     * <p>
     * it requires more or less memory?
     * <p>
     * Merge sort doesn't provide in place sorting
     * Use insertion sort if memory is a problem
     */

    public static void main(String[] args) {
        System.out.println("Merge sort");
        int[] arr = new int[]{7, 11, 5, 1, 9, 3, 99, 10, 4, 8, 6, 2, 0};
        System.out.println(Arrays.toString(MergeSort.sort(arr)));

        System.out.println("Insertion sort");
        int[] arr2 = new int[]{7, 11, 5, 1, 9, 3, 99, 10, 4, 8, 6, 2, 0};
        System.out.println(Arrays.toString(InsertionSort.sort(arr2)));

    }

}