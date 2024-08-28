package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.CountryRiskConfig;
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

    public MerchantDTO createMerchant(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() != null) {
            throw new CustomException("new merchant can not have ID");
        }
        return merchantRepository.save(merchantDTO.toEntity()).toDTO();
    }

    public MerchantDTO updateOne(MerchantDTO merchantDTO) {
        if (merchantDTO.getId() == null) {
            throw new CustomException("please enter an merchant Id");
        }
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantDTO.getId());

        if (optionalMerchant.isEmpty()) {
            throw new CustomException("merchant not found");
        }

        Merchant merchant = optionalMerchant.get();

        if (merchantDTO.getName() != null)
            merchant.setName(merchantDTO.getName());
        if (merchantDTO.getCountryOfOrigin() != null)
            merchant.setCountryOfOrigin(merchant.getCountryOfOrigin());
        if (merchantDTO.getRiskScore() != null)
            merchant.setRiskScore(merchant.getRiskScore());
        if (merchantDTO.getPoliticallyExposedPerson() != null)
            merchant.setPoliticallyExposedPerson(merchantDTO.getPoliticallyExposedPerson());
        if (merchantDTO.getIsBlocked() != null)
            merchant.setBlocked(merchantDTO.getIsBlocked());
        if (merchantDTO.getIdentityType() != null)
            merchant.setIdentityType(merchantDTO.getIdentityType());
        if (merchantDTO.getIdentityNumber() != null)
            merchant.setIdentityNumber(merchantDTO.getIdentityNumber());
        return merchantRepository.save(merchant).toDTO();
    }

    public MerchantDTO getById(Long id){
        Optional<Merchant> merchant = merchantRepository.findById(id);
        if(merchant.isPresent()){
            return merchant.get().toDTO();
        }
        throw new CustomException("Merchant not found");
    }

    public PaginatedMerchant findAll(Pageable pageable) {
        PaginatedMerchant response = new PaginatedMerchant();
        response.setData(merchantRepository.findAll(pageable).stream().map(Merchant::toDTO).toList());
        response.setTotalDocuments(merchantRepository.count());
        return response;
    }

    public ValidateRiskResDTO validateRegAccount(ValidateRiskRegReqDTO req) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(req.getId());
        Merchant merchant = null;
        if (optionalMerchant.isPresent()) {
            log.info("merchant present " + req.getId());
            merchant = optionalMerchant.get();
            return new ValidateRiskResDTO(merchant.getRiskScore(), !merchant.isBlocked());

        } else {
            CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountry(req.getCountryOfOrigin());
            Long countryRisk = 0L;
            Long risk;
            boolean isBlocked = false;
            if (countryRiskConfig != null)
                countryRisk = Long.valueOf(countryRiskConfig.getRiskScoreNationality());
            if (req.isPoliticallyExposedPerson())
                risk = (countryRisk + Constants.POLITICALLY_EXPOSED);
            else {
                risk = countryRisk;
            }
            if (risk > Constants.ALLOWED_RISK)
                isBlocked = true;
            merchant = new Merchant(req, risk.toString(), isBlocked);
            merchantRepository.save(merchant);
            return new ValidateRiskResDTO(merchant.getRiskScore(), true);
        }

    }

}
