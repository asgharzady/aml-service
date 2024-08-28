package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AgentDTO {

    Long id;
    String name;
    String countryOfOrigin;
    String riskStatus;
    String riskScore;
    Boolean politicallyExposedPerson;
    Boolean isBlocked;
    String identityType;
    String identityNumber;

    public Agent toEntity(){
        Agent agent = new Agent();
        agent.setId(this.id);
        agent.setName(this.name);
        agent.setCountryOfOrigin(this.countryOfOrigin);
        agent.setRiskScore(this.riskScore);
        agent.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        agent.setBlocked(this.isBlocked);
        agent.setIdentityType(this.identityType);
        agent.setIdentityNumber(this.identityNumber);

        return agent;
    }
}