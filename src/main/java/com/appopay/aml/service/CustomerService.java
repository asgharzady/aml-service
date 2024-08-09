package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.CustomerRepository;
import com.appopay.aml.repository.TransactionRepository;
import com.appopay.aml.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public List<Customers> findAll() {
        List<Customers> list = customerRepository.findAll();
        return list;
    }

    public Customers findById(Long id) {
        Optional<Customers> optionalCustomers = customerRepository.findById(id);
        if (optionalCustomers.isPresent())
            return optionalCustomers.get();
        else {
            throw new CustomException("customer not found");
        }
    }

    public ValidateRiskResDTO validateRegAccount(ValidateRiskRegReqDTO req) {

        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
        Customers customer = null;

        if (optionalCustomers.isPresent()) {
            System.out.println("customer present");
            customer = optionalCustomers.get();
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
            else {
                risk = countryRisk;
            }
            if (risk > Constants.ALLOWED_RISK)
                isBlocked = true;
            customer = new Customers(req, risk.toString(), isBlocked);
            customerRepository.save(customer);
            return new ValidateRiskResDTO(customer.getRiskScore(), !customer.isBlocked());
        }

    }

    public ValidateRiskResDTO validateVIPAccount(ValidateRiskVIPReqDTO req) throws Exception {
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
        Customers customer = null;


        if (optionalCustomers.isEmpty()) {
            throw new CustomException("customer not present");
        } else {
            customer = optionalCustomers.get();
            if (customer.getSourceOfIncome() == null) {
                customer.setSourceOfIncome(req.getSourceOfIncome());
                customerRepository.save(customer);
            }
            return new ValidateRiskResDTO(customer.getRiskScore(), !customer.isBlocked());
        }
    }

    public String blockbyCustomerId(Long customerId, boolean block) {
        Optional<Customers> optionalCustomers = customerRepository.findById(customerId);
        Customers customer = null;
        if (optionalCustomers.isEmpty()) {
            throw new CustomException("customer not present");
        } else {
            customer = optionalCustomers.get();
            customer.setBlocked(block);
            customerRepository.save(customer);
            return "block: " + block;
        }
    }

    public List<CustomerTrxResDTO> getTransactions(Long id) {
        Optional<Customers> optionalCustomers = customerRepository.findById(id);
        Customers customer = null;
        if (optionalCustomers.isEmpty()) {
            throw new CustomException("customer not found");
        } else {
            customer = optionalCustomers.get();
            System.out.println("customer.getId()");
            System.out.println(customer.getId());
            List<Transaction> transactions = transactionRepository.findAllByCustomers(customer);
            List<CustomerTrxResDTO> response = transactions.stream().map(Transaction::toDTO).toList();
            return response;
        }
    }
}
