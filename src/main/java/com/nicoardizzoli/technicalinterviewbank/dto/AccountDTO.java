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

    @NotNull(message = "Account type required")
    private AccountType accountType;

    @NotNull(message = "Starting balance required")
    private BigDecimal startingBalance;

    @NotNull(message = "State required")
    private Boolean state;

    @NotNull(message = "Withdraw limit required")
    private BigDecimal withdrawLimit;

    @NotBlank(message = "Holder required")
    private String customerId;

    @NotNull(message = "Balance required")
    private BigDecimal balance;
}
