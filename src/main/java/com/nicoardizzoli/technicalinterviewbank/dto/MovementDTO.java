package com.nicoardizzoli.technicalinterviewbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicoardizzoli.technicalinterviewbank.model.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovementDTO {

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime date;

    @NotNull(message = "Movement type required - DEPOSIT or WITHDRAW")
    private MovementType movementType;

    @NotNull(message = "Amount required")
    private BigDecimal amount;

    @NotNull(message = "Account number required")
    private Integer accountNumber;

}
