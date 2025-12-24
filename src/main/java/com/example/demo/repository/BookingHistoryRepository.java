package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {


}
