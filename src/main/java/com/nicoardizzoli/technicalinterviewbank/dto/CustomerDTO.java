package com.nicoardizzoli.technicalinterviewbank.dto;

import com.nicoardizzoli.technicalinterviewbank.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private String customerId;

    @NotBlank(message = "name required")
    private String name;

    @NotBlank(message = "surname required")
    private String surname;

    @NotNull(message = "gender required")
    private Gender gender;

    @NotNull(message = "age required")
    private Integer age;

    @NotBlank(message = "identification required")
    private String identification;

    @NotBlank(message = "address requerido")
    private String address;

    @NotBlank(message = "phone number required")
    private String phoneNumber;

    @NotBlank(message = "password required")
    private String password;

    @NotNull(message = "state required")
    private Boolean state;

}

