package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.ClientAttachment;
import com.example.demo.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {

    @Query("select b from PaymentHistory b where createdDts between :startDate and :endDate ")
    List<PaymentHistory> getPaymentList(LocalDateTime startDate, LocalDateTime endDate);
}
