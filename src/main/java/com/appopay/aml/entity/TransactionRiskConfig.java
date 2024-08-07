package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "transaction_risk_config")
@Data
@Entity
public class TransactionRiskConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dailyLimit;
    private String dailyAmount;
    private String monthlyFrequency;
    private String monthlyAmount;
    private String locationFrequency;
    @OneToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryRiskConfig countryRiskConfig;
}
