package com.org;

import com.org.dto.Customer;
import com.org.util.CustomerReader;
import com.org.util.DistanceCalculator;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Executor {

    static Logger logger = Logger.getLogger(Executor.class.getName());

    public static void main(String[] args)  {
            Executor e = new Executor();
            e.getCustomerForTea("src/main/resources/application.properties");
    }

    public void getCustomerForTea(String propertyFile) {
        try {

            FileReader reader = new FileReader(propertyFile);
            Properties p = new Properties();

            p.load(reader);
            String fileUrl = p.getProperty("customer.file.url");
            CustomerReader customerReader = new CustomerReader();

            List<Customer> customers = customerReader.getCustomers(fileUrl);
            PrintWriter pW = new PrintWriter(new FileWriter(p.getProperty("output.file")));
            if (customers.size() > 0) {

                double officeLat = Double.parseDouble(p.getProperty("office.lat"));
                double officeLon = Double.parseDouble(p.getProperty("office.lon"));
                double invitationDistance = Double.parseDouble(p.getProperty("thresold.distance"));

                logger.info("Office latitude: " + officeLat + "Office longitude: " + officeLon);

                DistanceCalculator findDistance = new DistanceCalculator(officeLat, officeLon);

                List<Customer> invitedCustomers = findDistance.findDistanceForCustomers(customers, invitationDistance);

                List<Customer> sortedCustomer = invitedCustomers.stream().sorted(Comparator.comparing(Customer::getUserID)).collect(Collectors.toList());

                writeOutPut(pW, sortedCustomer);
            } else {
                pW.println("No customer data find in file.");
            }
            logger.info("Out file Generated at location:" + p.getProperty("output.file"));
            pW.close();

        }
        catch (FileNotFoundException e) {
            logger.severe("Please provide valid file/location.");
            e.printStackTrace();
        }
        catch (IOException e) {
            logger.severe("Not able to read given file, please provide valid file format.");
            e.printStackTrace();
        }
        catch (Exception e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }

    }

    private void writeOutPut(PrintWriter pW, List<Customer> sortedCustomer) {
        if (sortedCustomer.size() > 0) {
            for (Customer customer : sortedCustomer) {
                pW.println("Customer Name: " + customer.getName() + ", Customer ID: " + customer.getUserID());
            }
        } else
            pW.println("No customer found within 100KM in file.");
    }
}
