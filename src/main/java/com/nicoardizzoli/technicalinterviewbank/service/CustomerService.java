package com.nicoardizzoli.technicalinterviewbank.service;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.exception.ApiRequestException;
import com.nicoardizzoli.technicalinterviewbank.exception.FoundException;
import com.nicoardizzoli.technicalinterviewbank.exception.NotFoundException;
import com.nicoardizzoli.technicalinterviewbank.mapper.CustomerMapper;
import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import com.nicoardizzoli.technicalinterviewbank.model.Gender;
import com.nicoardizzoli.technicalinterviewbank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

        Optional<Customer> customerByCustomerIdentification = customerRepository.findCustomerByIdentification(customerDTO.getIdentification());
        if (customerByCustomerIdentification.isPresent()) throw new FoundException(String.format("The customer with identification %s already exist", customerDTO.getIdentification()));

        Optional<Customer> customerByCustomerPhoneNumber = customerRepository.findCustomerByPhoneNumber(customerDTO.getPhoneNumber());
        if (customerByCustomerPhoneNumber.isPresent()) throw new FoundException(String.format("The customer with phone number %s already exist", customerDTO.getPhoneNumber()));

        if (Arrays.stream(Gender.values()).noneMatch(genero -> genero.toString().equals(customerDTO.getGender().toString()))) throw new ApiRequestException(String.format("Gender %s does not exist", customerDTO.getGender()));

        Customer customer = customerMapper.dtoToCustomer(customerDTO);
        customer.setCustomerId(UUID.randomUUID().toString());
        log.info(customer.toString());
        Customer customerSaved = customerRepository.save(customer);
        customerDTO.setCustomerId(customerSaved.getCustomerId());
        return customerDTO;
    }

//    public CustomerDTO getCustomerById(String clienteId) {
//        Optional<Customer> clienteByClienteId = customerRepository.findCustomerByCustomerId(clienteId);
//        Customer customer = clienteByClienteId.orElseThrow(() -> new NotFoundException("The customer with id " + clienteId + "not found"));
//        return customerMapper.customerToDto(customer);
//
//    }

    public CustomerDTO getCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> clienteByClienteId = customerRepository.findCustomerByPhoneNumber(phoneNumber);
        Customer customer = clienteByClienteId.orElseThrow(() -> new NotFoundException("The customer with phone number " + phoneNumber + "not found"));
        return customerMapper.customerToDto(customer);
    }

    public CustomerDTO getCustomerByIdentification(String identification) {
        Optional<Customer> clienteByClienteId = customerRepository.findCustomerByIdentification(identification);
        Customer customer = clienteByClienteId.orElseThrow(() -> new NotFoundException("The customer with identification " + identification + "not found"));
        return customerMapper.customerToDto(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToDto).collect(Collectors.toList());
    }

}
