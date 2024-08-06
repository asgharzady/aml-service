package com.appopay.aml.model;

import lombok.Data;

@Data
public class ValidateRiskResDTO {

    private int riskScore;
    private boolean signupAllowed;
    private String rspCode;

}
