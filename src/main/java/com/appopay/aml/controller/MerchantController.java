package com.appopay.aml.controller;


import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.MerchantDTO;
import com.appopay.aml.model.PaginatedMerchant;
import com.appopay.aml.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    private static final Logger log = LoggerFactory.getLogger(MerchantController.class);
    @Autowired
    private MerchantService merchantService;

    @PostMapping(value = "/")
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantDTO request) {
        log.info("creating merchant with name: "+ request.getAccountName());
        return ResponseEntity.ok().body(merchantService.createMerchant(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<Merchant> updateOne(@RequestBody MerchantDTO request) {
        log.info("updating merchant with name: "+ request.getAccountName());
        return ResponseEntity.ok().body(merchantService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Merchant> updateOne(@PathVariable("id") Long id) {
        log.info("retrieving merchant with id: "+ id);
        return ResponseEntity.ok().body(merchantService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedMerchant> getAllMerchants(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(merchantService.findAll(PageRequest.of(page, size)));
    }


}
