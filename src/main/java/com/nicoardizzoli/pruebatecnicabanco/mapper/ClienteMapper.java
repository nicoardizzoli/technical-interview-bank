package com.nicoardizzoli.pruebatecnicabanco.mapper;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {


    ClienteDTO clienteToDto(Cliente cliente);

    Cliente dtoToCliente(ClienteDTO clienteDTO);
}
