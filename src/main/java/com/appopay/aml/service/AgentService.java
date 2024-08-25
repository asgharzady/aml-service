package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.IAM;
import com.appopay.aml.model.AgentDTO;
import com.appopay.aml.model.LoginReqDTO;
import com.appopay.aml.model.PaginatedUsers;
import com.appopay.aml.model.SignupReqDTO;
import com.appopay.aml.repository.AgentRepository;
import com.appopay.aml.repository.IAMRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public AgentDTO createAgent(AgentDTO agentDTO) {
        if (agentDTO.getId() != null) {
            throw new CustomException("new agent can not have ID");
        }
        return agentRepository.save(agentDTO.toEntity()).toDTO();
    }

    public AgentDTO updateOne(AgentDTO agentDTO) {
        if (agentDTO.getId() == null) {
            throw new CustomException("please enter an agent Id");
        }
        Optional<Agent> optionalAgent = agentRepository.findById(agentDTO.getId());

        if (optionalAgent.isEmpty()) {
            throw new CustomException("agent not found");
        }

        Agent agent = optionalAgent.get();

        if (agentDTO.getCustomerName() != null)
            agent.setCustomerName(agentDTO.getCustomerName());
        if (agentDTO.getCountryOfOrigin() != null)
            agent.setCountryOfOrigin(agent.getCountryOfOrigin());
        if (agentDTO.getRiskScore() != null)
            agent.setRiskScore(agent.getRiskScore());
        if (agentDTO.getPoliticallyExposedPerson() != null)
            agent.setPoliticallyExposedPerson(agentDTO.getPoliticallyExposedPerson());
        if (agentDTO.getIsBlocked() != null)
            agent.setBlocked(agentDTO.getIsBlocked());
        if (agentDTO.getIdentityType() != null)
            agent.setIdentityType(agentDTO.getIdentityType());
        if (agentDTO.getIdentityNumber() != null)
            agent.setIdentityNumber(agentDTO.getIdentityNumber());
        return agentRepository.save(agent).toDTO();
    }

    public AgentDTO getById(Long id){
        Optional<Agent> agent = agentRepository.findById(id);
        if(agent.isPresent()){
            return agent.get().toDTO();
        }
        throw new CustomException("Agent not found");
    }

}
