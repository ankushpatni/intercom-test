package com.org;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ExecutorTest {

    @Test
    public void getCustomerForTea() throws FileNotFoundException {
        Executor e = new Executor();
        try {
            File fd = new File("output/output.txt");
            if (fd != null)
                fd.delete();
        }
        catch(Exception ex)
        {
            System.out.println("file is not available.");
        }

        e.getCustomerForTea("src/test/resources/application.properties");
        FileReader f = new FileReader("output/output.txt");
        assertNotNull(f);
    }

}
