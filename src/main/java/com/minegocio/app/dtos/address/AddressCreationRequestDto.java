package com.minegocio.app.dtos.address;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddressCreationRequestDto {

    @NotNull
    @NotEmpty
    private String province;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String addres;
    @NotNull
    @NotEmpty
    private String identificationNumber;

}
