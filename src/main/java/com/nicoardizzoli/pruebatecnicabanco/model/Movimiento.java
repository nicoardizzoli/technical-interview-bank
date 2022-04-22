package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movimiento implements Serializable {

    @Id
    @SequenceGenerator(name = "movimiento_id_seq", sequenceName = "movimiento_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_id_seq")
    private Long movimientoId;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private TipoMovimiento tipoMovimiento;

    @NotNull
    private BigDecimal valor;

    @NotNull
    @Column(name = "saldo_cuenta")
    private BigDecimal saldoCuenta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento")
    @ToString.Exclude
    private List<CuentaMovimiento> movimientos = new ArrayList<>();


    public void addMovimiento(CuentaMovimiento cuentaMovimiento) {
        if (!this.movimientos.contains(cuentaMovimiento)) {
            this.movimientos.add(cuentaMovimiento);
        }
    }

    public void removeMovimiento(CuentaMovimiento cuentaMovimiento) {
        this.movimientos.remove(cuentaMovimiento);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movimiento that = (Movimiento) o;
        return movimientoId != null && Objects.equals(movimientoId, that.movimientoId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
