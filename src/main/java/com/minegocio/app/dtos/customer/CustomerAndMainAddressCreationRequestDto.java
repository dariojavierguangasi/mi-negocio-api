package com.minegocio.app.dtos.customer;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CustomerAndMainAddressCreationRequestDto {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "RUC|CEDULA")
    private String identificationType;
    @NotNull
    @NotEmpty
    private String identificationNumber;
    @NotNull
    @NotEmpty
    private String names;
    @Email
    private String mail;
    @Size(min = 9, max = 20)
    private String cellphone;
    @NotNull
    @NotEmpty
    private String mainProvince;
    @NotNull
    @NotEmpty
    private String mainCity;
    @NotNull
    @NotEmpty
    private String mainAddress;
}
