package com.appopay.aml.model;

import com.appopay.aml.entity.Customers;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedMerchant {

    List<MerchantDTO> data;
    Long totalDocuments;

}
