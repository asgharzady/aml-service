package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.IAM;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.*;
import com.appopay.aml.repository.AgentRepository;
import com.appopay.aml.repository.CountryRiskConfigRepository;
import com.appopay.aml.repository.IAMRepository;
import com.appopay.aml.util.Constants;
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

    @Autowired
    private CountryRiskConfigRepository countryRiskConfigRepository;
    private static final Logger log = LoggerFactory.getLogger(AgentService.class);
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

        if (agentDTO.getName() != null)
            agent.setName(agentDTO.getName());
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

    public PaginatedAgent findAll(Pageable pageable) {
        PaginatedAgent response = new PaginatedAgent();
        response.setData(agentRepository.findAll(pageable).stream().map(Agent::toDTO).toList());
        response.setTotalDocuments(agentRepository.count());
        return response;
    }

    public ValidateRiskResDTO validateRegAccount(ValidateRiskRegReqDTO req) {
        Optional<Agent> optionalAgent = agentRepository.findById(req.getId());
        Agent agent = null;
        if (optionalAgent.isPresent()) {
            log.info("agent present " + req.getId());
            agent = optionalAgent.get();
            return new ValidateRiskResDTO(agent.getRiskScore(), !agent.isBlocked());

        } else {
            CountryRiskConfig countryRiskConfig = countryRiskConfigRepository.findByCountry(req.getCountryOfOrigin());
            Long countryRisk = 0L;
            Long risk;
            boolean isBlocked = false;
            if (countryRiskConfig != null)
                countryRisk = Long.valueOf(countryRiskConfig.getRiskScoreNationality());
            if (req.isPoliticallyExposedPerson())
                risk = (countryRisk + Constants.POLITICALLY_EXPOSED);
            else {
                risk = countryRisk;
            }
            if (risk > Constants.ALLOWED_RISK)
                isBlocked = true;
            agent = new Agent(req, risk.toString(), isBlocked);
            agentRepository.save(agent);
            return new ValidateRiskResDTO(agent.getRiskScore(), true);
        }

    }


}
