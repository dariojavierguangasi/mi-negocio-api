package com.minegocio.app.services;

import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerAndMainAddressCreationResponseDto;
import com.minegocio.app.dtos.customer.CustomerCreationRequestDto;
import com.minegocio.app.dtos.customer.CustomerFilterDto;
import com.minegocio.app.errors.exceptions.AlreadyExistsException;
import com.minegocio.app.errors.exceptions.NotFoundException;
import com.minegocio.app.repositories.AddressRepository;
import com.minegocio.app.repositories.CustomerRepository;
import com.minegocio.app.repositories.entities.Address;
import com.minegocio.app.repositories.entities.Customer;
import com.minegocio.app.repositories.enums.IdentificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomersServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public CustomerAndMainAddressCreationResponseDto createCustomerWithMainAddress(
            CustomerAndMainAddressCreationRequestDto requestDto) {
        Optional<Customer> existsCustomer =
                customerRepository.findFirstByIdentificationNumber(requestDto.getIdentificationNumber());

        existsCustomer.ifPresent(customer -> {
            throw new AlreadyExistsException(format("Ya existe un cliente con el numero de identificacion {0}", customer.getIdentificationNumber()));
        });

        Customer customer = customerRepository.save(toEntity(requestDto));
        Address address = addressRepository.save(toEntity(requestDto, customer));
        return toDto(customer, address);
    }

    @Override
    public Page<CustomerAndMainAddressCreationResponseDto> findAllByFilters(
            CustomerFilterDto customerFilterDto, Pageable pageable) {

        IdentificationType identificationType =
                customerFilterDto.getIdentificationType() != null ? IdentificationType.valueOf(customerFilterDto.getIdentificationType().toUpperCase()) : null;

        Page<Customer> customerPage = customerRepository.findAllByFilters(
                customerFilterDto.getIdentificationNumber(),
                identificationType,
                customerFilterDto.getNames(),
                pageable
        );

        return new PageImpl<>(
                customerPage.stream().map(this::toDto).collect(Collectors.toList()),
                customerPage.getPageable(),
                customerPage.getTotalElements()
        );
    }

    @Override
    public CustomerAndMainAddressCreationResponseDto updateCustomer(UUID id, CustomerCreationRequestDto requestDto) {
        Customer customer = findFirstById(id);
        Optional<Customer> existsCustomer =
                customerRepository.findFirstByIdentificationNumberAndDiferentId(requestDto.getIdentificationNumber(), id);
        existsCustomer.ifPresent(c -> {
            throw new AlreadyExistsException(format("Ya existe un cliente con el numero de identificacion {0}", c.getIdentificationNumber()));
        });
        customer.setIdentificationType(IdentificationType.valueOf(requestDto.getIdentificationType()));
        customer.setIdentificationNumber(requestDto.getIdentificationNumber());
        customer.setNames(requestDto.getNames());
        customer.setMail(requestDto.getMail());
        customer.setCellphone(requestDto.getCellphone());
        return toDto(customerRepository.save(customer));
    }

    @Override
    public void delete(UUID id) {
        Customer customer = findFirstById(id);
        customerRepository.delete(customer);
    }

    private Customer findFirstById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(format("No existe un cliente con id {0}", id)));
    }

    private Customer toEntity(CustomerAndMainAddressCreationRequestDto requestDto) {
        Customer customer = new Customer();
        customer.setIdentificationType(IdentificationType.valueOf(requestDto.getIdentificationType()));
        customer.setIdentificationNumber(requestDto.getIdentificationNumber());
        customer.setNames(requestDto.getNames());
        customer.setMail(requestDto.getMail());
        customer.setCellphone(requestDto.getCellphone());
        return customer;
    }

    private Address toEntity(CustomerAndMainAddressCreationRequestDto requestDto, Customer customer) {
        Address address = new Address();
        address.setProvince(requestDto.getMainProvince());
        address.setCity(requestDto.getMainCity());
        address.setAddress(requestDto.getMainAddress());
        address.setIsMain(true);
        address.setCustomer(customer);
        return address;
    }

    private CustomerAndMainAddressCreationResponseDto toDto(Customer customer) {
        CustomerAndMainAddressCreationResponseDto responseDto = new CustomerAndMainAddressCreationResponseDto();
        responseDto.setId(customer.getId());
        responseDto.setIdentificationType(customer.getIdentificationType().name());
        responseDto.setIdentificationNumber(customer.getIdentificationNumber());
        responseDto.setNames(customer.getNames());
        responseDto.setMail(customer.getMail());
        responseDto.setCellphone(customer.getCellphone());

        Address address = customer.getAddresses()
                .stream()
                .filter(Address::getIsMain)
                .findFirst()
                .orElse(null);

        if (address != null) {
            responseDto.setAddressId(address.getId());
            responseDto.setMainProvince(address.getProvince());
            responseDto.setMainCity(address.getCity());
            responseDto.setMainAddress(address.getAddress());
        }

        return responseDto;
    }

    private CustomerAndMainAddressCreationResponseDto toDto(Customer customer, Address address) {
        CustomerAndMainAddressCreationResponseDto responseDto = new CustomerAndMainAddressCreationResponseDto();
        responseDto.setId(customer.getId());
        responseDto.setIdentificationType(customer.getIdentificationType().name());
        responseDto.setIdentificationNumber(customer.getIdentificationNumber());
        responseDto.setNames(customer.getNames());
        responseDto.setMail(customer.getMail());
        responseDto.setCellphone(customer.getCellphone());
        responseDto.setAddressId(address.getId());
        responseDto.setMainProvince(address.getProvince());
        responseDto.setMainCity(address.getCity());
        responseDto.setMainAddress(address.getAddress());
        return responseDto;
    }

}
