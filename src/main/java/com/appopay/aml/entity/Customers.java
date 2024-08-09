package com.appopay.aml.entity;

import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    private Long id;
    private String customerName;
    private String countryOfOrigin;
    private String riskScore;
    private boolean politicallyExposedPerson;
    private boolean isBlocked;
    private String sourceOfIncome;
    private String identityType;
    private String identityNumber;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public Customers() {
    }

    public Customers(ValidateRiskRegReqDTO req,String riskScore, boolean isBlocked) {
        this.id=req.getCustomerId();
        this.customerName = req.getCustomerName();
        this.countryOfOrigin = req.getCountryOfOrigin();
        this.riskScore = riskScore;
        this.politicallyExposedPerson = req.isPoliticallyExposedPerson();
        this.isBlocked = isBlocked;
        this.identityType = req.getIdentityType();
        this.identityNumber = req.getIdentityNumber();

    }


}
