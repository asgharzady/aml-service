package com.appopay.aml.entity;

import com.appopay.aml.model.CustomersDTO;
import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Table(name = "merchant")
@Data
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String legalName;
    private String tradeName;
    private String typeOfBusiness;
    private String startDate;
    private String country;
    private String telephone;
    private String email;
    private String ruc;
    private String legalRepName;
    private String license;
    private String title;
    private String dob;
    private String nationality;
    private String address;
    private String contactName;
    private String contactTelephone;
    private String contactEmail;
    private String monthlySales;
    private String transactionTypech;
    private String averageTicket;
    private String monthlyTransactions;
    private String commercialReferences;
    private String accountName;
    private String accountType;
    private String bankName;
    private String accountNumber;
    private String pepStatus;
    private String pepPosition;
    private String pepStartDate;
    private String pepEndDate;
    private String pepFamilyDetails;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
