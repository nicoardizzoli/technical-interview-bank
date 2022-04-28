package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovimientoReport {
    private LocalDateTime fecha;
    private String clienteNombreCompleto;
    private Integer numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldo;
    private Boolean estado;
    private BigDecimal monto;
    private BigDecimal saldoDisponible;

}
