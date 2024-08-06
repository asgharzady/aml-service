package com.appopay.aml.service;

import com.appopay.aml.entity.Customers;
import com.appopay.aml.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customers> findAll(){
        List<Customers> list = customerRepository.findAll();
         return list;
    }

    public Customers findById(Long id){
        Optional<Customers> customer = customerRepository.findById(id);
        if(customer.isPresent())
            return customer.get();

        else{
            throw new RuntimeException();
        }
    }
}
