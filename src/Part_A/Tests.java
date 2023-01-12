package Part_A;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tests {

    private String[] arr;
    private EX2_1 test = new EX2_1();

    @BeforeAll
    public void TestCreateTextFiles(){
        int n =15000;
       arr = EX2_1.createTextFiles(n, n/2, n);
       assertEquals(arr.length,n);
   }

    @AfterAll
    public void TestDelete(){
        EX2_1.delete(arr);
    }

    @Test
    @Order(1)
    public void TestGetNumOfLines(){
        long start= System.currentTimeMillis();
        System.out.print("without threads number of line is "+EX2_1.getNumOfLines(arr));
        long end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
    }


    @Test
    @Order(2)
    public void TestGetNumOfLinesThreads(){
        long start=System.currentTimeMillis();
        System.out.print("with threads number of line is "+test.getNumOfLinesThreads(arr));
        long end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
    }

    @Test
    @Order(3)
    public void TestGetNumOfLinesThreadPool(){
        long start=System.currentTimeMillis();
        System.out.print("with threadpool number of line is "+test.getNumOfLinesThreadPool(arr));
        long end=System.currentTimeMillis();
        System.out.println(" and time is take to cal is  "+(end-start)+" ms");
    }
}

