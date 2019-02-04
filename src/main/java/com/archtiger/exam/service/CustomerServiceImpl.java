package com.archtiger.exam.service;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.AllCustomersResponse;
import com.archtiger.exam.contract.CustomerSimpleInformation;
import com.archtiger.exam.model.Customer;
import com.archtiger.exam.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AllCustomersResponse all() throws ExamException {
        List<Customer> customers = customerRepository.findAll();
        AllCustomersResponse response = new AllCustomersResponse();
        for (Customer c : customers) {
            CustomerSimpleInformation item = new CustomerSimpleInformation(c.getEmail(), c.getRegisterDateTime());
            response.customers().add(item);
        }
        return response;
    }

}
