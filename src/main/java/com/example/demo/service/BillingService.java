package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomType;

import java.util.List;

public interface BillingService {


     BookingDTO getBillingById(Long id);

}
