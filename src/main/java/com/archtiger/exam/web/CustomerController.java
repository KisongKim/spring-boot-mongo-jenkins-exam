package com.archtiger.exam.web;

import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.contract.CustomerSearchResultResponse;
import com.archtiger.exam.service.CustomerRegisterService;
import com.archtiger.exam.service.CustomerSearchService;
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

    @Autowired
    public CustomerController(CustomerRegisterService customerRegisterService,
                              CustomerSearchService customerSearchService) {
        this.customerRegisterService = customerRegisterService;
        this.customerSearchService = customerSearchService;
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

}
