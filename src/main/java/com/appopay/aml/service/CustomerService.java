package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.IdCard;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.CustomerRepository;
import com.appopay.aml.repository.IdRepository;
import com.appopay.aml.repository.TransactionRepository;
import com.appopay.aml.util.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IdRepository idRepository;
    @Autowired
    private S3Service s3Service;

    @Value("${s3Url}")
    private String baseUrl;


    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);


    public PaginatedCustomers findAll(Pageable pageable) {
        PaginatedCustomers response = new PaginatedCustomers();
        response.setData(customerRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(customerRepository.count());
        return response;
    }

    public CustomersDTO findById(String id) {
        Optional<Customers> optionalCustomers = customerRepository.findById(id);
        if (optionalCustomers.isPresent())
            return optionalCustomers.get().toDTO();
        else {
            throw new CustomException("customer not found");
        }
    }

    public ValidateRiskResDTO validateRegAccount(ValidateRiskRegReqDTO req) {
        log.info("validating reg account with customer id " + req.getId());
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getId());
        Customers customer = null;

        if (optionalCustomers.isPresent()) {
            log.info("customer present " + req.getId());
            customer = optionalCustomers.get();
            return new ValidateRiskResDTO(customer.getRiskScore(), !customer.isBlocked());

        } else {
            CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountryIgnoreCase(req.getCountryOfOrigin());
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
            customer = new Customers(req, risk.toString(), isBlocked);
            customerRepository.save(customer);
            return new ValidateRiskResDTO(customer.getRiskScore(), true);
        }

    }

    public ValidateRiskResDTO validateVIPAccount(ValidateRiskVIPReqDTO req) throws Exception {
        log.info("validating VIP account with customer id " + req.getCustomerId());
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getCustomerId());
        Customers customer = null;


        if (optionalCustomers.isEmpty()) {
            log.info("customer not present with customer id " + req.getCustomerId());
            throw new CustomException("customer not present");
        } else {
            customer = optionalCustomers.get();
            if (customer.getSourceOfIncome() == null) {
                customer.setSourceOfIncome(req.getSourceOfIncome());
                customerRepository.save(customer);
            }
            return new ValidateRiskResDTO(customer.getRiskScore(), true);
        }
    }

    public String blockbyCustomerId(String customerId, boolean block) {
        log.info("blocking customer with customer id " + customerId);
        Optional<Customers> optionalCustomers = customerRepository.findById(customerId);
        Customers customer = null;
        if (optionalCustomers.isEmpty()) {
            log.info("customer not present with customer id " + customerId);
            throw new CustomException("customer not present");
        } else {
            customer = optionalCustomers.get();
            customer.setBlocked(block);
            customerRepository.save(customer);
            return "block: " + block;
        }
    }

    public List<CustomerTrxResDTO> getTransactions(String id) {
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

    public CustomerAmlResDTO getAmlData(String id) {
        Optional<Customers> optionalCustomers = customerRepository.findById(id);
        if (optionalCustomers.isPresent()) {
            Customers customer = optionalCustomers.get();
            CustomerAmlResDTO response = new CustomerAmlResDTO();
            response.setRiskScore(customer.getRiskScore());
            response.setBlocked(customer.isBlocked());
            return response;
        } else {
            throw new CustomException("customer not found");
        }
    }

    public CustomersDTO updateCustomer(CustomersDTO req) {
        Optional<Customers> optionalCustomers = customerRepository.findById(req.getId());
        Customers customer = null;
        if (optionalCustomers.isEmpty()) {
            throw new CustomException("customer not found");
        } else {
            customer = optionalCustomers.get();
            if (req.getCustomerName() != null)
                customer.setCustomerName(req.getCustomerName());
            if (req.getCountryOfOrigin() != null)
                customer.setCountryOfOrigin(req.getCountryOfOrigin());
            if (req.getRiskStatus() != null)
                customer.setRiskStatus(req.getRiskStatus());
            if (req.getRiskScore() != null)
                customer.setRiskScore(req.getRiskScore());
            if (req.getPoliticallyExposedPerson() != null)
                customer.setPoliticallyExposedPerson(req.getPoliticallyExposedPerson());
            if (req.getIsBlocked() != null)
                customer.setBlocked(req.getIsBlocked());
            if (req.getIdentityType() != null)
                customer.setIdentityType(req.getIdentityType());
            if (req.getIdentityNumber() != null)
                customer.setIdentityNumber(req.getIdentityNumber());

            customerRepository.save(customer);
            return customer.toDTO();
        }
    }

    public List<String> uploadId(MultipartFile front, MultipartFile back, UploadIdDTO request) {
        Optional<Customers> optionalCustomers = customerRepository.findById(request.getCustomerId());
        if (optionalCustomers.isEmpty())
            throw new CustomException("customer not found");
        else {
            Customers customer = optionalCustomers.get();
            List<String> response = new ArrayList<>();
            IdCard idCard = new IdCard();

            String keyName = getFileHashName(front);
            s3Service.uploadFile(front, keyName);
            String url = baseUrl + keyName;
            idCard.setFrontIdUrl(url);
            response.add("frontUrl: " + url);

            keyName = getFileHashName(back);
            s3Service.uploadFile(back, keyName);
            url = baseUrl + keyName;
            idCard.setBackIdUrl(url);
            response.add("backUrl: " + url);

            idCard.setFirstName(request.getFirstName());
            idCard.setLastName(request.getLastName());
            idCard.setExpiryDate(request.getExpiryDate());
            customer.setIdCards(new ArrayList<>(List.of(idCard)));
            customerRepository.save(customer);

            return response;
        }
    }

    public String getFileHashName(MultipartFile file) {
        try {

            String hash = DigestUtils.md5DigestAsHex(file.getBytes());
            String originalFileName = file.getOriginalFilename();
            String extention = originalFileName.substring(originalFileName.lastIndexOf('.'));

            return hash + extention;
        } catch (IOException e) {
            throw new CustomException("not able to process file");
        }
    }


    public IdCard getIdCard(String customerId) {
        Optional<Customers> optionalCustomers = customerRepository.findById(customerId);
        if (optionalCustomers.isPresent()) {
            List<IdCard> idCardList = optionalCustomers.get().getIdCards();
            if (idCardList.size() == 0)
                throw new CustomException("id card not found");
            return idCardList.get(0);

        } else {
            throw new CustomException("customer not found");
        }
    }
}
