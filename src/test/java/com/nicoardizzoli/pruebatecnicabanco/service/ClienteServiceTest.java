package com.nicoardizzoli.pruebatecnicabanco.service;

import com.nicoardizzoli.pruebatecnicabanco.dto.ClienteDTO;
import com.nicoardizzoli.pruebatecnicabanco.exception.FoundException;
import com.nicoardizzoli.pruebatecnicabanco.mapper.ClienteMapper;
import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.model.Genero;
import com.nicoardizzoli.pruebatecnicabanco.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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
        String identificacion = "id35";
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .contrasena("1234")
                .apellido("Ardizzoli")
                .nombre("Nicolas")
                .direccion("direccion 123")
                .edad(21)
                .estado(true)
                .genero("MASCULINO")
                .identificacion(identificacion)
                .telefono("123123123")
                .build();

        Cliente cliente = Cliente.builder()
                .contrasena("1234")
                .apellido("Ardizzoli")
                .nombre("Nicolas")
                .direccion("direccion 123")
                .edad(21)
                .estado(true)
                .genero(Genero.MASCULINO)
                .identificacion(identificacion)
                .telefono("123123123")
                .build();


        given(clienteRepository.findClienteByClienteId(clienteDTO.getClienteId())).willReturn(Optional.empty());
        given(clienteMapper.dtoToCliente(clienteDTO)).willReturn(cliente);

        //When
        underTestClienteService.saveCliente(clienteDTO);

        //Then
        ArgumentCaptor<Cliente> clienteArgumentCaptor = ArgumentCaptor.forClass(Cliente.class);
        then(clienteRepository).should().save(clienteArgumentCaptor.capture());

        assertThat(clienteArgumentCaptor.getValue().getIdentificacion()).isEqualTo(identificacion);
    }

    @Test
    void itShouldNotSaveClienteWhenClienteExist() {
        //Given
        String identificacion = "id35";
        String clienteId = UUID.randomUUID().toString();
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .contrasena("1234")
                .clienteId(clienteId)
                .apellido("Ardizzoli")
                .nombre("Nicolas")
                .direccion("direccion 123")
                .edad(21)
                .estado(true)
                .genero("MASCULINO")
                .identificacion(identificacion)
                .telefono("123123123")
                .build();

        given(clienteRepository.findClienteByClienteId(clienteDTO.getClienteId())).willReturn(Optional.of(mock(Cliente.class)));

        //When
        //Then
        assertThatThrownBy(() -> underTestClienteService.saveCliente(clienteDTO)).isInstanceOf(FoundException.class).hasMessage("El cliente con id "+clienteId+" ya existe");
        then(clienteRepository).should(never()).save(any(Cliente.class));

    }
}