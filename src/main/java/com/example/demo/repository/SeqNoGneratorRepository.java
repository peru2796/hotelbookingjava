package com.example.demo.repository;

import com.example.demo.entity.Billing;
import com.example.demo.entity.BillingSeqNoGenerator;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SeqNoGneratorRepository extends JpaRepository<BillingSeqNoGenerator, Long> {


    BillingSeqNoGenerator findTopByStatusOrderById(Integer n);


}
