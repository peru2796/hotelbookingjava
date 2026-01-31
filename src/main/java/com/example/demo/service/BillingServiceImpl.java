package com.example.demo.service;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.GstReportDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.BillingMapper;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.MapperInterface;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    BillingMapper billingMapper;

    @Autowired
    MapperInterface mapperInterface;

    @Override
    public BookingDTO getBillingById(Long id) {
        BookingDTO bookingDTO = bookingService.getBookingAndClientDetailsById(id);
        setAmountInGst(bookingDTO);
        bookingDTO.setTotalAmountInWords(mapperInterface.formatTotalAmount(bookingDTO.getAmountPaid()+bookingDTO.getMiscellaneousCharge()));
        bookingDTO.setTotalAmount(bookingDTO.getAmountPaid()+bookingDTO.getMiscellaneousCharge());
        return bookingDTO;
    }

    @Override
    public GstReportDTO getGstBillingReport(String fromDate, String toDate) {
        GstReportDTO getReportDTO = new GstReportDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime fromDateTime = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(toDate, formatter);
        List<Billing> billingList = billingRepository.findByBilledDtsBetween(fromDateTime, toDateTime);
        List<Client> clientList = clientService.getClientList();
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        getReportDTO.setBookingDTOList(billingMapper.getBookingDTOFromClientBillingList(billingList, clientList, roomTypeList));
        getReportDTO.setGrantTotal(billingList.stream().filter(x -> x.getTotalAmount() != null).mapToDouble(Billing::getTotalAmount).sum());
        getReportDTO.setCgstTotal(billingList.stream().filter(x -> x.getCgst() != null).mapToDouble(Billing::getCgst).sum());
        getReportDTO.setSgstTotal(billingList.stream().filter(x -> x.getSgst() != null).mapToDouble(Billing::getSgst).sum());
        getReportDTO.setBaseFareTotal(billingList.stream().filter(x -> x.getBaseFare() != null).mapToDouble(Billing::getBaseFare).sum());
        return getReportDTO;
    }

    private void setAmountInGst(BookingDTO billing) {
        double gstAmount = billing.getTotalAmount() * (5.0 / 100);
        billing.setBaseFare(billing.getTotalAmount() - gstAmount);
        billing.setSgst(gstAmount / 2);
        billing.setCgst(gstAmount / 2);
    }

}
