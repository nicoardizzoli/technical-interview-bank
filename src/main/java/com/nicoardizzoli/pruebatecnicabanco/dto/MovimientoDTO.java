package com.nicoardizzoli.pruebatecnicabanco.dto;

import com.nicoardizzoli.pruebatecnicabanco.model.TipoMovimiento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimientoDTO {
    private LocalDateTime fecha;
    private String tipoMovimiento;
}
