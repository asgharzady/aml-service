package com.appopay.aml.model;

import lombok.Data;

@Data
public class ValidateRiskRegReqDTO {

    private String customerName;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private String customerId;
    private String identityType;
    private String identityNumber;

}
