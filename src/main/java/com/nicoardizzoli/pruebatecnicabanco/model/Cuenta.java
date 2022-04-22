package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuenta {

    @Id
    @SequenceGenerator(name = "cuenta_id_seq", sequenceName = "cuenta_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_id_seq")
    @Column(name = "cuenta_id")
    private Long cuentaId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @NotNull
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    @NotNull
    private Boolean estado = false;

    @ManyToOne()
    @JoinColumn(name = "cliente_id", nullable = false, referencedColumnName = "cliente_id")
    private Cliente titular;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cuenta cuenta = (Cuenta) o;
        return cuentaId != null && Objects.equals(cuentaId, cuenta.cuentaId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
