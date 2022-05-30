package com.nicoardizzoli.technicalinterviewbank.movement;

import com.nicoardizzoli.technicalinterviewbank.model.*;
import com.nicoardizzoli.technicalinterviewbank.repository.MovementRepository;
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
class MovementRepositoryTest {

    @Autowired
    private MovementRepository movementRepository;

    @BeforeEach
    void setUp() {
    }

//    @Test
    void itShouldFindMovimientosByTipoDateAndCuenta() {
        //Given
        String identification = "id35";

        Account account = Account.builder()
                .accountType(AccountType.SAVINGS)
                .movements(new ArrayList<>())
                .state(true)
                .withdrawLimit(BigDecimal.valueOf(1000))
                .balance(BigDecimal.valueOf(2000))
                .build();

        Customer customer = Customer.builder()
                .customerId(UUID.randomUUID().toString())
                .password("1234")
                .surname("Ardizzoli")
                .name("Nicolas")
                .address("direccion 123")
                .age(21)
                .state(true)
                .gender(Gender.MALE)
                .identification(identification)
                .phoneNumber("123123123")
                .build();

        customer.addAccount(account);

        Movement movement = Movement.builder()
                .movementType(MovementType.WITHDRAW)
                .account(account)
                .date(LocalDateTime.now())
                .amount(BigDecimal.valueOf(1000))
                .build();

        //When


        //Then

    }


//    @Test
//    void itShouldFindMovimientosByFechaBetween() {
//        //Given
//        //When
//        //Then
//
//    }

//    @Test
//    void itShouldMovimientosReportByFechaBetweenAndCliente() {
//        //Given
//        //When
//        //Then
//
//    }
}