package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.BookingMapperInterface;
import com.example.demo.repository.*;
import com.example.demo.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.AppConstants.BOOKED_STATUS_CODE;
import static com.example.demo.util.AppConstants.CHECKED_IN_CODE;

@Service
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    private BookingRepository bookingRepository;

@Autowired
private BookingService bookingService;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    BookingMapperInterface bookingMapperInterface;


    @Override
    public DashboardDTO getBookingAndClientDetailsById() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        String startDate = LocalDate.now().toString()+" 00:00:00";
        String endDate = LocalDate.now().toString()+" 23:59:59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

      Long totalRoom = roomDetailsRepository.countByStatus(1);
      Long noOfAvailableRooms = roomDetailsRepository.getRoomDetailsList(startDateTime,endDateTime).stream().count();
        dashboardDTO.setNoOfAvailableRooms(noOfAvailableRooms);
      Long noOfOccupiedRooms = totalRoom - noOfAvailableRooms;
        dashboardDTO.setNoOfOccupiedRooms(noOfOccupiedRooms);
     List<Booking> bookingList = bookingRepository.getRoomDetailsList(startDateTime,endDateTime);
     Integer revenueToday =  bookingList.stream().filter(x -> x.getCheckinDts().isBefore(LocalDate.now().atTime(23,59,59))).mapToInt(y -> y.getTotalAmount().intValue()).sum();
    dashboardDTO.setRevenueEarnedToday(revenueToday);
     Long noOfCheckIns =  bookingList.stream().filter(x -> x.getCheckinDts().isBefore(LocalDate.now().atTime(23,59,59))).toList().stream().count();
        Long noOfCheckOuts =  bookingList.stream().filter(x -> x.getCheckoutDts().isBefore(LocalDate.now().atTime(23,59,59))).toList().stream().count();
    dashboardDTO.setNoOfCheckins(noOfCheckIns);
    dashboardDTO.setNoOfCheckouts(noOfCheckOuts);
       return dashboardDTO;
    }

    @Override
    public List<BookingDTO> getBookingListToday(){
        return bookingService.getListBookingDTO();
    }

}
