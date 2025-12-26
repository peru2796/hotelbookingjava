package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.BookingHistory;
import com.example.demo.entity.Client;
import com.example.demo.entity.PaymentHistory;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.repository.BookingHistoryRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentHistoryRepository;
import com.example.demo.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.AppConstants.BOOKED_STATUS_CODE;
import static com.example.demo.util.AppConstants.CHECKED_IN_CODE;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    BookingMapper bookingMapper;

    @Override
    public String createBooking(Booking booking) {
            if(null != booking){
              booking =   bookingRepository.save(booking);
               if(null != booking.getPaymentHistory()){
                   List<PaymentHistory> paymentHistoryList = booking.getPaymentHistory();
                   Long bookingId = booking.getId();
                   Booking finalBooking = booking;
                   finalBooking.setId(bookingId);
                   paymentHistoryList.forEach(paymentHistory -> paymentHistory.setBooking(finalBooking));
                    paymentHistoryRepository.saveAll(paymentHistoryList);
               }
               if(null != booking.getTransactionStatus()){
                 List<BookingHistory> bookingHistoryList = new ArrayList<>();

                   BookingHistory bookingHistory = new BookingHistory();
                   bookingHistory.setBooking(booking);
                   bookingHistory.setBookingStatus(BOOKED_STATUS_CODE);
                   bookingHistoryList.add(bookingHistory);

                   if(booking.getTransactionStatus().equals(CHECKED_IN_CODE)){
                       BookingHistory bookingHistory1 = new BookingHistory();
                       bookingHistory1.setBooking(booking);
                       bookingHistory1.setBookingStatus(CHECKED_IN_CODE);
                       bookingHistoryList.add(bookingHistory1);
                   }
                   bookingHistoryRepository.saveAll(bookingHistoryList);
               }
            }
            return AppConstants.STATUS_SUCCESS;
    }

    @Override
    public List<Booking> getBookingList() {

        List<Booking> bookingList = bookingRepository.findAll();
//        List<Booking> bookingList = bookingHistoryRepository.findAll();
//        List<Booking> bookingList = paymentHistoryRepository.findAll();
        return bookingList;

    }

    @Override
    public Booking getBookingDetailsById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    @Override
    public List<Booking> deleteBooking(Booking booking) {

         booking.setStatus(-1);
         booking = bookingRepository.save(booking);

        return getBookingList();
    }

    @Override
    public List<BookingDTO> getBookingAndClientDetails(){
        List<Booking> bookingList = getBookingList();
        List<Client> clientList = clientService.getClientList();
       return bookingMapper.getBookingDTOFromClientBooking(bookingList,clientList);
    }

}
