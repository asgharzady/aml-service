package com.appopay.aml.entity;

import com.appopay.aml.model.AgentDTO;
import com.appopay.aml.model.CustomersDTO;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "agent")
@Data
@Entity
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String countryOfOrigin;
    private String riskScore;
    private boolean politicallyExposedPerson;
    private boolean isBlocked;
    private String identityType;
    private String identityNumber;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public Agent() {
    }

    public AgentDTO toDTO() {
        AgentDTO response = new AgentDTO();
        response.setId(this.id);
        response.setCustomerName(this.customerName);
        response.setCountryOfOrigin(this.countryOfOrigin);
        response.setRiskScore(this.riskScore);
        response.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        response.setIsBlocked(this.isBlocked);
        response.setIdentityType(this.identityType);
        response.setIdentityNumber(this.identityNumber);
        return response;
    }


}