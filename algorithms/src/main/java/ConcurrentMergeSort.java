import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ConcurrentMergeSort extends RecursiveAction {

    private int[] arr;

    public ConcurrentMergeSort(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        if (arr.length == 1) {
            return;
        }
        var left = Arrays.copyOfRange(arr, 0, arr.length / 2);
        var right = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
        var leftTask = new ConcurrentMergeSort(left);
        var rightTask = new ConcurrentMergeSort(right);
        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        merge(left, right, this.arr);
    }

    public static void merge(int[] a1, int[] a2, int[] arr) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (i > a1.length - 1) {
                arr[k] = a2[j];
                j++;
            } else if (j > a2.length - 1) {
                arr[k] = a1[i];
                i++;
            } else if (a1[i] < a2[j]) {
                arr[k] = a1[i];
                i++;
            } else {
                arr[k] = a2[j];
                j++;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 11, 5, 1, 9, 3, 99, 10, 4, 8, 6, 2, 0};
        ForkJoinPool.commonPool().invoke(new ConcurrentMergeSort(arr));
        System.out.println(Arrays.toString(arr));
    }
}