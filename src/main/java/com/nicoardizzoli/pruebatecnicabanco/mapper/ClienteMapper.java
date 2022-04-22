package com.nicoardizzoli.pruebatecnicabanco.mapper;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

//    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDTO clienteToDto(Cliente cliente);

    Cliente dtoToCliente(ClienteDTO clienteDTO);
}
