package com.archtiger.exam;

import com.archtiger.exam.model.Customer;
import com.archtiger.exam.model.DeliveryAddress;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CustomerPopulator {

    public static List<Customer> populate() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        Customer c1 = new Customer(
                "john@test.com",
                passwordEncoder.encode("password1"),
                "John",
                "Doe",
                LocalDateTime.now());
        DeliveryAddress cda1 = new DeliveryAddress(c1, "Frankfurt", "Street 1", "65720", true, LocalDateTime.now());
        DeliveryAddress cda2 = new DeliveryAddress(c1, "Frankfurt", "Street 2", "65721", false, LocalDateTime.now());
        c1.deliveryAddresses().addAll(Arrays.asList(cda1, cda2));

        Customer c2 = new Customer(
                "jane@test.com",
                passwordEncoder.encode("password2"),
                "Jane",
                "Gue",
                LocalDateTime.now());
        DeliveryAddress cda3 = new DeliveryAddress(c2, "Berlin", "Street B1", "75720", false, LocalDateTime.now());
        DeliveryAddress cda4 = new DeliveryAddress(c2, "Berlin", "Street B2", "75721", true, LocalDateTime.now());
        DeliveryAddress cda5 = new DeliveryAddress(c2, "Berlin", "Street B3", "75722", false, LocalDateTime.now());
        c2.deliveryAddresses().addAll(Arrays.asList(cda3, cda4, cda5));

        Customer c3 = new Customer(
                "jonny@test.com",
                passwordEncoder.encode("password1"),
                "John",
                "Smith",
                LocalDateTime.now());
        DeliveryAddress cda6 = new DeliveryAddress(c3, "Hamburg", "Street H1", "85720", true, LocalDateTime.now());
        c3.deliveryAddresses().add(cda6);

        return Arrays.asList(c1, c2, c3);
    }

}
