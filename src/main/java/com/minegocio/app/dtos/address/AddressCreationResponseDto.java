package com.minegocio.app.dtos.address;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressCreationResponseDto {

    private UUID id;
    private String province;
    private String city;
    private String addres;
    private UUID idCustomer;
    private String identicationNumberCustomer;
    private String identicationTypeCustomer;

}
