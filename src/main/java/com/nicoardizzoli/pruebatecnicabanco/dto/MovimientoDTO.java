package com.nicoardizzoli.pruebatecnicabanco.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovimientoDTO {

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime fecha;

    @NotBlank(message = "tipo de movimiento requerido - DEPOSITO o RETIRO")
    private String tipoMovimiento;

    @NotNull(message = "valor requerido")
    private BigDecimal valor;

    private CuentaDTO cuenta;
}
