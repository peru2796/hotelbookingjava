package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomType;
import com.example.demo.service.BookingService;
import com.example.demo.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DashBoardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/getDashboardservice")
    public ResponseEntity<DashboardDTO> getDashboardservice() {
        return new ResponseEntity<>(dashboardService.getBookingAndClientDetailsById(),HttpStatus.OK);
    }


}
// Full implementation will include CRUD and JWT logic