package com.appopay.aml.controller;


import com.appopay.aml.model.*;
import com.appopay.aml.service.IAMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/")
public class IAMController {

    @Autowired
    private IAMService iamService;

    @PostMapping(value = "sign-up")
    public ResponseEntity<Void> SignUpUser(@RequestBody SignupReqDTO request ){
        iamService.SignUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginReqDTO request ){
        iamService.login(request);
        return ResponseEntity.ok().build();
    }


}
