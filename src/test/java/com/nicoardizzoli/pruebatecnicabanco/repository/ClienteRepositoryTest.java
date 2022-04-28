package com.nicoardizzoli.pruebatecnicabanco.repository;


import com.nicoardizzoli.pruebatecnicabanco.model.Cliente;
import com.nicoardizzoli.pruebatecnicabanco.model.Genero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//ojo que esta annotation es clave para testear los repositorys, y el properties es para que tome las validaciones de @Column
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldFindClienteByClienteId() {
        //Given
        String clienteId = UUID.randomUUID().toString();
        Cliente cliente = Cliente.builder()
                .clienteId(clienteId)
                .contrasena("12345")
                .estado(true)
                .apellido("Ardizzoli")
                .nombre("Nicolas")
                .direccion("siempre viva 123")
                .edad(21)
                .genero(Genero.MASCULINO)
                .identificacion("identificaicon test")
                .telefono("12345")
                .build();

        clienteRepository.save(cliente);

        //When
        Optional<Cliente> clienteByClienteId = clienteRepository.findClienteByClienteId(clienteId);

        //Then
        assertThat(clienteByClienteId).isPresent().hasValueSatisfying(
                c -> assertThat(c).usingRecursiveComparison().ignoringFields("id").isEqualTo(cliente));
    }
}