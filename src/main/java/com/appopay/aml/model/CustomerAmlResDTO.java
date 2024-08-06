package com.appopay.aml.model;

import lombok.Data;

@Data
public class CustomerAmlResDTO {

    private String rspCode;
    private String riskScore;
    private String transactionRiskScore;


}
