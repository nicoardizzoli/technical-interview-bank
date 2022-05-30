package com.nicoardizzoli.technicalinterviewbank.mapper;

import com.nicoardizzoli.technicalinterviewbank.dto.CustomerDTO;
import com.nicoardizzoli.technicalinterviewbank.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    CustomerDTO customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDTO clienteDTO);
}
