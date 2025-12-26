package com.example.demo.repository;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Booking;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where (checkinDts between :startDate and :endDate OR checkoutDts between :startDate and :endDate )")
    List<Booking> getRoomDetailsList(LocalDateTime startDate, LocalDateTime endDate);


    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.comments = :comments,b.checkoutDts =:checkoutDts  WHERE b.id = :id")
    int updateBooking(@Param("id") Long id, @Param("checkoutDts") LocalDateTime checkoutDts, @Param("comments") String comments);


}
