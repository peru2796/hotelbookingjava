package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    BookingMapperInterface bookingMapperInterface;

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
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
       return bookingMapper.getBookingDTOFromClientBookingList(bookingList,clientList,roomTypeList);
    }

    @Override
    public BookingDTO getBookingAndClientDetailsById(Long id){
        Booking booking = Optional.ofNullable(getBookingDetailsById(id)).get();
        Client client = clientService.getClientById(booking.getClientId()).get();
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        return bookingMapper.getBookingDTOFromClientBooking(booking,client,roomTypeList);
    }

    @Override
    public List<RoomType> getRoomType(){
        return roomTypeRepository.findAll();
    }
    @Override
    public List<BookingDTO> getRoomDetails(String startDate,String endDate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

      List<RoomDetailsDTO> roomDetailsDTOList =  roomDetailsRepository.getRoomList().get();
      List<Booking> bookingList = bookingRepository.getRoomDetailsList(startDateTime,endDateTime);
      List<BookingDTO> bookingDTOList = new ArrayList<>();
      roomDetailsDTOList.forEach(roomDetails ->{
          BookingDTO bookingDTO = new BookingDTO();
          Optional<Booking> bookingObject = bookingList.stream().filter( x -> x.getRoomId().equals(roomDetails.getId())).findFirst();
         if(bookingObject.isPresent()){
             Client client = clientService.getClientById(bookingObject.get().getClientId()).get();
             bookingDTO = bookingMapperInterface.toRoomDetailsDto(roomDetails,bookingObject.get(),client);
         }else{
             bookingDTO = bookingMapperInterface.toRoomDetailsDto(roomDetails);
         }
          bookingDTOList.add(bookingDTO);
      });
      return bookingDTOList;
    }
@Override
    public String updateBooking(Long id,Booking booking){

        int result = bookingRepository.updateBooking(id, booking.getCheckoutDts().toLocalDateTime(),booking.getComments());
        return "Success";
    }

}
