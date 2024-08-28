package com.appopay.aml.model;

import lombok.Data;

@Data
public class ValidateRiskRegReqDTO {

    private String name;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private Long id;
    private String identityType;
    private String identityNumber;

}
