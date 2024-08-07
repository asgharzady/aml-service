package com.appopay.aml.model;

import lombok.Data;

@Data
public class ValidateRiskVIPReqDTO {

    private String customerName;
    private String countryOfOrigin;
    private boolean politicallyExposedPerson;
    private String sourceOfIncome;
    private Long customerId;

}
