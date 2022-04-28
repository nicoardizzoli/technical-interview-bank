package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.ApiRequestException;
import com.nicoardizzoli.pruebatecnicabanco.exception.FoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.ClienteMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.model.Genero;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public Cliente saveCliente(ClienteDTO clienteDTO) {

        Optional<Cliente> clienteByClienteId = clienteRepository.findClienteByClienteId(clienteDTO.getClienteId());
        if (clienteByClienteId.isPresent()) throw new FoundException(String.format("El cliente con id %s ya existe", clienteDTO.getClienteId()));
        if (Arrays.stream(Genero.values()).noneMatch(genero -> genero.toString().equals(clienteDTO.getGenero()))) throw new ApiRequestException(String.format("El genero %s no existe", clienteDTO.getGenero()));

        Cliente cliente = clienteMapper.dtoToCliente(clienteDTO);
        cliente.setClienteId(UUID.randomUUID().toString());
        log.info(cliente.toString());
        return clienteRepository.save(cliente);
    }

}
