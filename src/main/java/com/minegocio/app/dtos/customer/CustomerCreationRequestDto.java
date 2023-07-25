package com.minegocio.app.dtos.customer;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CustomerCreationRequestDto {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "RUC|CEDULA")
    private String identificationType;
    @NotNull
    @NotEmpty
    @Size(min = 9, max = 20)
    private String identificationNumber;
    @NotEmpty
    @NotNull
    private String names;
    @Email
    private String mail;
    @Size(min = 9, max = 20)
    private String cellphone;
}
