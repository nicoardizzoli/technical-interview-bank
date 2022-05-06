package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cuenta")
//ESTE NOMBRE ES EL QUE SE VA A USAR EN JPQL PARA LAS QUERYS, SI BIEN LO HACE AUTOMATICO ES UNA BUENA PRACTICA PONERLO.
public class Cuenta implements Serializable {

    @Id
    @SequenceGenerator(name = "cuenta_id_seq", sequenceName = "cuenta_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_id_seq")
    @Column(name = "cuenta_id")
    private Long cuentaId;

    private Integer numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    private Boolean estado = false;

    @ManyToOne()
    @JoinColumn(name = "cliente_id", nullable = false, referencedColumnName = "cliente_id", foreignKey = @ForeignKey(name = "cliente_id_cuenta_id_fk"))
    private Cliente titular;

    private BigDecimal tope = new BigDecimal(1000);


    //OJO EN LOS ONE TO MANY, HAY QUE HACER LOS METODOS ADD PARA LA BIDIRECCIONALIDAD.
    //LA MANERA DE INICIALIZAR LOS MOVIMIENTOS EN LA CUENTA, YA Q ESTAN LAZY, ES LLAMANDO AL GETTER LOS TRAE AUTOMATICAMENTE
    //EN CASO DE NECESITARLOS.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Movimiento> movimientos = new ArrayList<>();


    public void addMovimiento(Movimiento movimiento) {
        if (!this.movimientos.contains(movimiento)) {
            this.movimientos.add(movimiento);
            movimiento.setCuenta(this);
        }
    }

    public void removeMovimiento(Movimiento movimiento) {
        this.movimientos.remove(movimiento);
        movimiento.setCuenta(null);
    }


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
