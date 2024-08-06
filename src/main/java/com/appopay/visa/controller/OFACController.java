package com.appopay.visa.controller;

import com.appopay.visa.model.ChangeOfacRequestDTO;
import com.appopay.visa.model.PaginatedOfacDto;
import com.appopay.visa.model.VisaApiResponse;
import com.appopay.visa.service.OFACService;
import com.appopay.visa.service.VisaAuthAndEncryptionServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("visa/ofac/")
public class OFACController {

    private static final Logger log = LoggerFactory.getLogger(OFACController.class);

    @Autowired
    private OFACService ofacService;

    @GetMapping("{page}/{size}")
    public ResponseEntity<PaginatedOfacDto> getAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return ResponseEntity.ok().body(ofacService.getAll(PageRequest.of(page,size)));
    }

    @GetMapping(value = "status/{name}")
    public ResponseEntity<String> getStatus(@PathVariable String name) throws Exception {
        return ResponseEntity.ok().body(ofacService.getStatus(name));
    }

    @PutMapping(value = "status/{name}")
    public ResponseEntity<String> changeStatus(@RequestBody ChangeOfacRequestDTO request) throws Exception {
        return ResponseEntity.ok().body(ofacService.changeStatus(request));
    }


}