package Part_A;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class EX2_1 {

    /**
     * This method receiveb how much file to create and create them.
     * @param n number of file to create.
     * @param seed
     * @param bound
     * @return array of all file name.
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
     * This method count how much line all the file have without threads.
     * @param fileNames array of all file name.
     * @return number of all line in file together.
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
     * This method count how much line all the file have with threads.
     * @param fileNames array of all file name.
     * @return number of all line in file together.
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
     * This method count how much line all the file have with threadpool.
     * @param fileNames array of all file name.
     * @return number of all line in file together.
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

    public static void main(String[] args) {
        String[] arr = new String[10000];
        EX2_1 test = new EX2_1();
        arr = createTextFiles(arr.length, arr.length/5, arr.length);
        long start= System.currentTimeMillis();
        System.out.print("without threads number of line is "+getNumOfLines(arr));
        long end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
        start=System.currentTimeMillis();
        System.out.print("with threads number of line is "+test.getNumOfLinesThreads(arr));
        end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
        start=System.currentTimeMillis();
        System.out.print("with threadpool number of line is "+test.getNumOfLinesThreadPool(arr));
        end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
        delete(arr);
    }


    // delete all the file remove before submitting the assignment
    private static void delete(String[] fileNames) {
        for (String fileName : fileNames) {
            File myObj = new File(fileName);
            myObj.delete();
        }
    }
}