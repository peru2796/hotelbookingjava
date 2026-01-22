package com.example.demo.repository;

import com.example.demo.dto.BarChartResponse;
import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Booking;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where (checkinDts between :startDate and :endDate OR checkoutDts between :startDate and :endDate ) AND transactionStatus = 22")
    List<Booking> getRoomDetailsList(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select b from Booking b where (checkinDts between :startDate and :endDate OR checkoutDts between :startDate and :endDate )")
    List<Booking> getRoomDataList(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select b from Booking b where (checkinDts between :startDate and :endDate )")
    List<Booking> getTodayRoomDetailsList(LocalDateTime startDate, LocalDateTime endDate);


    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.comments = :comments,b.checkoutDts =:checkoutDts  WHERE b.id = :id")
    int updateBooking(@Param("id") Long id, @Param("checkoutDts") LocalDateTime checkoutDts, @Param("comments") String comments);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.amountPaid = :amountPaid,b.amountRemaining =:amountRemaining, b.transactionStatus =:transactionStatus,b.checkoutDts = :checkoutDts  WHERE b.id = :id")
    int checkOutBooking(@Param("id") Long id, @Param("amountPaid") Double amountPaid, @Param("amountRemaining") Double amountRemaining,@Param("transactionStatus") Integer transactionStatus,@Param("checkoutDts") LocalDateTime checkoutDts);



    @Query("""
        select new com.example.demo.dto.BarChartResponse(FUNCTION('DATE', b.checkinDts), count(b.id))
        from Booking b
        where b.checkinDts between :start and :end
        group by FUNCTION('DATE', b.checkinDts)
        order by 1
    """)
    List<BarChartResponse> countCheckinsByDay(LocalDateTime start, LocalDateTime end);

    // Check-outs per day in range
    @Query("""
        select new com.example.demo.dto.BarChartResponse(FUNCTION('DATE', b.checkoutDts), count(b.id))
        from Booking b
        where b.checkoutDts between :start and :end
        group by FUNCTION('DATE', b.checkoutDts)
        order by 1
    """)
    List<BarChartResponse> countCheckoutsByDay(LocalDateTime start, LocalDateTime end);

}
