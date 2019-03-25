import java.io.File;

public class ExternalSortTest {
    public static void main(String[] args) {
        String inputString = "input";
        String outputString = "output";
        ExternalSort test = new ExternalSort();
        test.externalSort(inputString, outputString, 160,16);
        File dir = ExternalSort.toDirectory(".", "Results");
        double[] in = ExternalSort.fileToArray(new File(dir + File.separator + inputString));
        Sorting.mergeSort(in);
        System.out.println("Expected: ");
        ExternalSort.displayArray(in);

        System.out.println("Actual: ");
        double[] out = ExternalSort.fileToArray(new File(dir + File.separator + outputString));
        ExternalSort.displayArray(out);
        System.out.println("Matching Arrays: " + ExternalSort.isMatching(in, out));
    }
}
