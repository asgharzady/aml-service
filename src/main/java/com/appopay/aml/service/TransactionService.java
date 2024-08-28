package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.entity.TransactionRiskConfig;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.CustomerRepository;
import com.appopay.aml.repository.TransactionRepository;
import com.appopay.aml.repository.TransactionRiskConfigRepository;
import com.appopay.aml.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRiskConfigRepository transactionRiskConfigRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public RecordCustomerTrxResDTO recordTrx(RecordCustomerTrxReqDTO req) {
        log.info("transaction received with cid " + req.getCustomerId());
        boolean isAllowed = true;
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
        Customers customers = null;
        if(optionalCustomers.isPresent())
            customers = optionalCustomers.get();
        else{
            throw  new CustomException("customer not found");
        }
        TransactionRiskConfig transactionRiskConfig = transactionRiskConfigRepository.findAll().get(0);
        LocalDate now = LocalDate.now();
        Instant startOfMonth = now.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<Transaction> monthlyTransactions = transactionRepository.findAllByCustomersAndCreatedAtAfter(customers, startOfMonth);
        List<Transaction> dailyTransactions = transactionRepository.findAllByCustomersAndCreatedAtAfter(customers, startOfToday);

        Long monthlyAmount = monthlyTransactions.stream().mapToLong(n -> Long.parseLong(n.getAmount())).sum();
        Long dailyAmount = dailyTransactions.stream().mapToLong(n -> Long.parseLong(n.getAmount())).sum();

        CountryRiskConfig merchantCountryScore = countryRiskConfigRepository.findByCountryIgnoreCase(req.getMerchantLocation());
        Long riskScore = 0l;
        if(merchantCountryScore != null)
            riskScore = Long.valueOf(merchantCountryScore.getRiskScoreGeography());
        if(monthlyAmount + Long.parseLong(req.getTransactionAmount()) >transactionRiskConfig.getMonthlyLimit())
            isAllowed = false;

        else if(dailyAmount + Long.parseLong(req.getTransactionAmount()) > transactionRiskConfig.getDailyLimit())
            isAllowed = false;

        else if(monthlyTransactions.size() + 1  > transactionRiskConfig.getMonthlyFrequency())
            isAllowed = false;

        else if(!req.getMerchantLocation().equals(customers.getCountryOfOrigin()))
            isAllowed = false;

        else if(riskScore > Constants.ALLOWED_RISK)
            isAllowed = false;


        boolean isFlagged = !isAllowed;
        Transaction transaction = new Transaction(req, customers,isFlagged,riskScore.toString());

        transactionRepository.save(transaction);

        return new RecordCustomerTrxResDTO(riskScore.toString(),true);

    }

    public PaginatedTransactions getAll(Pageable pageable){
        PaginatedTransactions response = new PaginatedTransactions();
        response.setData(transactionRepository.findAll(pageable).stream().collect(Collectors.toList()));
        response.setDocuments(transactionRepository.count());
        return response;
    }
}
