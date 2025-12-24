package com.example.demo.repository;

import com.example.demo.entity.ClientAttachment;
import com.example.demo.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
