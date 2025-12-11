package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking create(Booking booking) { return bookingRepository.save(booking); }
    public List<Booking> getAll() { return bookingRepository.findAll(); }
    public Optional<Booking> getById(Long id) { return bookingRepository.findById(id); }
    public Booking update(Long id, Booking booking) {
        booking.setId(id);
        return bookingRepository.save(booking);
    }
    public void delete(Long id) { bookingRepository.deleteById(id); }
}
