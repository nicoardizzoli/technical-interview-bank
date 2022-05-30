package com.nicoardizzoli.technicalinterviewbank.utils;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import com.nicoardizzoli.technicalinterviewbank.model.Gender;

import java.util.UUID;

public class TestUtils {

    public static CustomerDTO getCustomerDto() {

        return CustomerDTO.builder()
                .customerId(UUID.randomUUID().toString())
                .password("1234")
                .surname("Ardizzoli")
                .name("Nicolas")
                .address("direccion 123")
                .age(21)
                .state(true)
                .gender(Gender.MALE)
                .identification("id35")
                .phoneNumber("123123123")
                .build();
    }

    public static Customer getCustomer() {
        return Customer.builder()
                .customerId(UUID.randomUUID().toString())
                .password("1234")
                .surname("Ardizzoli")
                .name("Nicolas")
                .address("direccion 123")
                .age(21)
                .state(true)
                .gender(Gender.MALE)
                .identification("id35")
                .phoneNumber("123123123")
                .build();
    }
}
