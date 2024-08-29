package com.appopay.aml.model;

import lombok.Data;

@Data
public class RecordCustomerTrxReqDTO {

    private String transactionAmount;
    private String transactionType;
    private String transactionDescription;
    private String customerId;
    private String merchantLocation;

}
