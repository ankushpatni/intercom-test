package com.org.util;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.dto.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CustomerReader {

    Logger logger = Logger.getLogger(CustomerReader.class.getName());

    public List<Customer> getCustomers(String fileUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        BufferedReader in = null;
        List<Customer> customers = new ArrayList();
        URL url = new URL(fileUrl);
        in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {

            try {
                Customer customer = mapper.readValue(inputLine, Customer.class);
                customers.add(customer);
            } catch (JsonMappingException e) {
                logger.severe("File record is not Parsable. " + e.getMessage());
            }
        }

        in.close();
        logger.info("Customer data has been read. Total record found: " + customers.size());
        return customers;
    }
}
