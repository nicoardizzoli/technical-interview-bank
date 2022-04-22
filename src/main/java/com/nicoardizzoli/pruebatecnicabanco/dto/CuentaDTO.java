package com.nicoardizzoli.pruebatecnicabanco.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaDTO {
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal tope;
    private ClienteDTO titular;
}
