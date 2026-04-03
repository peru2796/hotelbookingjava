package com.example.demo.repository;

import com.example.demo.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


public interface BillingRepository extends JpaRepository<Billing, Long> {

    List<Billing> findByBilledDtsBetween(LocalDateTime start, LocalDateTime end);
    @Modifying
    @Transactional
    @Query("UPDATE Billing b SET b.amountPaid = :amountPaid,b.amountRemaining =:amountRemaining  WHERE b.id = :id")
    int addPaymentBilling(@Param("id") Long id, @Param("amountPaid") Double amountPaid, @Param("amountRemaining") Double amountRemaining);
}
