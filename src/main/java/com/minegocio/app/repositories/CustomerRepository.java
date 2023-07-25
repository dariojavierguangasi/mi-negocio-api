package com.minegocio.app.repositories;

import com.minegocio.app.repositories.entities.Customer;
import com.minegocio.app.repositories.enums.IdentificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM Customer c where c.identificationNumber = ?1")
    Optional<Customer> findFirstByIdentificationNumber(
            String identificacionNumber);

    @Query("SELECT c FROM Customer c where c.identificationNumber = :identificacionNumber and c.id != :id")
    Optional<Customer> findFirstByIdentificationNumberAndDiferentId(
            String identificacionNumber, UUID id);

    @Query(
            value = "SELECT c FROM Customer c " +
                    "WHERE (:identificacionNumber IS NULL OR LOWER(c.identificationNumber) LIKE LOWER(CONCAT('%', :identificacionNumber, '%'))) AND " +
                    "(:identificationType IS NULL OR c.identificationType = :identificationType ) AND " +
                    "(:names IS NULL OR LOWER(c.names) LIKE LOWER(CONCAT('%', :names, '%')))",
            countQuery = "SELECT COUNT(1) FROM Customer c " +
                    "WHERE (:identificacionNumber IS NULL OR LOWER(c.identificationNumber) LIKE LOWER(CONCAT('%', :identificacionNumber, '%'))) AND " +
                    "(:identificationType IS NULL OR c.identificationType = :identificationType ) AND " +
                    "(:names IS NULL OR LOWER(c.names) LIKE LOWER(CONCAT('%', :names, '%')))"
    )
    Page<Customer> findAllByFilters(
            String identificacionNumber,
            IdentificationType identificationType,
            String names,
            Pageable pageable
    );
}
