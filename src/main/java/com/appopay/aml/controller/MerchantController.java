package com.appopay.aml.controller;


import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchant")
public class MerchantController {


    @Autowired
    private MerchantService merchantService;
    @PostMapping(value = "/")
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO request) {
        return ResponseEntity.ok().body(merchantService.createMerchant(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<MerchantDTO> updateOne(@RequestBody MerchantDTO request) {
        return ResponseEntity.ok().body(merchantService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MerchantDTO> updateOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(merchantService.getById(id));
    }



}
