package com.appopay.aml.controller;


import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.model.*;
import com.appopay.aml.service.CustomerService;
import com.appopay.aml.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "validateRegularAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileRegularAccount(@RequestBody ValidateRiskRegReqDTO request) {
        return ResponseEntity.ok().body(customerService.validateRegAccount(request));
    }

    @PostMapping(value = "validateVIPAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileVIPAccount(@RequestBody ValidateRiskVIPReqDTO request) throws Exception {
        return ResponseEntity.ok().body(customerService.validateVIPAccount(request));
    }

    @PostMapping(value = "record-trx")
    public ResponseEntity<RecordCustomerTrxResDTO> RecordCustomerTransactions(@RequestBody RecordCustomerTrxReqDTO request) {
        return ResponseEntity.ok().body(transactionService.recordTrx(request));
    }


    @PostMapping(value = "findAll")
    public ResponseEntity<List<Customers>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Customers> getCustomerByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @PutMapping(value = "block/{customerId}/{block}")
    public ResponseEntity<String> BlockCustomer(@PathVariable Long customerId, @PathVariable boolean block) {
        return ResponseEntity.ok().body(customerService.blockbyCustomerId(customerId, block));
    }

    @GetMapping(value = "aml/{id}")
    public ResponseEntity<CustomerAmlResDTO> getAMLData(@PathVariable Long id) {
        return ResponseEntity.ok().body(new CustomerAmlResDTO());
    }

    @GetMapping(value = "transactions/{id}")
    public ResponseEntity<List<CustomerTrxResDTO>> getTransactions(@PathVariable Long id) {

        return ResponseEntity.ok().body(customerService.getTransactions(id));
    }

}
