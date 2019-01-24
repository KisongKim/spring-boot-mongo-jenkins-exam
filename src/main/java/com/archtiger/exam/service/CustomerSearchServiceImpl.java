package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.CustomerSearchResultResponse;
import com.archtiger.exam.contract.CustomerSimpleInformation;
import com.archtiger.exam.model.Customer;
import com.archtiger.exam.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerSearchServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerSearchResultResponse searchCustomerByEmail(String email) throws ExamException {
        // if email is email format drop @
        String normalizedEmail = email.contains("@") == true ? email.substring(0, email.indexOf("@")) : email;
        List<Customer> customers = customerRepository.findCustomersByEmailLike(normalizedEmail);

        CustomerSearchResultResponse response = new CustomerSearchResultResponse();
        for (Customer c : customers) {
            response.customers().add(new CustomerSimpleInformation(c.getEmail(), c.getRegisterDateTime()));
        }
        return response;
    }

}
