package com.appopay.aml.controller;


import com.appopay.aml.model.ValidateRiskRegReqDTO;
import com.appopay.aml.model.ValidateRiskResDTO;
import com.appopay.aml.model.ValidateRiskVIPReqDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("risk/")
public class RiskController {




    @PostMapping(value = "validateVIPAcc")
    public ResponseEntity<ValidateRiskResDTO> ValidateCustomerRiskProfileVIPAccount(@RequestBody ValidateRiskVIPReqDTO request ){
        return ResponseEntity.ok().body(new ValidateRiskResDTO());
    }
}
