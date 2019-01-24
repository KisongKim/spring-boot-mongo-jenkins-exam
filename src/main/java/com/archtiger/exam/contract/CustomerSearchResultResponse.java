package com.archtiger.exam.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
@NoArgsConstructor
public class CustomerSearchResultResponse {

    @JsonProperty(required = true, value = "customers")
    private List<CustomerSimpleInformation> customers;

    public List<CustomerSimpleInformation> customers() {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        return customers;
    }

}
