package com.appopay.aml.model;

import lombok.Data;

@Data
public class RecordCustomerTrxReqDTO {

    private String transactionAmount;
    private String transactionType;
    private String transactionDescription;
    private Long customerId;
    private String merchantLocation;

}
