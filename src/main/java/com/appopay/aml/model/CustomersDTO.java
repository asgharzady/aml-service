package com.appopay.aml.model;

import lombok.Data;

@Data
public class CustomersDTO {

    Long id;
    String customerName;
    String countryOfOrigin;
    String riskStatus;
    String riskScore;
    Boolean politicallyExposedPerson;
    Boolean isBlocked;
    String identityType;
    String identityNumber;

}
