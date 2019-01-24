package com.archtiger.exam.model;

import java.util.List;

public interface CustomerRepositoryCustom {

    List<Customer> findCustomersByEmailLike(String email);

}
