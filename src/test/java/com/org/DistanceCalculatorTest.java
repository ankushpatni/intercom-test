package com.org;

import com.org.dto.Customer;
import com.org.util.DistanceCalculator;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class DistanceCalculatorTest {
    Properties p = null;
    double officeLat ;
    double officeLon ;
    @Before
    public void init() throws IOException {
        FileReader reader = new FileReader("src/test/resources/application.properties");
        p = new Properties();

        p.load(reader);
        officeLat = Double.parseDouble(p.getProperty("office.lat"));
        officeLon = Double.parseDouble(p.getProperty("office.lon"));
    }

    @Test
    public void getDistanceFromOfficeTest() throws IOException {

        DistanceCalculator findDistance = new DistanceCalculator(officeLat, officeLon);

        Customer customer = new Customer();
        customer.setLatitude(52.3191841);
        customer.setLongitude(-8.5072391);
        customer.setUserID(3);

        double dis = findDistance.findDistanceBetweenPointAndOffice(customer);
        assertEquals(188.95027293951009,dis,0);
    }

    @Test
    public void findDistanceForCustomersTest()
    {
        DistanceCalculator findDistance = new DistanceCalculator(officeLat, officeLon);
        Customer customer = new Customer();
        customer.setLatitude(52.3191841);
        customer.setLongitude(-8.5072391);
        customer.setUserID(3);

        Customer customer1 = new Customer();
        customer1.setLatitude(52.986375);
        customer1.setLongitude(-6.043701);
        customer1.setUserID(12);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        customers.add(customer1);

        List<Customer> invitedCustomers = findDistance.findDistanceForCustomers(customers, 100);
        assertEquals(1,invitedCustomers.size());
    }
}
