package com.appopay.aml.service.rule;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.entity.ruleConfig.RuleConditions;
import com.appopay.aml.model.TransactionDTO;
import com.appopay.aml.model.rule.RuleDTO;
import com.appopay.aml.repository.rule.ConditionLogicRepository;
import com.appopay.aml.repository.rule.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    private static final Logger log = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ConditionLogicRepository conditionLogicRepository;


    public Rule create(RuleDTO ruleDTO){
        if (ruleDTO.getId() != null) {
            throw new CustomException("new rule can not have ID");
        } else {
            return ruleRepository.save(ruleDTO.toEntity());
        }
    }
    public boolean checkValidity(TransactionDTO req) {
        List<Rule> rules = ruleRepository.findAllByIsActive(true);
        boolean isValid = true;
        for (Rule rule : rules) {
            long checkCount = getFlagCount(req, rule.getRuleConditions());
            boolean allRequired = conditionLogicRepository.findByRuleId(rule).isAllRequired();
            if (allRequired && checkCount == rule.getRuleConditions().size()) isValid = false;
            else if (!allRequired && checkCount > 0) {
                isValid = false;
            }
        }
        return isValid;
    }
    public long getFlagCount(TransactionDTO req, List<RuleConditions> ruleConditionsList) {
        long checkCount = 0;
        for (RuleConditions ruleCondition : ruleConditionsList) {
            if (ruleCondition.getField().equals("TRANSACTION_FREQUENCY")) {
            } else if (ruleCondition.getField().equals("TRANSACTION_AMOUNT")) {
                if (ruleCondition.getCheckConstraint().equals("GREATER_THAN")) {
                    if (Long.parseLong(req.getAmount()) > Long.parseLong(ruleCondition.getValue())) checkCount++;
                } else if (ruleCondition.getCheckConstraint().equals("LESS_THAN")) {
                    if (Long.parseLong(req.getAmount()) < Long.parseLong(ruleCondition.getValue())) checkCount++;
                } else if (ruleCondition.getCheckConstraint().equals("EQUALS")) {
                    if (Long.parseLong(req.getAmount()) == Long.parseLong(ruleCondition.getValue())) checkCount++;
                }

            } else if (ruleCondition.getField().equals("IP_LOCATION")) {
                if (ruleCondition.getCheckConstraint().equals("IN_COUNTRY")) {
                    if (req.getMerchantLocation().equals(ruleCondition.getValue())) checkCount++;
                }
            } else if (ruleCondition.getField().equals("ACCOUNT_STATUS")) {
            } else if (ruleCondition.getField().equals("DEVICE_ID")) {
            }
        }
        return checkCount;
    }


}
