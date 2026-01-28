package com.example.demo.repository;

import com.example.demo.dto.BarChartResponse;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomServiceOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


public interface RoomServiceOrdersRepository extends JpaRepository<RoomServiceOrders, Long> {


}
