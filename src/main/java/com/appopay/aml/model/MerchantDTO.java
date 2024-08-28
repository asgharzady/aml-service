package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Merchant;
import lombok.Data;

@Data
public class MerchantDTO {

    Long id;
    String name;
    String countryOfOrigin;
    String riskScore;
    Boolean politicallyExposedPerson;
    Boolean isBlocked;
    String identityType;
    String identityNumber;

    public Merchant toEntity(){
        Merchant merchant = new Merchant();
        merchant.setId(this.id);
        merchant.setName(this.name);
        merchant.setCountryOfOrigin(this.countryOfOrigin);
        merchant.setRiskScore(this.riskScore);
        merchant.setPoliticallyExposedPerson(this.politicallyExposedPerson);
        merchant.setBlocked(this.isBlocked);
        merchant.setIdentityType(this.identityType);
        merchant.setIdentityNumber(this.identityNumber);

        return merchant;
    }
}