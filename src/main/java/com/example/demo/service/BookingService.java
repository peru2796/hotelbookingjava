package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomServiceOrders;
import com.example.demo.entity.RoomType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

    String createBooking(Booking booking);

    List<Booking> getBookingList();
    Booking getBookingDetailsById(Long id);
    List<Booking> deleteBooking(Booking booking);

     List<BookingDTO> getBookingAndClientDetails();
     BookingDTO getBookingAndClientDetailsById(Long id);
     List<RoomType> getRoomType();
     List<BookingDTO> getRoomDetails(String startDate,String endDate);
     String updateBooking(Long id,Booking booking);
    String checkOutBooking(Booking booking);
    List<BookingDTO> getListBookingDTO();
    String addRoomServiceOrders(RoomServiceOrders roomServiceOrders);
    List<BookingDTO> getBookingAndClientDetailsByDateFilter(String fromDate,String toDate);
    public List<RoomServiceOrders> getRoomServiceOrderByBookingId(Long bookingId);
}
