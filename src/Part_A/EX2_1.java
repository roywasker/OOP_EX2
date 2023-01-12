package Part_A;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class EX2_1 {

    /**
     * this method creates n text files in a specific path.
     * each file contains a random number of lines of a certain string.
     * In the end the function returns a String Array with the names of the files in it.
     * @param n - number of text files
     * @param seed - initial value of the internal state of the pseudorandom number generator
     * @param bound - the range of number of lines in each file, 0(inclusive) to bound(exclusive)
     * @return the String array of the files names
     */
    public static String[] createTextFiles(int n, int seed, int bound) {

        String[] FileName = new String[n];
        Random rand = new Random(seed);
        for (int i = 1; i <= n; i++) {
            try {
                String CurrentFile = "file_" + i + ".txt";
                FileWriter Writer = new FileWriter(CurrentFile);
                int NumOfLine = rand.nextInt(bound);
                for (int j = 0; j < NumOfLine - 1; j++) {
                    Writer.write("Hello World!\n");
                }
                Writer.write("Hello World!");
                FileName[i - 1] = CurrentFile;
                Writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        return FileName;
    }

    /**
     * this method gets a String array of files names and
     * summarize the total number of lines in all the files combine.
     * @param fileNames - String array that contains names of files
     * @return number of total lines
     */
    public static int getNumOfLines(String[] fileNames) {
        int NumOfLines = 0;
        try {
            for (String fileName : fileNames) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    NumOfLines++;
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return NumOfLines;
    }

    /**
     * this method gets a String array of files names and
     * summarize the total number of lines in all the files combine, using threads.
     * @param fileNames - String array that contains names of files
     * @return number of total lines
     */
    public int getNumOfLinesThreads(String[] fileNames) {
        int NumOfLines = 0;
        List<GetLineThreads> threads = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            threads.add(new GetLineThreads(fileNames[i]));
            threads.get(i).start();
        }
        for (GetLineThreads thread : threads) {
            try {
                thread.join();
                NumOfLines += thread.getNumOfLine();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return NumOfLines;
    }

    /**
     * this method gets a String array of files names and
     * summarize the total number of lines in all the files combine, using ThreadPool.
     * @param fileNames - String array that contains names of files
     * @return number of total lines
     */
    public int getNumOfLinesThreadPool(String[] fileNames) {
        int NumOfLines = 0;
        ExecutorService executor = Executors.newFixedThreadPool(fileNames.length);
        List<Future> futures = new ArrayList<>();
        for (String fileName : fileNames) {
            Future<Integer> future = executor.submit(new GetLineThreadpool(fileName));
            futures.add(future);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            for (Future f : futures) {
                NumOfLines += (int) f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return NumOfLines;
    }

    /**
     * Delete all the text files that created.
     * @param fileNames Array of file name.
     */
    public static void delete(String[] fileNames) {
        for (String fileName : fileNames) {
            File myObj = new File(fileName);
            myObj.delete();
        }
    }
}