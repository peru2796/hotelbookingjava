package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.entity.ClientAttachment;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ClientAttachmentRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientAttachmentRepository clientAttachmentRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;

    @Override
    public Client addClient(Client client, ClientAttachment clientAttachment) {

            client.setStatus(1);
            Client c = clientRepository.save(client);
            if(null != clientAttachment){
                clientAttachment.setClientId(c.getId());
                clientAttachmentRepository.save(clientAttachment);
            }

        return client;
    }

    @Override
    public List<Client> getClientList() {
        return clientRepository.findAll().stream().sorted(Comparator.comparing(Client::getId).reversed()).toList();
    }

    @Override
    public List<Client> getClientListByDateFilter(String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(toDate, formatter);

        return clientRepository.findByCreatedDtsBetweenOrderByIdDesc(startDateTime,endDateTime);
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(()-> new RuntimeException("no record found for the given id..")));

    }

    @Override
    public Optional<Client> getClientByMobileNumber(String mobileNumber) {
        return Optional.ofNullable(clientRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new RuntimeException("no record found for the given mobile number..")));

    }

    @Override
    public String createClientAndBooking(Client client, Booking booking,ClientAttachment clientAttachment) {
        Client c = addClient(client,clientAttachment);
        booking.setClientId(c.getId());
        String b = bookingService.createBooking(booking);
//        if(c.equalsIgnoreCase(AppConstants.STATUS_SUCCESS) && b.equalsIgnoreCase(AppConstants.STATUS_SUCCESS))
        return AppConstants.STATUS_SUCCESS;

    }
}
