package com.nicoardizzoli.pruebatecnicabanco.movimiento;

import com.nicoardizzoli.pruebatecnicabanco.model.*;
import com.nicoardizzoli.pruebatecnicabanco.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

//ojo que esta annotation es clave para testear los repositorys, y el properties es para que tome las validaciones de @Column
@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
class MovimientoRepositoryTest {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldFindMovimientosByTipoDateAndCuenta() {
        //Given
        Cuenta cuenta = Cuenta.builder()
                .tipoCuenta(TipoCuenta.AHORRO)
                .movimientos(new ArrayList<>())
                .estado(true)
                .tope(BigDecimal.valueOf(1000))
                .saldoInicial(BigDecimal.valueOf(2000))
                .build();

        Cliente cliente = Cliente.builder()
                .clienteId(UUID.randomUUID().toString())
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

        cliente.addCuenta(cuenta);

        Movimiento movimiento = Movimiento.builder()
                .tipoMovimiento(TipoMovimiento.RETIRO)
                .cuenta(cuenta)
                .fecha(LocalDateTime.now())
                .valor(BigDecimal.valueOf(1000))
                .build();

        //When


        //Then

    }


    @Test
    void itShouldFindMovimientosByFechaBetween() {
        //Given
        //When
        //Then

    }

    @Test
    void itShouldMovimientosReportByFechaBetweenAndCliente() {
        //Given
        //When
        //Then

    }
}