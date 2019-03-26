/**
 * Sorts an array in-place using the hybrid sort algorithm.
 *
 * @author Jason Liang
 * @version (27 February 2019)
 */
public class QuickSort implements SortingAlgorithm {
    public void sort(double[] a) {
        quickSort(a, 0, a.length-1);
    }


    private void quickSort(double[] a, int lower, int upper) {
        if (lower < upper) {
            int pivot = partition(a, lower, upper);
            quickSort(a, lower, pivot - 1);
            quickSort(a,pivot + 1, upper);
        }

    }
    /*
     *
     */
    private int partition (double[] a, int lower, int upper) {
        if (lower >= upper)
            return lower;
        int mid = (lower + upper)/2;
        swap(a, lower, (int)(Math.random()*(upper-lower+1))+lower);
//        if (a[mid] < Math.max(a[lower], a[upper]) && a[mid] > Math.min(a[lower], a[upper]))
//            swap(a, mid, lower);
//        else if (a[upper] < Math.max(a[lower], a[mid]) && a[upper] > Math.min(a[lower], a[mid]))
//            swap(a, lower, upper);
        int left = lower + 1;
        int right = upper;
        while (left < right) {
            while (left <= upper && a[left] <= a[lower])
                left++;
            while (right >= left && a[right] > a[lower])
                right--;
            if (left < right)
                swap(a, left, right);
        }
        swap(a, lower, right);

        if (a[lower] > a[upper])
            swap(a, lower, upper);
        return right;

    }

    /*
     * Assumes in bounds, swap() swaps 2 values in an array
     */
    public void swap(double[] a, int index1, int index2) {
        double temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }



}
