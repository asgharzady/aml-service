package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.MerchantRepository;
import com.appopay.aml.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;

    private static final Logger log = LoggerFactory.getLogger(MerchantService.class);

    public Merchant createMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() != null) {
            throw new CustomException("new agent can not have ID");
        } else {
            return merchantRepository.save(merchantDTO.toEntity());
        }
    }

    public Merchant updateOne(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() == null) {
            throw new CustomException("please enter an merchant Id");
        }
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantDTO.getId());

        if (optionalMerchant.isEmpty()) {
            throw new CustomException("merchant not found");
        }

        Merchant merchant = optionalMerchant.get();

        if(merchantDTO.getLegalName() != null)
            merchant.setLegalName(merchantDTO.getLegalName());
        if(merchantDTO.getTradeName() != null)
            merchant.setTradeName(merchantDTO.getTradeName());
        if(merchantDTO.getTypeOfBusiness() != null)
            merchant.setTypeOfBusiness(merchantDTO.getTypeOfBusiness());
        if(merchantDTO.getStartDate() != null)
            merchant.setStartDate(merchantDTO.getStartDate());
        if(merchantDTO.getCountry() != null)
            merchant.setCountry(merchantDTO.getCountry());
        if(merchantDTO.getTelephone() != null)
            merchant.setTelephone(merchantDTO.getTelephone());
        if(merchantDTO.getEmail() != null)
            merchant.setEmail(merchantDTO.getEmail());
        if(merchantDTO.getRuc() != null)
            merchant.setRuc(merchantDTO.getRuc());
        if(merchantDTO.getLegalRepName() != null)
            merchant.setLegalRepName(merchantDTO.getLegalRepName());
        if(merchantDTO.getLicense() != null)
            merchant.setLicense(merchantDTO.getLicense());
        if(merchantDTO.getTitle() != null)
            merchant.setTitle(merchantDTO.getTitle());
        if(merchantDTO.getDob() != null)
            merchant.setDob(merchantDTO.getDob());
        if(merchantDTO.getNationality() != null)
            merchant.setNationality(merchantDTO.getNationality());
        if(merchantDTO.getAddress() != null)
            merchant.setAddress(merchantDTO.getAddress());
        if(merchantDTO.getContactName() != null)
            merchant.setContactName(merchantDTO.getContactName());
        if(merchantDTO.getContactTelephone() != null)
            merchant.setContactTelephone(merchantDTO.getContactTelephone());
        if(merchantDTO.getContactEmail() != null)
            merchant.setContactEmail(merchantDTO.getContactEmail());
        if(merchantDTO.getMonthlySales() != null)
            merchant.setMonthlySales(merchantDTO.getMonthlySales());
        if(merchantDTO.getTransactionTypech() != null)
            merchant.setTransactionTypech(merchantDTO.getTransactionTypech());
        if(merchantDTO.getAverageTicket() != null)
            merchant.setAverageTicket(merchantDTO.getAverageTicket());
        if(merchantDTO.getMonthlyTransactions() != null)
            merchant.setMonthlyTransactions(merchantDTO.getMonthlyTransactions());
        if(merchantDTO.getCommercialReferences() != null)
            merchant.setCommercialReferences(merchantDTO.getCommercialReferences());
        if(merchantDTO.getAccountName() != null)
            merchant.setAccountName(merchantDTO.getAccountName());
        if(merchantDTO.getAccountType() != null)
            merchant.setAccountType(merchantDTO.getAccountType());
        if(merchantDTO.getBankName() != null)
            merchant.setBankName(merchantDTO.getBankName());
        if(merchantDTO.getAccountNumber() != null)
            merchant.setAccountNumber(merchantDTO.getAccountNumber());
        if(merchantDTO.getPepStatus() != null)
            merchant.setPepStatus(merchantDTO.getPepStatus());
        if(merchantDTO.getPepPosition() != null)
            merchant.setPepPosition(merchantDTO.getPepPosition());
        if(merchantDTO.getPepStartDate() != null)
            merchant.setPepStartDate(merchantDTO.getPepStartDate());
        if(merchantDTO.getPepEndDate() != null)
            merchant.setPepEndDate(merchantDTO.getPepEndDate());
        if(merchantDTO.getPepFamilyDetails() != null)
            merchant.setPepFamilyDetails(merchantDTO.getPepFamilyDetails());
        return merchantRepository.save(merchant);
    }

//    public String blockbyId(Long merchantId, boolean block) {
//        log.info("blocking agent with agent id " + merchantId);
//        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);
//        Merchant merchant = null;
//        if (optionalMerchant.isEmpty()) {
//            log.info("merchant not present with merchant id " + merchantId);
//            throw new CustomException("merchant not present");
//        } else {
//            merchant = optionalMerchant.get();
//            merchant.setBlocked(block);
//            merchantRepository.save(merchant);
//            return "block: " + block;
//        }
//    }

    public Merchant getById(Long id) {
        Optional<Merchant> merchant = merchantRepository.findById(id);
        if (merchant.isPresent()) {
            return merchant.get();
        }
        throw new CustomException("Merchant not found");
    }

    public PaginatedMerchant findAll(Pageable pageable) {
        PaginatedMerchant response = new PaginatedMerchant();
        response.setData(merchantRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(merchantRepository.count());
        return response;
    }

}
