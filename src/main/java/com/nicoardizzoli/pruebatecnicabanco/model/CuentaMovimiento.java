package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Table(name = "cuenta_movimiento")
@Entity(name = "CuentaMovimiento")
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMovimiento implements Serializable {

    @EmbeddedId
    private CuentaMovimientoId cuentaMovimientoId;

    @ManyToOne
    @MapsId("cuentaId")
    @JoinColumn(name = "cuenta_id", foreignKey = @ForeignKey(name = "cuenta_cuentamovimiento_id_fk"))
    private Cuenta cuenta;

    @ManyToOne
    @MapsId("movimientoId")
    @JoinColumn(name = "movimiento_id", foreignKey = @ForeignKey(name = "movimiento_cuentamovimiento_id_fk"))
    private Movimiento movimiento;




    public CuentaMovimiento(Cuenta cuenta, Movimiento movimiento){
        this.cuentaMovimientoId = new CuentaMovimientoId(cuenta.getCuentaId(), movimiento.getMovimientoId());
        this.cuenta = cuenta;
        this.movimiento = movimiento;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CuentaMovimiento that = (CuentaMovimiento) o;
        return cuentaMovimientoId != null && Objects.equals(cuentaMovimientoId, that.cuentaMovimientoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuentaMovimientoId);
    }
}
