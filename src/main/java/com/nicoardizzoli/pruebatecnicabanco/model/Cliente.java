package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder //SUPERBUILDER ES PARA MAPEAR EN EL BUILDER LOS CAMPOS DE LA CLASE PADRE ABSTRACTA, LOS 2 TIENEN Q TENER EL SUPERBUILDER
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id")
@Table(name = "cliente", uniqueConstraints = {
        //esto es el equivalente a ponerle el unique en la @Column, nada mas que aca podemos elegir el nombre de la constraint
        @UniqueConstraint(name = "cliente_id_unique", columnNames = "cliente_id")
})
public class Cliente extends Persona implements Serializable {

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;

    @NotBlank
    private String contrasena;

    @NotNull
    private Boolean estado;

    //PARA HACER LA BIDIRECCIONALIDAD ACORDARSE DE HACER LOS ADD Y REMOVES, PARA QUE TAMBIEN ESTE EN CUENTA.
    @OneToMany(mappedBy = "titular", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Cuenta> cuentas = new ArrayList<>();



    public void addCuenta(Cuenta cuenta) {
        if (!this.getCuentas().contains(cuenta)) {
            this.getCuentas().add(cuenta);
            cuenta.setTitular(this);
        }
    }

    public void removeCuenta(Cuenta cuenta) {
        if (this.getCuentas().contains(cuenta)) {
            this.getCuentas().remove(cuenta);
            cuenta.setTitular(null);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cliente cliente = (Cliente) o;
        return getPersonaId() != null && Objects.equals(getPersonaId(), cliente.getPersonaId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
