package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

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

        if (merchantDTO.getCustomerName() != null)
            merchant.setCustomerName(merchantDTO.getCustomerName());
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

}
