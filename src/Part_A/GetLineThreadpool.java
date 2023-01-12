package Part_A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class GetLineThreadpool implements Callable <Integer> {
    private String FileName;
    private int NumOfLine=0;

    /**
     * getter method for the FileName attribute
     * @param FileName
     */
    public GetLineThreadpool(String FileName){
        this.FileName=FileName;
    }


    /**
     * This method count the number of lines in the file.
     * @return number of lines.
     */
    @Override
    public Integer call()  {
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
        return NumOfLine;
    }
}