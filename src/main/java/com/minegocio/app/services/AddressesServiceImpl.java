package com.minegocio.app.services;

import com.minegocio.app.dtos.address.AddressCreationRequestDto;
import com.minegocio.app.dtos.address.AddressCreationResponseDto;
import com.minegocio.app.errors.exceptions.NotFoundException;
import com.minegocio.app.repositories.AddressRepository;
import com.minegocio.app.repositories.CustomerRepository;
import com.minegocio.app.repositories.entities.Address;
import com.minegocio.app.repositories.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
public class AddressesServiceImpl implements AddressesService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;


    public AddressesServiceImpl(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public AddressCreationResponseDto create(AddressCreationRequestDto requestDto) {

        Customer customer = customerRepository.findFirstByIdentificationNumber(requestDto.getIdentificationNumber())
                .orElseThrow(() -> new NotFoundException(format("No existe un cliente con el numero de identificacion {0}", requestDto.getIdentificationNumber())));

        Address address = toEntity(requestDto);
        address.setCustomer(customer);
        return toDto(addressRepository.save(address));
    }

    @Override
    public List<AddressCreationResponseDto> findAllByCustomer(UUID idCustomer) {

        List<Address> findAll = addressRepository.findAllByIdCustomer(idCustomer);

        return findAll
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Address toEntity(AddressCreationRequestDto requestDto) {
        Address address = new Address();
        address.setProvince(requestDto.getProvince());
        address.setCity(requestDto.getCity());
        address.setAddress(requestDto.getAddres());
        address.setIsMain(false);
        return address;
    }

    private AddressCreationResponseDto toDto(Address address) {
        AddressCreationResponseDto dto = new AddressCreationResponseDto();
        dto.setProvince(address.getProvince());
        dto.setCity(address.getCity());
        dto.setAddres(address.getAddress());
        dto.setId(address.getId());
        dto.setIdCustomer(address.getCustomer().getId());
        dto.setIdenticationTypeCustomer(address.getCustomer().getIdentificationType().name());
        dto.setIdenticationNumberCustomer(address.getCustomer().getIdentificationNumber());
        return dto;
    }
}
