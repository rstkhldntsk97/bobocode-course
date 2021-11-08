public class InsertionSort {

    public static int[] sort(int [] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j != 0 && arr[j] < arr[j - 1]) {
                int tmp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = tmp;
                j--;
            }
        }
        return arr;
    }

}
