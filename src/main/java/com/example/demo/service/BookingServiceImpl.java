package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.MapperInterface;
import com.example.demo.repository.*;
import com.example.demo.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    private BillingRepository billingRepository;

    @Autowired
    private SeqNoGneratorRepository seqNoGneratorRepository;

    @Autowired
    private RoomServiceOrdersRepository roomServiceOrdersRepository;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    MapperInterface mapperInterface;

    @Override
    public String createBooking(Booking booking) {
            if(null != booking){
                long days = ChronoUnit.DAYS.between(booking.getCheckinDts().toLocalDate(), booking.getCheckoutDts().toLocalDate());
                booking.setTotalAmount(booking.getTotalAmount()*days);
                booking.setAmountRemaining(booking.getTotalAmount() - booking.getAmountPaid());

                booking =   bookingRepository.save(booking);

                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.setBookingId(booking.getId());
                paymentHistory.setAmount(booking.getAmountPaid());
                paymentHistory.setBooking(booking);
                paymentHistory.setStatus(1);
                paymentHistoryRepository.save(paymentHistory);
            }
            return AppConstants.STATUS_SUCCESS;
    }

    @Override
    public List<Booking> getBookingList() {

        return bookingRepository.findAll().stream().sorted(Comparator.comparing(Booking::getId).reversed()).toList();

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
        List<RoomServiceOrders> roomServiceOrdersList = getRoomServiceOrderByBookingId(booking.getId());
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        return bookingMapper.getBookingDTOFromClientBooking(booking,client,roomTypeList,roomServiceOrdersList);
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
             bookingDTO = mapperInterface.toRoomDetailsDto(roomDetails,bookingObject.get(),client);
             List<RoomServiceOrders> roomServiceOrdersList = getRoomServiceOrderByBookingId(bookingObject.get().getId());
             bookingDTO.setMiscellaneousCharge(roomServiceOrdersList.stream().mapToDouble(RoomServiceOrders::getOrderValue).sum());
             bookingDTO.setTodayDts(LocalDateTime.now());
         }else{
             bookingDTO = mapperInterface.toRoomDetailsDto(roomDetails);
         }
          bookingDTOList.add(bookingDTO);
      });
      return bookingDTOList.stream().sorted(Comparator.comparing(BookingDTO::getRoomId))
              .toList();
    }
@Override
    public String updateBooking(Long id,Booking booking){

        int result = bookingRepository.updateBooking(id, booking.getCheckoutDts(),booking.getComments());
        return "Success";
    }

    @Override
    public String checkOutBooking(Booking booking) {
        booking.setId(booking.getBookingId());
        Booking book = bookingRepository.findById(booking.getId()).get();
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setBookingId(booking.getId());
        paymentHistory.setBooking(booking);
        paymentHistory.setAmount(booking.getAmountPaid()-book.getAmountPaid());
        paymentHistory.setStatus(1);
        paymentHistoryRepository.save(paymentHistory);
       Billing billing = mapperInterface.toBooking(booking);
        billing.setBillingNumber(billNo());
        billing.setTransactionStatus(AppConstants.CHECKOUT_STATUS_CODE);
        setAmountInGst(billing);
      billing = billingRepository.save(billing);
        bookingRepository.checkOutBooking(booking.getId(),booking.getAmountPaid(),booking.getAmountRemaining(),
                AppConstants.CHECKOUT_STATUS_CODE,booking.getCheckoutDts(),billing.getId(),billing.getBillingNumber());

        return "Success";
    }

    private String billNo(){
      BillingSeqNoGenerator billingSeqNoGenerator = seqNoGneratorRepository.findTopByStatusOrderById(1);
      billingSeqNoGenerator.setLast_value(billingSeqNoGenerator.getLast_value()+1);

      seqNoGneratorRepository.save(billingSeqNoGenerator);

      return (billingSeqNoGenerator.getLast_value()+1)+"/"+billingSeqNoGenerator.getYear();
    }

    private void setAmountInGst(Billing billing){
        double gstAmount = billing.getTotalAmount() * (5.0/100);
        billing.setBaseFare(billing.getTotalAmount() - gstAmount);
        billing.setSgst(gstAmount/2);
        billing.setCgst(gstAmount/2);
    }

    public List<BookingDTO> getListBookingDTO(){
        String startDate = LocalDate.now().toString()+" 00:00:00";
        String endDate = LocalDate.now().toString()+" 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        List<Booking> bookingList = bookingRepository.getTodayRoomDetailsList(startDateTime,endDateTime);
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        bookingList.stream().forEach(booking -> {
            Client client = clientService.getClientById(booking.getClientId()).get();
            BookingDTO bookingDTO = mapperInterface.toBookingDto(booking,client);
            bookingDTOList.add(bookingDTO);
        });
        return bookingDTOList.stream().sorted(Comparator.comparing(BookingDTO::getBookingId).reversed())
                .toList();
    }

    @Override
    public String addRoomServiceOrders(RoomServiceOrders roomServiceOrders) {
        roomServiceOrders.setStatus(1);
        roomServiceOrdersRepository.save(roomServiceOrders);
        return "Success";
    }

    @Override
    public List<BookingDTO> getBookingAndClientDetailsByDateFilter(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(toDate, formatter);
        List<Booking> bookingList = bookingRepository.findByBookedDtsBetween(startDateTime,endDateTime);
        List<Client> clientList = clientService.getClientList();
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        return bookingMapper.getBookingDTOFromClientBookingList(bookingList,clientList,roomTypeList);

    }

    @Override
    public List<RoomServiceOrders> getRoomServiceOrderByBookingId(Long bookingId){
        return roomServiceOrdersRepository.findByBookingId(bookingId);
    }
}
