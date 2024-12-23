package com.appopay.aml.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckDeviceStatusResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;


}
