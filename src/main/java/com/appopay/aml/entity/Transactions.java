package com.appopay.aml.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "transactions")
@Data
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;
    private String type;
    private String description;
    private String merchantLocation;



    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customers customers;
}
