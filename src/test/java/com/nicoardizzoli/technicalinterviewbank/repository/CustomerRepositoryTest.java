package com.nicoardizzoli.technicalinterviewbank.repository;


import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import com.nicoardizzoli.technicalinterviewbank.model.Gender;
import com.nicoardizzoli.technicalinterviewbank.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//ojo que esta annotation es clave para testear los repositorys, y el properties es para que tome las validaciones de @Column
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldFindClienteByClienteId() {
//        Given
        Customer customer = TestUtils.getCustomer();

        customerRepository.save(customer);

        //When
        Optional<Customer> clienteByClienteId = customerRepository.findCustomerByCustomerId(customer.getCustomerId());

        //Then
        assertThat(clienteByClienteId).isPresent().hasValueSatisfying(
                c -> assertThat(c).usingRecursiveComparison().ignoringFields("customerId").isEqualTo(customer));
    }
}