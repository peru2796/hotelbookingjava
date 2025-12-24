package com.example.demo.repository;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

}
