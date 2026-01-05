package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DashboardService {


     DashboardDTO getBookingAndClientDetailsById();

}
