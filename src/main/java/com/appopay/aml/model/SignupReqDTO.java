package com.appopay.aml.model;

import lombok.Data;

@Data
public class SignupReqDTO {

    private String username;
    private String userId;
    private String password;
    private String designation;
    private String timestamp;


}
