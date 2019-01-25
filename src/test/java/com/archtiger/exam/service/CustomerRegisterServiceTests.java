package com.archtiger.exam.service;

import com.archtiger.exam.CustomerPopulator;
import com.archtiger.exam.ExamException;
import com.archtiger.exam.ExamExceptionMatcher;
import com.archtiger.exam.contract.CustomerRegisterRequest;
import com.archtiger.exam.contract.CustomerRegisterResponse;
import com.archtiger.exam.model.Customer;
import com.archtiger.exam.model.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CustomerRegisterServiceTests {

    private CustomerRegisterService customerRegisterService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.customerRegisterService = new CustomerRegisterServiceImpl(customerRepository, passwordEncoder);
    }

    @Test
    public void register() {
        final String email = "user@junit.com";
        final String password = "password";
        final String familyName = "Junit";
        final String givenName = "Test";
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Customer customer = new Customer(email, password, familyName, givenName, LocalDateTime.now());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(new BCryptPasswordEncoder().encode(password));
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        CustomerRegisterRequest request = new CustomerRegisterRequest(email, password, familyName, givenName);
        CustomerRegisterResponse response = customerRegisterService.register(request);

        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerRepository, Mockito.times(1)).findByEmail(emailArgument.capture());
        ArgumentCaptor<Customer> customerArgument = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customerArgument.capture());
        Mockito.verifyNoMoreInteractions(customerRepository);

        Assert.assertEquals(email, emailArgument.getValue());
        Customer capturedCustomer = customerArgument.getValue();
        Assert.assertEquals(email, capturedCustomer.getEmail());
        Assert.assertEquals(familyName, capturedCustomer.getFamilyName());
        Assert.assertEquals(givenName, capturedCustomer.getGivenName());

        Assert.assertEquals(email, response.getEmail());
        Assert.assertEquals(familyName, response.getFamilyName());
        Assert.assertEquals(givenName, response.getGivenName());
    }

    @Test
    public void register_expectedException40002() {
        thrown.expect(ExamException.class);
        thrown.expect(ExamExceptionMatcher.is(40002));

        String email = "john@test.com";
        Optional<Customer> customer = CustomerPopulator.populate()
                .stream()
                .filter(i -> i.getEmail().equals(email))
                .findFirst();
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(customer);

        CustomerRegisterRequest request = new CustomerRegisterRequest(email,
                "password",
                "John",
                "Doe");
        customerRegisterService.register(request);
    }

}
