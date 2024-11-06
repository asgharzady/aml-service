package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import com.appopay.aml.entity.Agent;
import com.appopay.aml.model.AgentDTO;
import com.appopay.aml.model.PaginatedAgent;
import com.appopay.aml.repository.AgentRepository;
import com.appopay.aml.util.RiskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentService {

    private static final Logger log = LoggerFactory.getLogger(AgentService.class);
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private MerchantService merchantService;

    public Agent createAgent(AgentDTO agentDTO) {
        if (agentDTO.getId() != null) {
            throw new CustomException("new agent can not have ID");
        } else {
            Agent agent = agentDTO.toEntity();
            agent.setRisk(merchantService.getRisk(agentDTO.getCompRegCountry()));
            return agentRepository.save(agent);
        }
    }

    public Agent updateOne(AgentDTO agentDTO) {
        if (agentDTO.getId() == null) {
            throw new CustomException("please enter an agent Id");
        }
        Optional<Agent> optionalAgent = agentRepository.findById(agentDTO.getId());

        if (optionalAgent.isEmpty()) {
            throw new CustomException("agent not found");
        }

        Agent agent = optionalAgent.get();

        if (agentDTO.getCompRegName() != null) agent.setCompRegName(agentDTO.getCompRegName());
        if(agentDTO.getRiskStatus() != null){
            agent.setRisk(agentDTO.getRiskStatus().getValue());
            agent.setRiskStatus(RiskStatus.valueOf(agentDTO.getRiskStatus().name()));
        }
        if (agentDTO.getCompTradeName() != null) agent.setCompTradeName(agentDTO.getCompTradeName());
        if (agentDTO.getCompTaxNumber() != null) agent.setCompTaxNumber(agentDTO.getCompTaxNumber());
        if (agentDTO.getCompanyRegNumber() != null) agent.setCompanyRegNumber(agentDTO.getCompanyRegNumber());
        if (agentDTO.getCompRegCountry() != null) agent.setCompRegCountry(agentDTO.getCompRegCountry());
        if (agentDTO.getCompRegDate() != null) agent.setCompRegDate(agentDTO.getCompRegDate());
        if (agentDTO.getCompRegProvince() != null) agent.setCompRegProvince(agentDTO.getCompRegProvince());
        if (agentDTO.getCurrAddress() != null) agent.setCurrAddress(agentDTO.toEntity().getCurrAddress());
        if (agentDTO.getPhyAddress() != null) agent.setPhyAddress(agentDTO.toEntity().getPhyAddress());
        if (agentDTO.getPostAddress() != null) agent.setPostAddress(agentDTO.toEntity().getPostAddress());
        if (agentDTO.getMainPhoneNo() != null) agent.setMainPhoneNo(agentDTO.getMainPhoneNo());
        if (agentDTO.getSecPhoneNumber() != null) agent.setSecPhoneNumber(agentDTO.getSecPhoneNumber());
        if (agentDTO.getCompWebsite() != null) agent.setCompWebsite(agentDTO.getCompWebsite());
        if (agentDTO.getTradeNameWebsite() != null) agent.setTradeNameWebsite(agentDTO.getTradeNameWebsite());
        if (agentDTO.getIsListedOnSE() != null) agent.setIsListedOnSE(agentDTO.getIsListedOnSE());
        if (agentDTO.getExchangeName() != null) agent.setExchangeName(agentDTO.getExchangeName());
        if (agentDTO.getSymbolListed() != null) agent.setSymbolListed(agentDTO.getSymbolListed());
        if (agentDTO.getIsRegByFinEntity() != null) agent.setIsRegByFinEntity(agentDTO.getIsRegByFinEntity());
        if (agentDTO.getIsRegByFinSerRegulator() != null)
            agent.setIsRegByFinSerRegulator(agentDTO.getIsRegByFinSerRegulator());
        if (agentDTO.getFinEntity() != null) agent.setFinEntity(agentDTO.getFinEntity());
        if (agentDTO.getFinSerRegulatorName() != null) agent.setFinSerRegulatorName(agentDTO.getFinSerRegulatorName());
        if (agentDTO.getPrimPerContactName() != null) agent.setPrimPerContactName(agentDTO.getPrimPerContactName());
        if (agentDTO.getPrimPerEmail() != null) agent.setPrimPerEmail(agentDTO.getPrimPerEmail());
        if (agentDTO.getPrimPerPhoneNo() != null) agent.setPrimPerPhoneNo(agentDTO.getPrimPerPhoneNo());
        if (agentDTO.getPrimPerPosition() != null) agent.setPrimPerPosition(agentDTO.getPrimPerPosition());
        if (agentDTO.getPrimPerExtension() != null) agent.setPrimPerExtension(agentDTO.getPrimPerExtension());
        if (agentDTO.getAuthsignName() != null) agent.setAuthsignName(agentDTO.getAuthsignName());
        if (agentDTO.getAuthsignPosition() != null) agent.setAuthsignPosition(agentDTO.getAuthsignPosition());
        if (agentDTO.getFinancingBankName() != null) agent.setFinancingBankName(agentDTO.getFinancingBankName());
        if (agentDTO.getFinancingBankSwiftCode() != null)
            agent.setFinancingBankSwiftCode(agentDTO.getFinancingBankSwiftCode());
        if (agentDTO.getFundingAccountName() != null) agent.setFundingAccountName(agentDTO.getFundingAccountName());
        if (agentDTO.getFundingAccHolderRelation() != null)
            agent.setFundingAccHolderRelation(agentDTO.getFundingAccHolderRelation());
        if (agentDTO.getCurrencies() != null) agent.setCurrencies(agentDTO.getCurrencies());
        if (agentDTO.getBeneficialOweners() != null)
            agent.setBeneficialOweners(agentDTO.toEntity().getBeneficialOweners());
        if (agentDTO.getControlOweners() != null) agent.setControlOweners(agentDTO.toEntity().getControlOweners());
        if (agentDTO.getIsBlocked() != null) agent.setIsBlocked(agentDTO.toEntity().getIsBlocked());
        return agentRepository.save(agent);
    }

    public Agent getById(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            return agent.get();
        }
        throw new CustomException("Agent not found");
    }

    public PaginatedAgent findAll(Pageable pageable) {
        PaginatedAgent response = new PaginatedAgent();
        response.setData(agentRepository.findAll(pageable).stream().toList());
        response.setTotalDocuments(agentRepository.count());
        return response;
    }


}
