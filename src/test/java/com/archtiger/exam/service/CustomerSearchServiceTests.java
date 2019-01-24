package com.archtiger.exam.service;

import com.archtiger.exam.CustomerPopulator;
import com.archtiger.exam.contract.CustomerSearchResultResponse;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class CustomerSearchServiceTests {

    private CustomerSearchService customerSearchService;

    @MockBean
    private CustomerRepository customerRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static List<Customer> populate(String filter) {
        List<Customer> cs = CustomerPopulator.populate();
        return cs.stream().filter(c -> c.getEmail().startsWith(filter)).collect(Collectors.toList());
    }

    @Before
    public void setUp() {
        this.customerSearchService = new CustomerSearchServiceImpl(customerRepository);
    }

    @Test
    public void searchCustomerByEmail_withEmailFormat() {
        List<Customer> found = populate("john");
        Mockito.when(customerRepository.findCustomersByEmailLike(Mockito.anyString())).thenReturn(found);

        String searchTerm = "john@test.com";
        CustomerSearchResultResponse response = customerSearchService.searchCustomerByEmail(searchTerm);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerRepository, Mockito.times(1))
                .findCustomersByEmailLike(argument.capture());
        Mockito.verifyNoMoreInteractions(customerRepository);

        String captured = argument.getValue();
        Assert.assertEquals("john", captured);
        Assert.assertTrue(response.customers().size() == 1);
    }

    @Test
    public void searchCustomerByEmail_withNonEmailFormat() {
        List<Customer> found = populate("jane");
        Mockito.when(customerRepository.findCustomersByEmailLike(Mockito.anyString())).thenReturn(found);

        String searchTerm = "jane";
        CustomerSearchResultResponse response = customerSearchService.searchCustomerByEmail(searchTerm);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerRepository, Mockito.times(1))
                .findCustomersByEmailLike(argument.capture());
        Mockito.verifyNoMoreInteractions(customerRepository);

        String captured = argument.getValue();
        Assert.assertEquals("jane", captured);
        Assert.assertTrue(response.customers().size() == 1);
    }

    @Test
    public void searchCustomerByEmail_expectMoreThanOneCustomer() {
        List<Customer> found = populate("jo");
        Mockito.when(customerRepository.findCustomersByEmailLike(Mockito.anyString())).thenReturn(found);

        String searchTerm = "jo@test.com";
        CustomerSearchResultResponse response = customerSearchService.searchCustomerByEmail(searchTerm);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerRepository, Mockito.times(1))
                .findCustomersByEmailLike(argument.capture());
        Mockito.verifyNoMoreInteractions(customerRepository);

        String captured = argument.getValue();
        Assert.assertEquals("jo", captured);
        Assert.assertTrue(response.customers().size() > 1);
    }

    @Test
    public void searchCustomerByEmail_expectNoCustomer() {
        List<Customer> found = populate("zebra");
        Mockito.when(customerRepository.findCustomersByEmailLike(Mockito.anyString())).thenReturn(found);

        String searchTerm = "zebra@test.com";
        CustomerSearchResultResponse response = customerSearchService.searchCustomerByEmail(searchTerm);

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        Mockito.verify(customerRepository, Mockito.times(1))
                .findCustomersByEmailLike(argument.capture());
        Mockito.verifyNoMoreInteractions(customerRepository);

        String captured = argument.getValue();
        Assert.assertEquals("zebra", captured);
        Assert.assertTrue(response.customers().size() == 0);
    }

}
