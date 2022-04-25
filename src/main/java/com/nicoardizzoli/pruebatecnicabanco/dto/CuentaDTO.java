package com.nicoardizzoli.pruebatecnicabanco.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CuentaDTO {

    @NotBlank(message = "tipo de cuenta requerido")
    private String tipoCuenta;

    @NotNull(message = "saldo inicial requerido")
    private BigDecimal saldoInicial;

    @NotNull(message = "estado requerido")
    private Boolean estado;

    @NotNull(message = "tope requerido")
    private BigDecimal tope;

    @NotNull(message = "titular requerido")
    private ClienteDTO titular;
}
