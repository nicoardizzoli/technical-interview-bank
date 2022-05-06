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

@Getter
@Setter
@ToString
@SuperBuilder
//SUPERBUILDER ES PARA MAPEAR EN EL BUILDER LOS CAMPOS DE LA CLASE PADRE ABSTRACTA, LOS 2 TIENEN Q TENER EL SUPERBUILDER ASI FUNCA MAPSTRUCT
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "persona_id",foreignKey = @ForeignKey(name = "persona_id_cliente_id"))
@Table(name = "cliente", uniqueConstraints = {
        //esto es el equivalente a ponerle el unique en la @Column, nada mas que aca podemos elegir el nombre de la constraint
        @UniqueConstraint(name = "cliente_id_unique", columnNames = "cliente_id")})
@Entity(name = "Cliente") //ESTE NOMBRE ES EL QUE SE VA A USAR EN JPQL PARA LAS QUERYS, SI BIEN LO HACE AUTOMATICO ES UNA BUENA PRACTICA PONERLO.
public class Cliente extends Persona implements Serializable {

    //BUENA PRACTICA PONER EL COLUMNS EN TODOS..........
    // si en la base de datos nos piden algun tipo en especial como por ej TEXT o TIMESTAMP es el campo columnDefinition
    @Column(name = "cliente_id", nullable = false)
    private String clienteId;


    private String contrasena;


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

    public String getNombreCompleto() {
        return this.getNombre() + " " + this.getApellido();
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
