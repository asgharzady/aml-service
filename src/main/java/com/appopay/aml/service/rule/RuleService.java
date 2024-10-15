package com.appopay.aml.service.rule;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Transaction;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import com.appopay.aml.model.AgentDTO;
import com.appopay.aml.model.PaginatedAgent;
import com.appopay.aml.model.TransactionDTO;
import com.appopay.aml.repository.AgentRepository;
import com.appopay.aml.repository.rule.RuleRepository;
import com.appopay.aml.service.MerchantService;
import com.appopay.aml.util.RiskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    private static final Logger log = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleRepository ruleRepository;

    public void checkValidity(Transaction req){
        List<Rule> rules = ruleRepository.findAll();
        Rule rule = rules.get(0);
        RuleConditions ruleCondition = rule.getRuleConditions().get(0);

        if(ruleCondition.getField().equals("TRANSACTION_FREQUENCY")){
            if()
        }
    }

}
