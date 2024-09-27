package com.appopay.aml.entity;

import com.appopay.aml.model.CustomersDTO;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    private String id;
    private String customerName;
    private String countryOfOrigin;
    private String riskStatus;
    private String riskScore;
    private boolean politicallyExposedPerson;
    private boolean isBlocked;
    private String sourceOfIncome;
    private String identityType;
    private String identityNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_card")
    private IdCard idCard;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public Customers() {
    }

    public Customers(ValidateRiskRegReqDTO req, String riskScore, boolean isBlocked) {
        this.id = req.getId();
        this.customerName = req.getName();
        this.countryOfOrigin = req.getCountryOfOrigin();
        this.riskScore = riskScore;
        this.politicallyExposedPerson = req.isPoliticallyExposedPerson();
        this.isBlocked = isBlocked;
        this.identityType = req.getIdentityType();
        this.identityNumber = req.getIdentityNumber();
    }

    public CustomersDTO toDTO() {
        CustomersDTO response = new CustomersDTO();
        response.setId(this.id);
        response.setCustomerName(this.customerName);
        response.setCountryOfOrigin(this.countryOfOrigin);
        response.setRiskStatus(this.riskStatus);
        response.setRiskScore(this.riskScore);
        response.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        response.setIsBlocked(this.isBlocked);
        response.setIdentityType(this.identityType);
        response.setIdentityNumber(this.identityNumber);
        response.setSourceOfIncome(this.sourceOfIncome);
        return response;
    }


}
