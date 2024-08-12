package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.IAM;
import com.appopay.aml.model.LoginReqDTO;
import com.appopay.aml.model.SignupReqDTO;
import com.appopay.aml.repository.IAMRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IAMService {

    @Autowired
    private IAMRepository iamRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(IAMService.class);

    public void SignUp(SignupReqDTO request) {
        IAM checkExistingUser = iamRepository.findByUserName(request.getUsername());
        if (checkExistingUser != null) {
            throw new CustomException("username already taken !");
        } else {
            log.info("creating account with username " + request.getUsername());
            IAM iam = new IAM();
            iam.setUserName(request.getUsername());
            iam.setPassword(passwordEncoder.encode(request.getPassword()));
            iam.setDesignation(request.getDesignation());
            iamRepository.save(iam);
        }
    }

    public void login(LoginReqDTO request) {
        IAM checkExistingUser = iamRepository.findByUserName(request.getUserName());
        if (checkExistingUser == null) {
            throw new CustomException("username not found !");
        } else if (!passwordEncoder.matches(request.getPassword(), checkExistingUser.getPassword())) {
            throw new CustomException("wrong password !");
        }
    }

}
