package com.minegocio.app.repositories;

import com.minegocio.app.repositories.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query(value = "select a from Address a where a.customer.id = :idCustomer")
    List<Address> findAllByIdCustomer(UUID idCustomer);
}
