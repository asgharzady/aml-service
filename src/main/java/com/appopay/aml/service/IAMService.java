package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.IAM;
import com.appopay.aml.model.LoginReqDTO;
import com.appopay.aml.model.PaginatedUsers;
import com.appopay.aml.model.SignupReqDTO;
import com.appopay.aml.repository.IAMRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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
            iam.setBlocked(false);
            iamRepository.save(iam);
        }
    }

    public void login(LoginReqDTO request) {
        IAM checkExistingUser = iamRepository.findByUserName(request.getUserName());
        if (checkExistingUser == null) {
            log.info("username not found for user: " + request.getUserName());
            throw new CustomException("username not found !");
        } else if (!passwordEncoder.matches(request.getPassword(), checkExistingUser.getPassword())) {
            log.info("wrong password for user: " + request.getUserName());
            throw new CustomException("wrong password !");
        }
    }

    public IAM updateUser(SignupReqDTO request) {
        IAM checkExistingUser = iamRepository.findByUserName(request.getUsername());
        if (checkExistingUser == null) {
            throw new CustomException("username not found !");
        } else {
            if (request.getDesignation() != null) {
                checkExistingUser.setDesignation(request.getDesignation());
            }
            if (request.getIsBlocked() != null) {
                checkExistingUser.setBlocked(request.getIsBlocked());
            }
            if (request.getPassword() != null) {
                checkExistingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            iamRepository.save(checkExistingUser);
            return checkExistingUser;
        }
    }

    public PaginatedUsers getAllPaginsated(Pageable pageable){
        PaginatedUsers response = new PaginatedUsers();
        response.setData(iamRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(iamRepository.count());
        return response;
    }

    public IAM getByUsername(String username){
        IAM iam = iamRepository.findByUserName(username);

        if(iam == null){
            throw new CustomException("username not found");
        }

        return iam;
    }

}
