package com.appopay.aml.model;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Merchant;
import lombok.Data;

@Data
public class MerchantDTO {

    Long id;
    String legalName;
    String tradeName;
    String typeOfBusiness;
    String startDate;
    String country;
    String telephone;
    String email;
    String ruc;
    String legalRepName;
    String license;
    String title;
    String dob;
    String nationality;
    String address;
    String contactName;
    String contactTelephone;
    String contactEmail;
    String monthlySales;
    String transactionTypech;
    String averageTicket;
    String monthlyTransactions;
    String commercialReferences;
    String accountName;
    String accountType;
    String bankName;
    String accountNumber;
    String pepStatus;
    String pepPosition;
    String pepStartDate;
    String pepEndDate;
    String pepFamilyDetails;

    public Merchant toEntity() {
        Merchant merchant = new Merchant();
        merchant.setLegalName(this.getLegalName());
        merchant.setTradeName(this.getTradeName());
        merchant.setTypeOfBusiness(this.getTypeOfBusiness());
        merchant.setStartDate(this.getStartDate());
        merchant.setCountry(this.getCountry());
        merchant.setTelephone(this.getTelephone());
        merchant.setEmail(this.getEmail());
        merchant.setRuc(this.getRuc());
        merchant.setLegalRepName(this.getLegalRepName());
        merchant.setLicense(this.getLicense());
        merchant.setTitle(this.getTitle());
        merchant.setDob(this.getDob());
        merchant.setNationality(this.getNationality());
        merchant.setAddress(this.getAddress());
        merchant.setContactName(this.getContactName());
        merchant.setContactTelephone(this.getContactTelephone());
        merchant.setContactEmail(this.getContactEmail());
        merchant.setMonthlySales(this.getMonthlySales());
        merchant.setTransactionTypech(this.getTransactionTypech());
        merchant.setAverageTicket(this.getAverageTicket());
        merchant.setMonthlyTransactions(this.getMonthlyTransactions());
        merchant.setCommercialReferences(this.getCommercialReferences());
        merchant.setAccountName(this.getAccountName());
        merchant.setAccountType(this.getAccountType());
        merchant.setBankName(this.getBankName());
        merchant.setAccountNumber(this.getAccountNumber());
        merchant.setPepStatus(this.getPepStatus());
        merchant.setPepPosition(this.getPepPosition());
        merchant.setPepStartDate(this.getPepStartDate());
        merchant.setPepEndDate(this.getPepEndDate());
        merchant.setPepFamilyDetails(this.getPepFamilyDetails());
        return merchant;
    }
}