package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.PartnerRepository;
import com.appopay.aml.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;
    private static final Logger log = LoggerFactory.getLogger(PartnerService.class);

    public Partner createPartner(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() != null) {
            throw new CustomException("new partner can not have ID");
        } else {
            return partnerRepository.save(partnerDTO.toEntity());
        }
    }

    public Partner updateOne(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() == null) {
            throw new CustomException("please enter an partner Id");
        }
        Optional<Partner> optionalPartner = partnerRepository.findById(partnerDTO.getId());

        if (optionalPartner.isEmpty()) {
            throw new CustomException("partner not found");
        }

        Partner partner = optionalPartner.get();

        if (partnerDTO.getCompRegName() != null)
            partner.setCompRegName(partnerDTO.getCompRegName());
        if (partnerDTO.getCompTradeName() != null)
            partner.setCompTradeName(partnerDTO.getCompTradeName());
        if (partnerDTO.getCompTaxNumber() != null)
            partner.setCompTaxNumber(partnerDTO.getCompTaxNumber());
        if (partnerDTO.getCompanyRegNumber() != null)
            partner.setCompanyRegNumber(partnerDTO.getCompanyRegNumber());
        if (partnerDTO.getCompRegCountry() != null)
            partner.setCompRegCountry(partnerDTO.getCompRegCountry());
        if (partnerDTO.getCompRegDate() != null)
            partner.setCompRegDate(partnerDTO.getCompRegDate());
        if (partnerDTO.getCompRegProvince() != null)
            partner.setCompRegProvince(partnerDTO.getCompRegProvince());
        if (partnerDTO.getCurrAddress() != null)
            partner.setCurrAddress(partnerDTO.toEntity().getCurrAddress());
        if (partnerDTO.getPhyAddress() != null)
            partner.setPhyAddress(partnerDTO.toEntity().getPhyAddress());
        if (partnerDTO.getPostAddress() != null)
            partner.setPostAddress(partnerDTO.toEntity().getPostAddress());
        if (partnerDTO.getMainPhoneNo() != null)
            partner.setMainPhoneNo(partnerDTO.getMainPhoneNo());
        if (partnerDTO.getSecPhoneNumber() != null)
            partner.setSecPhoneNumber(partnerDTO.getSecPhoneNumber());
        if (partnerDTO.getCompWebsite() != null)
            partner.setCompWebsite(partnerDTO.getCompWebsite());
        if (partnerDTO.getTradeNameWebsite() != null)
            partner.setTradeNameWebsite(partnerDTO.getTradeNameWebsite());
        if (partnerDTO.getIsListedOnSE() != null)
            partner.setIsListedOnSE(partnerDTO.getIsListedOnSE());
        if (partnerDTO.getExchangeName() != null)
            partner.setExchangeName(partnerDTO.getExchangeName());
        if (partnerDTO.getSymbolListed() != null)
            partner.setSymbolListed(partnerDTO.getSymbolListed());
        if (partnerDTO.getIsRegByFinEntity() != null)
            partner.setIsRegByFinEntity(partnerDTO.getIsRegByFinEntity());
        if (partnerDTO.getIsRegByFinSerRegulator() != null)
            partner.setIsRegByFinSerRegulator(partnerDTO.getIsRegByFinSerRegulator());
        if (partnerDTO.getFinEntity() != null)
            partner.setFinEntity(partnerDTO.getFinEntity());
        if (partnerDTO.getFinSerRegulatorName() != null)
            partner.setFinSerRegulatorName(partnerDTO.getFinSerRegulatorName());
        if (partnerDTO.getPrimPerContactName() != null)
            partner.setPrimPerContactName(partnerDTO.getPrimPerContactName());
        if (partnerDTO.getPrimPerEmail() != null)
            partner.setPrimPerEmail(partnerDTO.getPrimPerEmail());
        if (partnerDTO.getPrimPerPhoneNo() != null)
            partner.setPrimPerPhoneNo(partnerDTO.getPrimPerPhoneNo());
        if (partnerDTO.getPrimPerPosition() != null)
            partner.setPrimPerPosition(partnerDTO.getPrimPerPosition());
        if (partnerDTO.getPrimPerExtension() != null)
            partner.setPrimPerExtension(partnerDTO.getPrimPerExtension());
        if (partnerDTO.getAuthsignName() != null)
            partner.setAuthsignName(partnerDTO.getAuthsignName());
        if (partnerDTO.getAuthsignPosition() != null)
            partner.setAuthsignPosition(partnerDTO.getAuthsignPosition());
        if (partnerDTO.getFinancingBankName() != null)
            partner.setFinancingBankName(partnerDTO.getFinancingBankName());
        if (partnerDTO.getFinancingBankSwiftCode() != null)
            partner.setFinancingBankSwiftCode(partnerDTO.getFinancingBankSwiftCode());
        if (partnerDTO.getFundingAccountName() != null)
            partner.setFundingAccountName(partnerDTO.getFundingAccountName());
        if (partnerDTO.getFundingAccHolderRelation() != null)
            partner.setFundingAccHolderRelation(partnerDTO.getFundingAccHolderRelation());
        if (partnerDTO.getCurrencies() != null)
            partner.setCurrencies(partnerDTO.getCurrencies());
        if(partnerDTO.getBeneficialOweners() != null)
            partner.setBeneficialOweners(partnerDTO.toEntity().getBeneficialOweners());
        if(partnerDTO.getControlOweners() != null)
            partner.setControlOweners(partnerDTO.toEntity().getControlOweners());
        return partnerRepository.save(partner);
    }

    public Partner getById(Long id){
        Optional<Partner> partner = partnerRepository.findById(id);
        if(partner.isPresent()){
            return partner.get();
        }
        throw new CustomException("Partner not found");
    }

    public PaginatedPartner findAll(Pageable pageable) {
        PaginatedPartner response = new PaginatedPartner();
        response.setData(partnerRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(partnerRepository.count());
        return response;
    }


}
