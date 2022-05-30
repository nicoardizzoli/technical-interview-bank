package com.nicoardizzoli.technicalinterviewbank.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id",foreignKey = @ForeignKey(name = "person_id_customer_id"))
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(name = "customer_id_unique", columnNames = "customer_id")})
@Entity(name = "Customer")
public class Customer extends Person implements Serializable {

    @Column(name = "customer_id", nullable = false)
    private String customerId;


    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Boolean state;

    @OneToMany(mappedBy = "holder", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();


    public void addAccount(Account account) {
        if (!this.getAccounts().contains(account)) {
            this.getAccounts().add(account);
            account.setHolder(this);
        }
    }

    public void removeAccount(Account account) {
        if (this.getAccounts().contains(account)) {
            this.getAccounts().remove(account);
            account.setHolder(null);
        }
    }

    public String getFullName() {
        return this.getName() + " " + this.getSurname();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getPersonId() != null && Objects.equals(getPersonId(), customer.getPersonId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
