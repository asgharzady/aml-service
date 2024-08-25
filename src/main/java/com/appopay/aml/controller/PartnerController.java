package com.appopay.aml.controller;


import com.appopay.aml.model.PartnerDTO;
import com.appopay.aml.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("partner")
public class PartnerController {


    @Autowired
    private PartnerService partnerService;
    @PostMapping(value = "/")
    public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerDTO request) {
        return ResponseEntity.ok().body(partnerService.createPartner(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<PartnerDTO> updateOne(@RequestBody PartnerDTO request) {
        return ResponseEntity.ok().body(partnerService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PartnerDTO> updateOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(partnerService.getById(id));
    }



}
