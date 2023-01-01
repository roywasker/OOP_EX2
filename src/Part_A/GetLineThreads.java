package Part_A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetLineThreads extends Thread{

    private String FileName;
    private int NumOfLine=0;

    public GetLineThreads(String FileName){
        super(FileName);
        this.FileName=FileName;
    }

    /**
     * The method count how much line the file have
     */
    public void run(){
        try {
            BufferedReader reader =new BufferedReader(new FileReader(FileName));
            while (reader.readLine()!=null) {
                NumOfLine++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getNumOfLine() {
        return NumOfLine;
    }
}