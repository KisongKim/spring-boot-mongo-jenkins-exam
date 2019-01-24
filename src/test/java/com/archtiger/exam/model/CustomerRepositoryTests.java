package com.archtiger.exam.model;

import com.archtiger.exam.CustomerPopulator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        List<Customer> customers = CustomerPopulator.populate();
        customerRepository.saveAll(customers);
    }

    @After
    public void clear() {
        customerRepository.deleteAll();
    }

    @Test
    public void findByIdJoinFetch() {
        List<Customer> customers = customerRepository.findAll();

        Optional<Customer> found = customerRepository.findByIdJoinFetch(customers.get(0).getId());
        Assert.assertTrue(found.isPresent());
        Customer customer = found.get();

        List<DeliveryAddress> addresses = customer.deliveryAddresses()
                .stream()
                .filter(a -> a.getFavorite() == true)
                .collect(Collectors.toList());
        Assert.assertTrue(addresses.size() == 1);
        addresses.forEach(i -> System.out.println(i));
    }

    @Test
    public void findCustomersByEmailLike() {
        String emailToSearch = "jo";
        List<Customer> results = customerRepository.findCustomersByEmailLike(emailToSearch);
        System.out.println(results.size());
        Assert.assertFalse(results.isEmpty());
    }

}
