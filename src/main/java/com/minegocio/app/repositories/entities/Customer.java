package com.minegocio.app.repositories.entities;

import com.minegocio.app.repositories.enums.IdentificationType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column
    private IdentificationType identificationType;

    @Column
    private String identificationNumber;

    @Column
    private String names;

    @Column
    private String mail;

    @Column
    private String cellphone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

}
