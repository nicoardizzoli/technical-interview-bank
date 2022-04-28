package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.mapper.ClienteMapper;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {


    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    private ClienteService underTestClienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTestClienteService = new ClienteService(clienteRepository, clienteMapper);
    }

    @Test
    void itShouldSaveCliente() {
        //Given
        //When
        //Then

    }
}