package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.BookingMapperInterface;
import com.example.demo.repository.*;
import com.example.demo.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BillingServiceImpl implements BillingService{

    @Autowired
    private BookingService bookingService;



    @Override
    public BookingDTO getBillingById(Long id) {
        BookingDTO bookingDTO = bookingService.getBookingAndClientDetailsById(id);
        setAmountInGst(bookingDTO);
        return bookingDTO;
    }
    private void setAmountInGst(BookingDTO billing){
        double gstAmount = billing.getTotalAmount() * (5.0/100);
        billing.setBaseFare(billing.getTotalAmount() - gstAmount);
        billing.setSgst(gstAmount/2);
        billing.setCgst(gstAmount/2);
    }

}
