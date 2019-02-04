package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class AllCustomersResponse {

    List<CustomerSimpleInformation> customers;

    public List<CustomerSimpleInformation> customers() {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        return customers;
    }

}
