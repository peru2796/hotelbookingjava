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
import java.util.*;

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
                long days = ChronoUnit.DAYS.between(booking.getCheckinDts(), booking.getCheckoutDts());
                Optional<RoomType> roomType = roomTypeRepository.findById(Math.toIntExact(booking.getRoomType()));
                days = days == 0? 1:days;
                booking.setTotalAmount(roomType.get().getAmount()*days);
                Optional<Booking> optionalBooking = bookingRepository.findById(Objects.isNull(booking.getId())?-1:booking.getId());
                booking.setAmountRemaining(booking.getTotalAmount() - booking.getAmountPaid());
                booking.setBookedDts(LocalDateTime.now());
                booking.setCreatedDts(LocalDateTime.now());
                booking =   bookingRepository.save(booking);
                List<PaymentHistory> paymentHistories = paymentHistoryRepository.findByBookingId(booking.getId());
                Double totalAmount = paymentHistories.stream()
                        .mapToDouble(PaymentHistory::getAmount)
                        .sum();
                if(!totalAmount.equals(booking.getAmountPaid())){
                    PaymentHistory paymentHistory = new PaymentHistory();
                    paymentHistory.setBookingId(booking.getId());
                    if(optionalBooking.isPresent())
                        paymentHistory.setAmount( booking.getAmountPaid() - totalAmount);
                    else
                        paymentHistory.setAmount(booking.getAmountPaid());
                    paymentHistory.setBooking(booking);
                    paymentHistory.setStatus(1);
                    paymentHistoryRepository.save(paymentHistory);
                }
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
      List<RoomType> roomTypeList = roomTypeRepository.findAll();

      List<Booking> bookingList = bookingRepository.getRoomDetailsList(startDateTime,endDateTime);
      List<BookingDTO> bookingDTOList = new ArrayList<>();
      roomDetailsDTOList.forEach(roomDetails ->{
          BookingDTO bookingDTO = new BookingDTO();
          Optional<Booking> bookingObject = bookingList.stream().filter( x -> x.getRoomId().equals(roomDetails.getId())).findFirst();
         if(bookingObject.isPresent()){
             Client client = clientService.getClientById(bookingObject.get().getClientId()).get();
            Optional<RoomType> roomTypeOptional = roomTypeList.stream().filter(x -> x.getId().equals(bookingObject.get().getRoomType())).findFirst();

            bookingDTO = mapperInterface.toRoomDetailsDto(roomDetails,bookingObject.get(),client);
            bookingDTO.setRoomTypeName(roomTypeOptional.get().getRoomType());
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
        long days = ChronoUnit.DAYS.between(booking.getCheckinDts(), booking.getCheckoutDts())+1;
        Optional<RoomType> roomType = roomTypeRepository.findById(Math.toIntExact(booking.getRoomType()));
        days = days == 0? 1:days;
        booking.setTotalAmount(roomType.get().getAmount()*days);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setBookingId(booking.getId());
        paymentHistory.setBooking(booking);
        paymentHistory.setAmount(booking.getAmountPaid()-book.getAmountPaid()-booking.getDiscountAmount());
        paymentHistory.setStatus(1);
        paymentHistoryRepository.save(paymentHistory);
       Billing billing = mapperInterface.toBooking(booking);
        if(booking.getDiscountAmount()> 0){
            double discountPercentage = (booking.getDiscountAmount() / booking.getTotalAmount()) * 100;
            booking.setDiscountPercentage(discountPercentage);
            booking.setTotalAmount(booking.getTotalAmount()-booking.getDiscountAmount());
            booking.setAmountPaid(booking.getTotalAmount());
            billing.setAmountPaid(booking.getTotalAmount());
            billing.setTotalAmount(booking.getTotalAmount());
            billing.setDiscountPercentage(discountPercentage);
        }
        billing.setGstEnabled(booking.isGstEnabled());
        billing.setBillingNumber(billNo());
        billing.setTransactionStatus(AppConstants.CHECKOUT_STATUS_CODE);
        if(billing.isGstEnabled()){
            setAmountInGst(billing);
        }else{
            billing.setBaseFare(billing.getTotalAmount());
            billing.setSgst(0.0);
            billing.setCgst(0.0);
        }


      billing = billingRepository.save(billing);
      booking.setTransactionStatus(AppConstants.CHECKOUT_STATUS_CODE);
      booking.setBillingId(billing.getId());
      booking.setBillingNumber(billing.getBillingNumber());
      booking.setCreatedDts(book.getCreatedDts());
      bookingRepository.save(booking);
      return "Success";
    }

    private String billNo(){
      BillingSeqNoGenerator billingSeqNoGenerator = seqNoGneratorRepository.findTopByStatusOrderById(1);
      billingSeqNoGenerator.setLast_value(billingSeqNoGenerator.getLast_value()+1);

      seqNoGneratorRepository.save(billingSeqNoGenerator);

      return (billingSeqNoGenerator.getLast_value())+"/"+billingSeqNoGenerator.getYear();
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
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        List<Booking> bookingList = bookingRepository.getTodayRoomDetailsList(startDateTime,endDateTime);
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        bookingList.stream().forEach(booking -> {
            Client client = clientService.getClientById(booking.getClientId()).get();
            BookingDTO bookingDTO = mapperInterface.toBookingDto(booking,client);
            Optional<RoomType> roomTypeOptional = roomTypeList.stream().filter(x -> x.getId().equals(booking.getRoomType())).findFirst();
            bookingDTO.setRoomTypeName(roomTypeOptional.get().getRoomType());
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

    @Override
    public String addPaymentBooking(Booking booking) {
        Booking book = bookingRepository.findById(booking.getId()).get();

        Optional<RoomType> roomType = roomTypeRepository.findById(Math.toIntExact(booking.getRoomType()));

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setBookingId(booking.getId());
        paymentHistory.setBooking(booking);
        paymentHistory.setAmount(booking.getAmountPaid());
        paymentHistory.setStatus(1);
        paymentHistoryRepository.save(paymentHistory);
        Double amountRemaining = book.getAmountRemaining()-booking.getAmountPaid();
        booking.setAmountPaid(book.getAmountPaid()+booking.getAmountPaid());

        bookingRepository.addPaymentBooking(booking.getId(), booking.getAmountPaid(),amountRemaining);
        billingRepository.addPaymentBilling(book.getBillingId(),booking.getAmountPaid(),booking.getAmountRemaining());
        return "Success";
    }
}
