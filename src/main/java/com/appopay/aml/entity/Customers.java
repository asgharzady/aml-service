package com.appopay.aml.entity;

import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String customerName;
    private String countryOfOrigin;
    private String riskScore;
    private boolean politicallyExposedPerson;
    private boolean isBlocked;

    private String sourceOfIncome;
    private String identityType;
    private String identityNumber;
    private Instant createdAt;
    private Instant updatedAt;

    public Customers() {
    }

    public Customers(ValidateRiskRegReqDTO req,String riskScore, boolean isBlocked) {
        this.customerId = req.getCustomerId();
        this.customerName = req.getCustomerName();
        this.countryOfOrigin = req.getCountryOfOrigin();
        this.riskScore = riskScore;
        this.politicallyExposedPerson = req.isPoliticallyExposedPerson();
        this.isBlocked = isBlocked;
        this.identityType = req.getIdentityType();
        this.identityNumber = req.getIdentityNumber();

    }


}
