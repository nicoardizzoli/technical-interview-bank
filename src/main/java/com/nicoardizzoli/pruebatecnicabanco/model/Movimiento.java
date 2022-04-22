package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movimiento {

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
