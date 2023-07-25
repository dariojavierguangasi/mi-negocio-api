package com.minegocio.app.controllers;

import com.minegocio.app.dtos.address.AddressCreationRequestDto;
import com.minegocio.app.dtos.address.AddressCreationResponseDto;
import com.minegocio.app.services.AddressesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "api/v1.0/addresses")
public class AddressesController {

    private final AddressesService addressesService;

    public AddressesController(AddressesService addressesService) {
        this.addressesService = addressesService;
    }

    @PostMapping("new-by-customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AddressCreationResponseDto create(@Valid @RequestBody AddressCreationRequestDto requestDto) {
        return addressesService.create(requestDto);
    }

    @GetMapping("{idCustomer}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AddressCreationResponseDto> findAllByCustomer(@PathVariable("idCustomer") UUID idCustomer) {
        return addressesService.findAllByCustomer(idCustomer);
    }

}
