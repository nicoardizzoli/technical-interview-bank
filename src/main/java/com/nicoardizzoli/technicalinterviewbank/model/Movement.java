package com.nicoardizzoli.technicalinterviewbank.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Movement")
public class Movement implements Serializable {

    @Id
    @SequenceGenerator(name = "movement_id_seq", sequenceName = "movement_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movement_id_seq")
    private Long movementId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;


    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type")
    private MovementType movementType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "initial_account_balance", nullable = false)
    private BigDecimal initialAccountBalance;

    @Column(name = "available_balance", nullable = false)
    private BigDecimal availableBalance;
    @ManyToOne()
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "account_id", foreignKey = @ForeignKey(name = "movement_id_account_id_fk"))
    @ToString.Exclude
    private Account account;



    public BigDecimal getAccountBalance(){
        return this.getAccount().getBalance();
    }

    public BigDecimal getWithdrawLimit(){
        return this.getAccount().getWithdrawLimit();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movement that = (Movement) o;
        return movementId != null && Objects.equals(movementId, that.movementId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
