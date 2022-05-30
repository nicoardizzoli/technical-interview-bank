package com.nicoardizzoli.technicalinterviewbank.repository;

import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerId(String customerId);
}
