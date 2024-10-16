package com.appopay.aml.model.rule;

import com.appopay.aml.entity.ruleConfig.RuleConditions;
import lombok.Data;

@Data
public class RuleConditionsDTO {

    Long id;
    String field;
    String checkConstraint;
    String value;

    public RuleConditions toEntity() {
        RuleConditions ruleConditions = new RuleConditions();
        ruleConditions.setId(this.id);
        ruleConditions.setField(this.field);
        ruleConditions.setCheckConstraint(this.checkConstraint);
        ruleConditions.setValue(this.value);
        return ruleConditions;
    }
}