package com.example.demo.controller;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;
import com.example.demo.service.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<Object> create(@RequestBody Booking booking) {
        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity<>(bookingService.createBooking(booking),HttpStatus.OK);
    }

    @GetMapping("/getBookings")
    public List<BookingDTO> getAll() { return bookingService.getBookingAndClientDetails(); }

    @GetMapping("/getBookingDetailsById")
    public ResponseEntity<BookingDTO> getById(@RequestHeader("id") Long id) {
        return ResponseEntity.ok(bookingService.getBookingAndClientDetailsById(id));
    }

    @PostMapping("/updateBooking")
    public ResponseEntity<Object> update(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PostMapping("/deleteBooking")
    public List<Booking> delete( @RequestBody Booking booking) {
        return bookingService.deleteBooking(booking);
    }
}
// Full implementation will include CRUD and JWT logic