package com.appopay.aml.repository.rule;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.ruleConfig.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
