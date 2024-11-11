package com.appopay.aml.controller;


import com.appopay.aml.entity.Partner;
import com.appopay.aml.entity.Partner;
import com.appopay.aml.model.*;
import com.appopay.aml.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("partner")
public class PartnerController {


    @Autowired
    private PartnerService partnerService;
    @PostMapping(value = "/")
    public ResponseEntity<Partner> createPartner(@RequestBody PartnerDTO request) {
        return ResponseEntity.ok().body(partnerService.createPartner(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<Partner> updateOne(@RequestBody PartnerDTO request) {
        return ResponseEntity.ok().body(partnerService.updateOne(request));
    }
//
//    @PutMapping(value = "/block/{partnerId}/{block}")
//    public ResponseEntity<String> BlockMerchant(@PathVariable Long partnerId, @PathVariable boolean block) {
//        return ResponseEntity.ok().body(partnerService.blockbyId(partnerId, block));

    @GetMapping(value = "/{id}")
    public ResponseEntity<Partner> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(partnerService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedPartner> getAllPartners(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(partnerService.findAll(PageRequest.of(page,size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable("id") long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }
}
