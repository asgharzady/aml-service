package com.appopay.aml.controller;


import com.appopay.aml.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/")
public class IAMController {

    @PostMapping(value = "sign-up")
    public ResponseEntity<SignupResDTO> SignUpUser(@RequestBody SignupReqDTO request ){
        return ResponseEntity.ok().body(new SignupResDTO());
    }

    @PostMapping(value = "login")
    public ResponseEntity<SignupResDTO> loginUser(@RequestBody LoginReqDTO request ){
        return ResponseEntity.ok().body(new SignupResDTO());
    }


}
