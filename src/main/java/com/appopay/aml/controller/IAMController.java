package com.appopay.aml.controller;


import com.appopay.aml.entity.IAM;
import com.appopay.aml.model.*;
import com.appopay.aml.service.IAMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class IAMController {

    @Autowired
    private IAMService iamService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Void> SignUpUser(@RequestBody SignupReqDTO request ){
        iamService.SignUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginReqDTO request ){
        iamService.login(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<IAM> updateUser(@RequestBody SignupReqDTO request){
        return ResponseEntity.ok().body(iamService.updateUser(request));
    }

    @GetMapping("/findAll/{page}/{size}")
    public ResponseEntity<PaginatedUsers> getAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        return ResponseEntity.ok(iamService.getAllPaginsated(PageRequest.of(page,size)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<IAM> getByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(iamService.getByUsername(username));
    }


}
