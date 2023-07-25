package com.minegocio.app.dtos.customer;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerCreationResponseDto {
    private UUID id;
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String mail;
    private String cellphone;
}
