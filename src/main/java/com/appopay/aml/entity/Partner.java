package com.appopay.aml.entity;

import com.appopay.aml.model.CustomersDTO;
import com.appopay.aml.model.PartnerDTO;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "partner")
@Data
@Entity
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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

    public Partner() {}

    public Partner(ValidateRiskRegReqDTO req, String riskScore, boolean isBlocked) {
        this.id = req.getId();
        this.name = req.getName();
        this.countryOfOrigin = req.getCountryOfOrigin();
        this.riskScore = riskScore;
        this.politicallyExposedPerson = req.isPoliticallyExposedPerson();
        this.isBlocked = isBlocked;
        this.identityType = req.getIdentityType();
        this.identityNumber = req.getIdentityNumber();
    }

    public PartnerDTO toDTO() {
        PartnerDTO response = new PartnerDTO();
        response.setId(this.id);
        response.setName(this.name);
        response.setCountryOfOrigin(this.countryOfOrigin);
        response.setRiskScore(this.riskScore);
        response.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        response.setIsBlocked(this.isBlocked);
        response.setIdentityType(this.identityType);
        response.setIdentityNumber(this.identityNumber);
        return response;
    }


}
