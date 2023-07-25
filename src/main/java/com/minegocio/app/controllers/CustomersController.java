package com.minegocio.app.controllers;

import com.minegocio.app.annotations.CustomerListFilter;
import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationResponseDto;
import com.minegocio.app.dtos.customer.CustomerCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerFilterDto;
import com.minegocio.app.services.CustomersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "api/v1.0/customers")
public class CustomersController {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("new-with-main-address")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerAndMainAddressCreationResponseDto createCustomerWithMainAddres(
            @Valid @RequestBody CustomerAndMainAddressCreationRequestDto requestDto) {
        return customersService.createCustomerWithMainAddress(requestDto);
    }

    @CustomerListFilter
    @GetMapping("all-filters")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<CustomerAndMainAddressCreationResponseDto> getAllByFilters(
            CustomerFilterDto customerFilterDto, Pageable pageable) {
        log.info("CustomerFilterDto {}", customerFilterDto);
        return customersService.findAllByFilters(customerFilterDto, pageable);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CustomerAndMainAddressCreationResponseDto update(@PathVariable("id") UUID id, @RequestBody CustomerCreationRequestDto requestDto) {
        return customersService.updateCustomer(id, requestDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id") UUID id) {
        log.info("REST to delete by id {}", id);
        customersService.delete(id);
    }

}
