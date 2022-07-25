package com.nicoardizzoli.technicalinterviewbank.controller;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Save a customer
     *
     * @param customerDTO complete and valid customerDTO
     * @return a message of success with the id of the customer
     */
    @Operation(summary = "Save a valid Customer")
    @PostMapping("/save")
    public ResponseEntity<String> saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerSaved = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>("Customer saved successfully, identification: " + customerSaved.getIdentification(), HttpStatus.CREATED);
    }


    /**
     * @param phoneNumber of customer to search
     * @return customerDto or throw NotFoundException
     */
    @Operation(summary = "Search and return a customer by phoneNumber")
    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<CustomerDTO> getCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        CustomerDTO customerDto = customerService.getCustomerByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * @param identification of customer to search
     * @return customerDto or throw NotFoundException
     */
    @Operation(summary = "Search and return a customer by identification")
    @GetMapping("/identification/{identification}")
    public ResponseEntity<CustomerDTO> getCustomerByIdentification(@PathVariable("identification") String identification) {
        CustomerDTO customerDto = customerService.getCustomerByIdentification(identification);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * @return all customers
     */
    @Operation(summary = "Search and return all customers")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }


    /**
     *
     * @param identification of customer to delete
     * @return message
     */
    @Operation(summary = "Delete a customer and all of your accounts and movements by customerIdentification")
    @DeleteMapping("/delete/{identification}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("identification") String identification) {
        customerService.deleteCustomer(identification);
        return new ResponseEntity<>("Customer deleted", HttpStatus.OK);
    }

}
