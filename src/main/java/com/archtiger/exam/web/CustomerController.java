package com.archtiger.exam.web;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.AllCustomersResponse;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.contract.CustomerSearchResultResponse;
import com.archtiger.exam.service.CustomerRegisterService;
import com.archtiger.exam.service.CustomerSearchService;
import com.archtiger.exam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
public class CustomerController {

    private CustomerRegisterService customerRegisterService;

    private CustomerSearchService customerSearchService;

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerRegisterService customerRegisterService,
                              CustomerSearchService customerSearchService,
                              CustomerService customerService) {
        this.customerRegisterService = customerRegisterService;
        this.customerSearchService = customerSearchService;
        this.customerService = customerService;
    }

    @PostMapping(path = "customer")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerRegisterResponse registerCustomer(@Valid @RequestBody CustomerRegisterRequest request)
            throws ExamException {
        return customerRegisterService.register(request);
    }

    @GetMapping(path = "customer/filter")
    public CustomerSearchResultResponse searchCustomer(@Email @RequestParam String email) throws ExamException {
        return customerSearchService.searchCustomerByEmail(email);
    }

    @GetMapping(path = "api/customer")
    public AllCustomersResponse all() throws ExamException {
        return customerService.all();
    }

}
