package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.RoomType;
import com.example.demo.service.BookingService;
import com.example.demo.service.BookingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<String> create(@RequestBody Booking booking) {
        ObjectMapper objectMapper = new ObjectMapper();
//        ApiResponse apiResponse = new ApiResponse("200 OK",bookingService.createBooking(booking));
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

    @GetMapping("/getRoomType")
    public List<RoomType> getRoomType(){
        return bookingService.getRoomType();
    }
    @GetMapping("/getRoomUserDetails")
    public List<BookingDTO> getBookingDTO(@RequestHeader(value = "date",required = false) String date){
        String startDate = LocalDate.now().toString()+" 00:00:00";
        String endDate = LocalDate.now().toString()+" 23:59:59";
        if(null != date){
            startDate = date+" 00:00:00";
            endDate = date+" 23:59:59";
        }
        return bookingService.getRoomDetails(startDate,endDate);
    }


    @PatchMapping("/booking/{id}")
    public ResponseEntity<ApiResponse> patchClient(
            @PathVariable Long id, @RequestBody Booking req) {
        String updated = bookingService.updateBooking(id, req);
        return ResponseEntity.ok(new ApiResponse(updated, "Client updated"));
    }

}
// Full implementation will include CRUD and JWT logic