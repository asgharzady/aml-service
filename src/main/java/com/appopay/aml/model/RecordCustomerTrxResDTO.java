package com.appopay.aml.model;

import lombok.Data;

@Data
public class RecordCustomerTrxResDTO {

    private String riskScore;
    private boolean transactionAllowed;

    public RecordCustomerTrxResDTO(String riskScore, boolean transactionAllowed) {
        this.riskScore = riskScore;
        this.transactionAllowed = transactionAllowed;
    }
}
