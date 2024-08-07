package com.appopay.aml.service;

import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import com.appopay.aml.model.ValidateRiskResDTO;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.CustomerRepository;
import com.appopay.aml.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;


    public List<Customers> findAll() {
        List<Customers> list = customerRepository.findAll();
        return list;
    }

    public Customers findById(Long id) {
        Optional<Customers> customer = customerRepository.findById(id);
        if (customer.isPresent())
            return customer.get();

        else {
            throw new RuntimeException();
        }
    }

    public ValidateRiskResDTO validateRegAccount(ValidateRiskRegReqDTO req) {
        Customers customer = customerRepository.findByCustomerId(req.getCustomerId());

        if (customer != null) {
            System.out.println("customer present");
            return new ValidateRiskResDTO(customer.getRiskScore(), !customer.isBlocked());

        } else {
            CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountry(req.getCountryOfOrigin());
            Long countryRisk = 0L;
            Long risk;
            boolean isBlocked = false;
            if (countryRiskConfig != null)
                countryRisk = Long.valueOf(countryRiskConfig.getRiskScore());
            if (req.isPoliticallyExposedPerson())
                risk = (countryRisk + Constants.POLITICALLY_EXPOSED) / 2;
            else{
                risk = countryRisk;
            }
            if (risk > Constants.ALLOWED_RISK)
                isBlocked = true;
            customer = new Customers(req, risk.toString(), isBlocked);
            customerRepository.save(customer);
            return new ValidateRiskResDTO(customer.getRiskScore(), !customer.isBlocked());
        }

    }
}
