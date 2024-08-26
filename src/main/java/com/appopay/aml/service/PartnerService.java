package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.model.PaginatedMerchant;
import com.appopay.aml.model.PaginatedPartner;
import com.appopay.aml.model.PartnerDTO;
import com.appopay.aml.repository.PartnerRepository;
import com.appopay.aml.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() != null) {
            throw new CustomException("new partner can not have ID");
        }
        return partnerRepository.save(partnerDTO.toEntity()).toDTO();
    }

    public PartnerDTO updateOne(PartnerDTO partnerDTO) {
        if (partnerDTO.getId() == null) {
            throw new CustomException("please enter an partner Id");
        }
        Optional<Partner> optionalPartner = partnerRepository.findById(partnerDTO.getId());

        if (optionalPartner.isEmpty()) {
            throw new CustomException("partner not found");
        }

        Partner partner = optionalPartner.get();

        if (partnerDTO.getCustomerName() != null)
            partner.setCustomerName(partnerDTO.getCustomerName());
        if (partnerDTO.getCountryOfOrigin() != null)
            partner.setCountryOfOrigin(partner.getCountryOfOrigin());
        if (partnerDTO.getRiskScore() != null)
            partner.setRiskScore(partner.getRiskScore());
        if (partnerDTO.getPoliticallyExposedPerson() != null)
            partner.setPoliticallyExposedPerson(partnerDTO.getPoliticallyExposedPerson());
        if (partnerDTO.getIsBlocked() != null)
            partner.setBlocked(partnerDTO.getIsBlocked());
        if (partnerDTO.getIdentityType() != null)
            partner.setIdentityType(partnerDTO.getIdentityType());
        if (partnerDTO.getIdentityNumber() != null)
            partner.setIdentityNumber(partnerDTO.getIdentityNumber());
        return partnerRepository.save(partner).toDTO();
    }

    public PartnerDTO getById(Long id){
        Optional<Partner> partner = partnerRepository.findById(id);
        if(partner.isPresent()){
            return partner.get().toDTO();
        }
        throw new CustomException("Partner not found");
    }

    public PaginatedPartner findAll(Pageable pageable) {
        PaginatedPartner response = new PaginatedPartner();
        response.setData(partnerRepository.findAll(pageable).stream().map(Partner::toDTO).toList());
        response.setTotalDocuments(partnerRepository.count());
        return response;
    }

}
