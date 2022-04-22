package com.nicoardizzoli.pruebatecnicabanco.dto;

import com.nicoardizzoli.pruebatecnicabanco.model.TipoCuenta;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaDTO {
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
}
