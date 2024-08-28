package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.PartnerRepository;
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

    public PartnerDTO createPartner(PartnerDTO partnerDTO) {
        Partner partner = null;
        if (partnerDTO.getId() != null) {
            throw new CustomException("new partner can not have ID");
        } else {
            CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountry(partnerDTO.getCountryOfOrigin());
            Long countryRisk = 0L;
            boolean isBlocked = false;
            if (countryRiskConfig != null)
                countryRisk = Long.valueOf(countryRiskConfig.getRiskScoreNationality());
            if (partnerDTO.getPoliticallyExposedPerson())
                partnerDTO.setRiskScore(String.valueOf(countryRisk + Constants.POLITICALLY_EXPOSED));
            else {
                partnerDTO.setRiskScore(String.valueOf(countryRisk));
            }
            if (Long.parseLong(partnerDTO.getRiskScore()) > Constants.ALLOWED_RISK)
                isBlocked = true;
            partner = new Partner(partnerDTO, isBlocked);
            return partnerRepository.save(partner).toDTO();
        }
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

        if (partnerDTO.getName() != null)
            partner.setName(partnerDTO.getName());
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
