package com.nicoardizzoli.pruebatecnicabanco.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity(name = "Persona")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

    @Id
    @SequenceGenerator(name = "persona_id_seq", sequenceName = "persona_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_id_seq")
    @Column(name = "persona_id")
    private Long personaId;


    @Column(name = "nombre", nullable = false) //ESTAS COMPROBACIONES SON PARA LA CREACION DE LA TABLA
    private String nombre;


    private String apellido;


    @Enumerated(value = EnumType.STRING)
    private Genero genero;


    private Integer edad;


    private String identificacion;


    private String direccion;


    private String telefono;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Persona persona = (Persona) o;
        return personaId != null && Objects.equals(personaId, persona.personaId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
