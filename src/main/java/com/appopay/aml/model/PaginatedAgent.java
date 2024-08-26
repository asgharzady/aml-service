package com.appopay.aml.model;

import com.appopay.aml.entity.Customers;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedAgent {

    List<AgentDTO> data;
    Long totalDocuments;

}