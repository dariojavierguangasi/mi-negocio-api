package com.minegocio.app.services;

import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationResponseDto;
import com.minegocio.app.dtos.customer.CustomerCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomersService {

    CustomerAndMainAddressCreationResponseDto createCustomerWithMainAddress(
            CustomerAndMainAddressCreationRequestDto requestDto);

    Page<CustomerAndMainAddressCreationResponseDto> findAllByFilters(
            CustomerFilterDto customerFilterDto,
            Pageable pageable
    );

    CustomerAndMainAddressCreationResponseDto updateCustomer(UUID id, CustomerCreationRequestDto requestDto);

    void delete(UUID id);
}
