package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomType;
import com.example.demo.service.BillingService;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping("/getBillingDetailsById")
    public ResponseEntity<BookingDTO> getById(@RequestHeader("id") Long id) {
        return ResponseEntity.ok(billingService.getBillingById(id));
    }


}
// Full implementation will include CRUD and JWT logic