package com.archtiger.exam.service;

import com.archtiger.exam.ExamError;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.model.Customer;
import com.archtiger.exam.model.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class CustomerRegisterServiceImpl implements CustomerRegisterService {

    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerRegisterServiceImpl(CustomerRepository customerRepository,
                                       PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public CustomerRegisterResponse register(CustomerRegisterRequest request) throws ExamException {
        // 1. check the email already taken by other customer, if yes throw a exception
        Optional<Customer> found = customerRepository.findByEmail(request.getEmail());
        if (found.isPresent()) {
             log.error("[register] Email already taken by another user.");
             throw new ExamException(ExamError.EMAIL_ALREADY_EXIST);
        }

        // 2. encode password then persist.
        Customer customer = new Customer(request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFamilyName(),
                request.getGivenName(),
                LocalDateTime.now());
        customer = customerRepository.save(customer);
        return new CustomerRegisterResponse(
                customer.getEmail(),
                customer.getFamilyName(),
                customer.getGivenName(),
                customer.getRegisterDateTime());
    }

}
