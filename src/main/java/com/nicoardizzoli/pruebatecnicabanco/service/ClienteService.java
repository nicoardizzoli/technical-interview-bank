package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.mapper.ClienteMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.INSTANCE.dtoToCliente(clienteDTO);
        cliente.setClienteId(UUID.randomUUID().toString());
        log.info(cliente.toString());
        return clienteRepository.save(cliente);
    }

}
