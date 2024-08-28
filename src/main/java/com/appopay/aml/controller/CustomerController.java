package com.appopay.aml.controller;


import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.IdCard;
import com.appopay.aml.model.*;
import com.appopay.aml.service.CustomerService;
import com.appopay.aml.service.S3Service;
import com.appopay.aml.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @PutMapping
    public ResponseEntity<CustomersDTO> updateOne(@RequestBody CustomersDTO customers) {
        return ResponseEntity.ok().body(customerService.updateCustomer(customers));
    }

    @PostMapping(value = "/validateRegularAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileRegularAccount(@RequestBody ValidateRiskRegReqDTO request) {
        return ResponseEntity.ok().body(customerService.validateRegAccount(request));
    }

    @PostMapping(value = "/validateVIPAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileVIPAccount(@RequestBody ValidateRiskVIPReqDTO request) throws Exception {
        return ResponseEntity.ok().body(customerService.validateVIPAccount(request));
    }

    @PostMapping(value = "/record-trx")
    public ResponseEntity<RecordCustomerTrxResDTO> RecordCustomerTransactions(@RequestBody RecordCustomerTrxReqDTO request) {
        return ResponseEntity.ok().body(transactionService.recordTrx(request));
    }


    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedCustomers> getAllCustomers(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(customerService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomersDTO> getCustomerByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @PutMapping(value = "/block/{customerId}/{block}")
    public ResponseEntity<String> BlockCustomer(@PathVariable Long customerId, @PathVariable boolean block) {
        return ResponseEntity.ok().body(customerService.blockbyCustomerId(customerId, block));
    }

    @GetMapping(value = "/aml/{id}")
    public ResponseEntity<CustomerAmlResDTO> getAMLData(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getAmlData(id));
    }

    @GetMapping(value = "/transactions/{id}")
    public ResponseEntity<List<CustomerTrxResDTO>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getTransactions(id));
    }


    @PostMapping(value = "/upload/{customerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(@ModelAttribute()  UploadIdDTO uploadIdDTO) {

        return ResponseEntity.ok(customerService.uploadId(uploadIdDTO.getFront(), uploadIdDTO.getBack(), uploadIdDTO));
    }

    @GetMapping(value = "idCard/{customerId}")
    public ResponseEntity<IdCard> getIdCard(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.getIdCard(customerId));
    }
}
