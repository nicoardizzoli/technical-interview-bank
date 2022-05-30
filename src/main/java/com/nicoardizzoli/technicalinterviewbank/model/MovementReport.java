package com.nicoardizzoli.technicalinterviewbank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovementReport {

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime date;
    private MovementType movementType;
    private String holderFullName;
    private Integer accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private Boolean state;
    private BigDecimal amount;
    private BigDecimal availableBalance;

}
