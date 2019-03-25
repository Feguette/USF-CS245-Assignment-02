/**
 * Sorts an array in-place using the hybrid sort algorithm.
 * Follows the format for other sorts instead of using a static method in order to test speed
 *
 * @author Jason Liang
 * @version (14 March 2019)
 */


public class HybridSort implements SortingAlgorithm {
    private final static int MAX_LIMIT = 20; //max limit for quadraticSort to run

    public void sort(double[] array) {
        hybridSort(array, 0, array.length-1);
    }
    private static void hybridSort(double[] array, int lower, int upper) {
        if (upper - lower > MAX_LIMIT)
            quickSort(array, lower, upper);
        else
            quadraticSort(array, lower, upper);
    }

    private static void quickSort(double[] array, int lower, int upper) {
        int pivot = partition(array, lower, upper);
        hybridSort(array, lower, pivot - 1);
        hybridSort(array,pivot + 1, upper);
    }

    private static int partition (double[] array, int lower, int upper) {
        if (lower >= upper)
            return lower;
        int mid = (lower + upper)/2;
//        if (array[mid] < Math.max(array[lower], array[upper]) && array[mid] > Math.min(array[lower], array[upper]))
//            swap(array, mid, lower);
//        else if (array[upper] < Math.max(array[lower], array[mid]) && array[upper] > Math.min(array[lower], array[mid]))
//            swap(array, lower, upper);
        swap(array, lower, (int)(Math.random()*(upper-lower+1))+lower);
        int left = lower + 1;
        int right = upper;
        while (left < right) {
            while (left <= upper && array[left] <= array[lower])
                left++;
            while (right >= left && array[right] > array[lower])
                right--;
            if (left < right)
                swap(array, left, right);
        }
        swap(array, lower, right);

//        if (array[lower] > array[upper]) {
//            swap(array, lower, upper);
//        }
        return right;

    }

    private static void swap(double[] array, int index1, int index2) {
        double temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static void quadraticSort(double[] array, int lower, int upper) {
        double temp;
        int j;
        for (int i = lower + 1; i <= upper; i++) {
            temp = array[i];
            j = i - 1;
            while (j >= lower && temp < array[j])
                array[j + 1] = array[j--];
            array[j + 1] = temp;
        }
    }

    public static boolean inOrder(double[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] > array[i])
                return false;
        }
        return true;
    }





}
