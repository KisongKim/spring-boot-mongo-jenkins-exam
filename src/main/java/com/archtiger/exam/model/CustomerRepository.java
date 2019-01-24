package com.archtiger.exam.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

    Optional<Customer> findByEmail(String email);

    @Query("select c from Customer c join fetch c.deliveryAddresses where c.id = ?1")
    Optional<Customer> findByIdJoinFetch(Long id);

}
