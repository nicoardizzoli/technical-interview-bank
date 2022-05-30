package com.nicoardizzoli.technicalinterviewbank.service;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.exception.FoundException;
import com.nicoardizzoli.technicalinterviewbank.mapper.CustomerMapper;
import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import com.nicoardizzoli.technicalinterviewbank.repository.CustomerRepository;
import com.nicoardizzoli.technicalinterviewbank.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CustomerServiceTest {


    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    private CustomerService underTestCustomerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTestCustomerService = new CustomerService(customerRepository, customerMapper);
    }

    @Test
    void itShouldSaveCliente() {
        //Given
        CustomerDTO customerDTO = TestUtils.getCustomerDto();

        Customer customer = TestUtils.getCustomer();


        given(customerRepository.findCustomerByCustomerId(customerDTO.getCustomerId())).willReturn(Optional.empty());
        given(customerMapper.dtoToCustomer(customerDTO)).willReturn(customer);
        given(customerRepository.save(customer)).willReturn(customer);

        //When
        underTestCustomerService.saveCustomer(customerDTO);

        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        then(customerRepository).should().save(customerArgumentCaptor.capture());

        Customer customerSaved = customerArgumentCaptor.getValue();
        assertThat(customerSaved.getIdentification()).isEqualTo(customer.getIdentification());
    }

    @Test
    void itShouldNotSaveClienteWhenClienteExist() {
        //Given
        CustomerDTO customerDto = TestUtils.getCustomerDto();

        given(customerRepository.findCustomerByCustomerId(customerDto.getCustomerId())).willReturn(Optional.of(mock(Customer.class)));

        //When
        //Then
        assertThatThrownBy(() -> underTestCustomerService.saveCustomer(customerDto)).isInstanceOf(FoundException.class).hasMessage(String.format("The customer with id %s already exist", customerDto.getCustomerId()));
        then(customerRepository).should(never()).save(any(Customer.class));

    }
}