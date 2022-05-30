package com.nicoardizzoli.technicalinterviewbank.controller;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Save a customer
     * @param customerDTO complete and valid customerDTO
     * @return a message of success with the id of the customer
     */
    @Operation(summary = "Save a valid Customer")
    @PostMapping("/save")
    public ResponseEntity<String> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        CustomerDTO customerSaved = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>("Customer saved successfully, id: "+ customerSaved.getCustomerId(),HttpStatus.CREATED);
    }

    /**
     * Get customer by id
     * @param customerId id of customer to search
     * @return customerDto or throw NotFoundException
     */
    @Operation(summary = "Search and return a customer by customerId")
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("customerId") String customerId){
        CustomerDTO customerDto = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customerDto,HttpStatus.CREATED);
    }
}
