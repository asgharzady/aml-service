package com.appopay.aml.model;

import lombok.Data;

@Data
public class RecordCustomerTrxResDTO {

    private int riskScore;
    private boolean transactionAllowed;
    private int rspCode;

}
