package com.appopay.aml.controller;


import com.appopay.aml.entity.Agent;
import com.appopay.aml.model.*;
import com.appopay.aml.service.AgentService;
import com.appopay.aml.service.CustomerService;
import com.appopay.aml.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agent")
public class AgentController {


    @Autowired
    private AgentService agentService;
    @PostMapping(value = "/")
    public ResponseEntity<Agent> createAgent(@RequestBody AgentDTO request) {
        return ResponseEntity.ok().body(agentService.createAgent(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<Agent> updateOne(@RequestBody AgentDTO request) {
        return ResponseEntity.ok().body(agentService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Agent> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(agentService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedAgent> getAllAgents(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(agentService.findAll(PageRequest.of(page,size)));
    }



}
