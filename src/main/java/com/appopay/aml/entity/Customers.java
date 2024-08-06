package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private String identityType;
    private String identityNumber;
    private Instant createdAt;
    private Instant updatedAt;


}
