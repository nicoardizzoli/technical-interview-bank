package com.nicoardizzoli.technicalinterviewbank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(name = "person_identification_unique", columnNames = "identification")})
@Entity(name = "Person")
public abstract class Person {

    @Id
    @SequenceGenerator(name = "person_id_seq", sequenceName = "person_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
    @Column(name = "person_id")
    private Long personId;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "surname", nullable = false)
    private String surname;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    @Column(name = "age", nullable = false)
    private Integer age;


    @Column(name = "identification", nullable = false)
    private String identification;


    @Column(name = "address", nullable = false)
    private String address;


    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return personId != null && Objects.equals(personId, person.personId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
