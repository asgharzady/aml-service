package com.appopay.aml.repository;

import com.appopay.aml.entity.CountryRiskConfig;
import com.appopay.aml.entity.Customers;
import com.appopay.aml.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCustomersAndCreatedAtAfter(Customers customer, Instant date);

    List<Transaction> findAllByCustomers(Customers customer);

}
