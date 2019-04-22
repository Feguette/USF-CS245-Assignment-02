import java.io.*;
import java.util.Scanner;

public class ExternalSort implements SortingAlgorithm{
    private static int size = 10, lower = 0, upper = 1;
    public ExternalSort() {

    }

    /**
     * Skeleton code
     * @param arr Skeleton
     */
    public void sort(double[] arr) {
        File results = toDirectory(".", "Results");
        arrayToFile(arr, "input", results.toString());
        externalSort("input", "output", arr.length, arr.length/16);
        try {
            copyOver(fileToArray(new File(results + File.separator + "output")), arr);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void copyOver(double[] input, double[] output) {
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }
    }

    public void externalSort(String inputFile, String outputFile, int n, int k) {
        File results = toDirectory(".", "Results");

        File tempFiles = toDirectory(".", "Results", "TempFiles");
        File[] tempLists = tempFiles.listFiles();
        for (File temp: tempLists)
            forceDelete(temp);

        File input = new File(results + File.separator + inputFile);

        if (!input.exists()) {
            double[] list = generate(n, lower, upper); //Example input
            input = arrayToFile(list, inputFile, results.toString());
        }
        File source = toDirectory(".", "Results", "TempFiles", 0 + "");
        createSource(input, source, k);
        sortSource(source);
        //System.out.println(arrayToString(fileToArray(input)));
        mergeSource(tempFiles, source, outputFile, 0);
    }

    /**
     * Sorts all file data and rewrites it onto the same file
     * @param file File to be sorted
     */
    private void sortSource(File file) {
        File[] sources = file.listFiles();
        for (int i = 0; i < sources.length; i++) {
            File temp = sources[i];
            double[] part = fileToArray(temp);
            Sorting.quickSort(part);
            arrayToFile(part, sources[i].getName(), file.toString());
        }
    }

    /**
     * Recursively creates merged files for every higher level until only 1 file exists, which is then written
     * as file output
     * @param dir The directory containing all tempFiles and their directories
     * @param source The directory containing the tempFile at level start
     * @param outputFile The string of the file to be written
     * @param start The current level to be merged.
     */
    private void mergeSource(File dir, File source, String outputFile, int start) {
        File[] sources = source.listFiles();
        //System.out.println(start);
        if (sources.length > 1) {
            int i = 0;
            File mergedSource = new File(dir + File.separator + (start+1));
            mergedSource.mkdirs();
            for (i = 0; i < sources.length/2; i++) {
                try {
                    mergeFiles(mergedSource, i, sources[2 * i], sources[2 * i + 1]);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (sources.length%2 == 1) {
                File temp = sources[sources.length-1];
                File leftover = new File(mergedSource + File.separator + "temp" + (i+1));

                copyFile(temp, leftover);
            }
            mergeSource(dir, mergedSource, outputFile, start + 1);
        }
        else {
            if (sources.length != 0) {
                File out = new File(toDirectory(".", "Results") + File.separator + outputFile);
                File temp = sources[0];
                copyFile(temp, out);
            }

        }
    }

    /**
     * Copies file in into file out
     * @param in file to be copied
     * @param out file to be written
     */
    private void copyFile(File in, File out) {
        try {
            Scanner re = new Scanner(in);
            BufferedWriter wr = new BufferedWriter(new FileWriter(out));
            while(re.hasNextDouble()) {
                wr.write(re.nextDouble() + "");
                if (re.hasNextDouble())
                    wr.write("\n");
            }
            wr.close();
            re.close();
        }
        catch (Exception e) {}
    }

    /**
     * Helper method of mergeSource, which merges two files in order
     * @param source The directory to contain to merged file
     * @param count The current temp file number for writing the file into the next dir
     * @param child1 File 1 to merge with File 2
     * @param child2 File 2 to merge with File 1
     * @throws IOException
     */
    private void mergeFiles(File source, int count, File child1, File child2) throws IOException{
        File parent = new File(source + File.separator + "temp" + count);
        BufferedWriter wr = new BufferedWriter(new FileWriter(parent));
        Scanner re1 = new Scanner(child1);
        Scanner re2 = new Scanner(child2);
        double aspect1 = 0, aspect2 = 0;
        boolean running1 = re1.hasNextDouble(), running2 = re2.hasNextDouble();
        boolean first = false;
        while (running1 || running2) {
            if (!first) {
                first = true;
                if (running1)
                    aspect1 = re1.nextDouble();
                if (running2)
                    aspect2 = re2.nextDouble();
            }

            if (!running2 || (running1 && aspect1 <= aspect2)) {
                if (re1.hasNextDouble()) {
                    wr.write(aspect1 + "");
                    aspect1 = re1.nextDouble();
                }
                else {
                    wr.write(aspect1 + "");
                    running1 = false;
                }
            }
            else {
                if (re2.hasNextDouble()) {
                    wr.write(aspect2 + "");
                    aspect2 = re2.nextDouble();
                }
                else {
                    wr.write(aspect2 + "");
                    running2 = false;
                }
            }
            if (running1 || running2)
                wr.write("\n");
        }
        wr.close();
        re1.close();
        re2.close();
    }

    /**
     * Splits input file into Math.ceil(n, k) smaller files, where n is the total size of the file
     * @param input input file to be split
     * @param source Directory to store Math.ceil(n, k) smaller temp files.
     * @param k Maximum size of the file
     */
    private void createSource(File input, File source, int k) {
        File tempFile;
        int counter = 0;

        try {
            Scanner re = new Scanner(input);
            BufferedWriter wr;
            while (re.hasNextLine()) {
                tempFile = new File(source + File.separator
                        + "temp" + counter);
                wr = new BufferedWriter(new FileWriter(tempFile));
                for (int i = 0; i < k && re.hasNextLine(); i++) {
                    wr.write(re.nextLine());
                    if (i < k - 1 && re.hasNextLine())
                        wr.write("\n");
                }
                wr.close();
                counter++;
            }
        }
        catch (Exception e) {

        }
    }

    /**
     * Creates a new directory or uses existing directory of path[0]/path[1]/.../path[path.length-1]
     * @param paths 
     * @return The new directory of said path
     */
    public static File toDirectory(String... paths) {
        String augment = "";
        for (int i = 0; i < paths.length; i++)
            augment += paths[i] + File.separator;
        File dir = new File(augment);
        dir.mkdirs();
        return dir;
    }

    public static File arrayToFile(double[] arr, String textFile, String path) {
        File temp;
        temp = new File(path);
        temp.mkdirs();
        try {
            temp = new File(temp.toString() + File.separator + textFile);
            BufferedWriter wr = new BufferedWriter(new FileWriter(temp));
            wr.write(arrayToString(arr));
            wr.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return temp;
    }

    public static double[] fileToArray(File file) {
        String[] list;
        try {
            BufferedReader re = new BufferedReader(new FileReader(file));
            String line = "", temp = "";
            while ((temp = re.readLine()) != null) {
                line += temp + " ";
            }
            list = line.split(" ");
            re.close();

        }
        catch (Exception e)  {
            return null;
        }
        double[] array = new double[list.length];
        for (int i = 0; i < array.length; i++)
            array[i] = Double.parseDouble(list[i]);
        return array;
    }

    public static String arrayToString(double[] arr) {
        String augment = "";
        for (int i = 0; i < arr.length - 1; i++) {
            augment += arr[i] + "\n";
        }
        augment += arr[arr.length-1] + "";
        return augment;
    }

    public static void displayArray(double[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    public static double[] generate(int size, double lower, double upper) {
        double[] temp = new double[size];
        for (int i = 0; i < size; i++) {
            temp[i] = (Math.random()*(upper-lower))+lower;
        }
        return temp;
    }

    public static boolean isMatching(double[] child1, double[] child2) {
        for (int i = 0; i < child1.length; i++) {
            if (child1[i] != child2[i])
                return false;
        }
        return true;
    }

    public static boolean forceDelete(File path) {
        if (path.exists() && path.isDirectory()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    forceDelete(files[i]);
                }
                else
                    files[i].delete();
            }
        }
        return (path.delete());
    }
}
