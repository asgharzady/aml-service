package com.appopay.aml.controller;


import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.model.AgentDTO;
import com.appopay.aml.model.TransactionDTO;
import com.appopay.aml.model.rule.RuleDTO;
import com.appopay.aml.service.rule.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/testTrx")
    public ResponseEntity<Boolean> testTrx(TransactionDTO transaction) {
        return ResponseEntity.ok(ruleService.checkValidity(transaction));
    }

    @PostMapping(value = "/")
    public ResponseEntity<Rule> createRule(@RequestBody RuleDTO ruleDTO) {
        //TODO ADD CONDITIONAL VALIDATION
        return ResponseEntity.ok().body(ruleService.create(ruleDTO));
    }
}