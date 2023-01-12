package Part_A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetLineThreads extends Thread{

    private String FileName;
    private int NumOfLine=0;

    // constructor
    public GetLineThreads(String FileName){
        super(FileName);
        this.FileName=FileName;
    }

    /**
     * This method count the number of lines in the file.
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

    /**
     * getter method for the NumOfLine attribute
     * @return
     */
    public int getNumOfLine() {
        return NumOfLine;
    }
}