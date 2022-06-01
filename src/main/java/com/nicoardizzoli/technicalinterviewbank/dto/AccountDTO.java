package com.nicoardizzoli.technicalinterviewbank.dto;

import com.nicoardizzoli.technicalinterviewbank.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Long accountId;

    private Integer accountNumber;

    @NotNull(message = "Account type required")
    private AccountType accountType;

    @NotNull(message = "State required")
    private Boolean state;

    @NotNull(message = "Withdraw limit required")
    private BigDecimal withdrawLimit;


    @NotBlank(message = "Holder identification required")
    private String customerIdentification;

    @NotNull(message = "Balance required")
    private BigDecimal balance;
}
