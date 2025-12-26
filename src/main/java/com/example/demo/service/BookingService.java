package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

    String createBooking(Booking booking);

    List<Booking> getBookingList();
    Booking getBookingDetailsById(Long id);
    List<Booking> deleteBooking(Booking booking);

    public List<BookingDTO> getBookingAndClientDetails();
}
