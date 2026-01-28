package com.example.demo.repository;

import com.example.demo.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findByBilledDtsBetween(LocalDateTime start, LocalDateTime end);

}
