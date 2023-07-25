package com.minegocio.app.services;

import com.minegocio.app.dtos.address.AddressCreationRequestDto;
import com.minegocio.app.dtos.address.AddressCreationResponseDto;

import java.util.List;
import java.util.UUID;

public interface AddressesService {

    AddressCreationResponseDto create(AddressCreationRequestDto requestDto);

    List<AddressCreationResponseDto> findAllByCustomer(UUID idCustomer);
}
