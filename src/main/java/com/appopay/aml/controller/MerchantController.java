package com.appopay.aml.controller;


import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.model.PaginatedMerchant;
import com.appopay.aml.model.ValidateRiskRegReqDTO;
import com.appopay.aml.model.ValidateRiskResDTO;
import com.appopay.aml.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    @PostMapping(value = "/validateRegularAcc")
    public ResponseEntity<ValidateRiskResDTO> createRegularMerchant(@RequestBody ValidateRiskRegReqDTO request) {
        return ResponseEntity.ok().body(merchantService.validateRegAccount(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MerchantDTO> updateOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(merchantService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedMerchant> getAllMerchants(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(merchantService.findAll(PageRequest.of(page,size)));
    }



}
