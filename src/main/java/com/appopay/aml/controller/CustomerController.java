package com.appopay.aml.controller;


import com.appopay.aml.entity.Customers;
import com.appopay.aml.model.*;
import com.appopay.aml.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "record-trx")
    public ResponseEntity<RecordCustomerTrxResDTO> RecordCustomerTransactions(@RequestBody RecordCustomerTrxReqDTO request ){
        return ResponseEntity.ok().body(new RecordCustomerTrxResDTO());
    }


    @PostMapping(value = "findAll")
    public ResponseEntity<List<Customers>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Customers> getCustomerByID(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findById(id));
    }

    @GetMapping(value = "{id}/{opName}")
    public ResponseEntity<BlockResDTO> BlockCustomer(@PathVariable Long id,@PathVariable String opName){
        return ResponseEntity.ok().body(new BlockResDTO());
    }

    @GetMapping(value = "aml/{id}")
    public ResponseEntity<CustomerAmlResDTO> getAMLData(@PathVariable Long id){
        return ResponseEntity.ok().body(new CustomerAmlResDTO());
    }

    @GetMapping(value = "transactions/{id}")
    public ResponseEntity<CustomerTrxResDTO> getTransactions(@PathVariable Long id){
        return ResponseEntity.ok().body(new CustomerTrxResDTO());
    }

}
