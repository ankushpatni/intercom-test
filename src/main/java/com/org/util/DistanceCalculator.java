package com.org.util;

import com.org.dto.Customer;

import java.util.ArrayList;
import java.util.List;

public class DistanceCalculator {

    double officeLat;
    double officeLon;

    public DistanceCalculator(double lat, double lon)
    {
        this.officeLat = lat;
        this.officeLon = lon;
    }

    public double findDistanceBetweenPointAndOffice(Customer customer)
    {
        return distance(customer.getLatitude(), customer.getLongitude(),officeLat,officeLon);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public List<Customer> findDistanceForCustomers(List<Customer> customers, double invitationDistance)
    {
        List<Customer> invitedCustomers = new ArrayList<>();
        for(Customer customer : customers)
        {
            double distance = findDistanceBetweenPointAndOffice(customer);
            System.out.println("Customer Name: "+customer.getName() + " :: Customer distance: "+distance);
            if(distance <= invitationDistance)
                invitedCustomers.add(customer);
        }
        return invitedCustomers;
    }
}
