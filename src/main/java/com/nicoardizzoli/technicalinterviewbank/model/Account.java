package com.nicoardizzoli.technicalinterviewbank.model;

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
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(name = "account_number_unique", columnNames = "account_number")})
@Entity(name = "Account")
public class Account implements Serializable {

    @Id
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @Column(name = "account_id")
    private Long accountId;

    @SequenceGenerator(name = "account_number_seq", sequenceName = "account_number_seq", allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_number_seq")
    @Column(name = "account_number")
    private Integer accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "state", nullable = false)
    private Boolean state = false;

    @Column(name = "withdraw_limit", nullable = false)
    private BigDecimal withdrawLimit;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "customer_id", foreignKey = @ForeignKey(name = "customer_id_account_id_fk"))
    private Customer holder;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Movement> movements = new ArrayList<>();


    public void addMovement(Movement movement) {
        if (!this.movements.contains(movement)) {
            this.movements.add(movement);
            movement.setAccount(this);
        }
    }

    public void removeMovement(Movement movement) {
        this.movements.remove(movement);
        movement.setAccount(null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return accountId != null && Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
