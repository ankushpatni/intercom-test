package com.org;

import com.org.dto.Customer;
import com.org.util.CustomerReader;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class CustomerReaderTest {

    @Test
    public void getCustomerForTea() throws IOException {
        FileReader reader = new FileReader("src/test/resources/application.properties");
        Properties p = new Properties();

        p.load(reader);
        String fileUrl = p.getProperty("customer.file.url");
        CustomerReader customerReader = new CustomerReader();

        List<Customer> customers = customerReader.getCustomers(fileUrl);
        assertEquals(32, customers.size());

    }
}
