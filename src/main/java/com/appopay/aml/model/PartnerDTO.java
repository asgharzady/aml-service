package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Partner;
import lombok.Data;

@Data
public class PartnerDTO {

    Long id;
    String customerName;
    String countryOfOrigin;
    String riskScore;
    Boolean politicallyExposedPerson;
    Boolean isBlocked;
    String identityType;
    String identityNumber;

    public Partner toEntity(){
        Partner partner = new Partner();
        partner.setId(this.id);
        partner.setCustomerName(this.customerName);
        partner.setCountryOfOrigin(this.countryOfOrigin);
        partner.setRiskScore(this.riskScore);
        partner.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        partner.setBlocked(this.isBlocked);
        partner.setIdentityType(this.identityType);
        partner.setIdentityNumber(this.identityNumber);

        return partner;
    }
}