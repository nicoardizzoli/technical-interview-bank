package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CuentaMovimientoId implements Serializable {

    private static final long serialVersionUID = 2106911912259706491L;

    @Column(name = "cuenta_id")
    private Long cuentaId;

    @Column(name = "movimiento_id")
    private Long movimientoId;
}
